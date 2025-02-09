package desafio.votacao.service.servico;

import desafio.votacao.service.dominio.entidade.Pauta;
import desafio.votacao.service.dominio.entidade.PautaSessao;
import desafio.votacao.service.dominio.entidade.VotoSessao;
import desafio.votacao.service.dominio.repositorio.PautaRepository;
import desafio.votacao.service.dominio.repositorio.VotoSessaoRepository;
import desafio.votacao.service.dto.VotarDto;
import desafio.votacao.service.dto.VotoSessaoDto;
import desafio.votacao.service.enums.SimNao;
import desafio.votacao.service.excecao.FalhaRequisicaoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VotoSessaoService {

    private final VotoSessaoRepository repository;
    private final PautaRepository pautaRepository;
    private final PautaSessaoService pautaSessaoService;


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void votar(VotarDto votarDto, Long pautaId) {

        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new FalhaRequisicaoException("Pauta não encontrada."));

        PautaSessao pautaSessao = pautaSessaoService.buscarSessaoDaPauta(pauta);
        pautaSessaoService.validarSessaoAtiva(pautaSessao);

        if (repository.existsByRefAssociadoAndAndSessao(votarDto.getRefAssociado(), pautaSessao)) {
            throw new FalhaRequisicaoException("Associado informado já votou na pauta.");
        }

        repository.save(VotoSessao
                .builder()
                .refAssociado(votarDto.getRefAssociado())
                .sessao(pautaSessao)
                .voto(votarDto.getVoto().isSim())
                .build());

    }

    public VotoSessaoDto contabilizarVotos(PautaSessao pautaSessao) {
        List<VotoSessao> votos = repository.findAllBySessao(pautaSessao);

        if (CollectionUtils.isEmpty(votos)) {
            return VotoSessaoDto
                    .builder()
                    .totalVotos(0L)
                    .votoNao(0L)
                    .votoSim(0L)
                    .build();
        }

        long votosSim = votos.stream().filter(VotoSessao::getVoto).count();
        long votosNao = votos.size() - votosSim;
        long totalVotos = votos.size();

        return VotoSessaoDto
                .builder()
                .totalVotos(totalVotos)
                .votoSim(votosSim)
                .votoNao(votosNao)
                .build();

    }
}
