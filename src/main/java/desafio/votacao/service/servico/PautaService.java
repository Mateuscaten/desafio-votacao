package desafio.votacao.service.servico;

import desafio.votacao.service.dominio.entidade.Pauta;
import desafio.votacao.service.dominio.entidade.PautaSessao;
import desafio.votacao.service.dominio.repositorio.PautaRepository;
import desafio.votacao.service.dto.PautaDto;
import desafio.votacao.service.dto.PautaDtoDetalhe;
import desafio.votacao.service.dto.PautaSessaoDto;
import desafio.votacao.service.dto.VotoSessaoDto;
import desafio.votacao.service.excecao.DadosNaoEncontradosException;
import desafio.votacao.service.excecao.FalhaRequisicaoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PautaService {

    private final PautaRepository repository;
    private final PautaSessaoService pautaSessaoService;
    private final VotoSessaoService votoSessaoService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PautaDto criarPauta(PautaDto pautaDto) {

        if (pautaDto.getTempo() != null) {
            if (pautaDto.getTempo() < 1) {
                throw new FalhaRequisicaoException("O tempo da votação não pode ser menor que 1 minuto.");
            }
        } else {
            pautaDto.setTempo(1);
        }

        Pauta pauta = repository.save(Pauta
                .builder()
                .tempo(pautaDto.getTempo())
                .titulo(pautaDto.getTitulo())
                .descricao(pautaDto.getDescricao())
                .build());

        return PautaDto.converterEntidade(pauta);

    }

    @Transactional(readOnly = true)
    public List<PautaDto> buscarPautas() {

        List<Pauta> pautas = repository.findAll();
        if (pautas.isEmpty()) {
            return Collections.emptyList();
        }

        return pautas.stream().map(PautaDto::converterEntidade).collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public Pauta buscarPautaPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DadosNaoEncontradosException("Pauta não encontrada."));
    }

    @Transactional(readOnly = true)
    public PautaDtoDetalhe buscarDetalhePautas(Long pautaId) {
        Pauta pauta = buscarPautaPorId(pautaId);

        PautaSessaoDto pautaSessaoDto = null;
        VotoSessaoDto votoSessaoDto = null;
        if (pautaSessaoService.existeSessao(pauta)) {
            PautaSessao pautaSessao = pautaSessaoService.buscarSessaoDaPauta(pauta);
            pautaSessaoDto = PautaSessaoDto.converterEntidade(pautaSessao);
            votoSessaoDto = votoSessaoService.contabilizarVotos(pautaSessao);
        }


        return PautaDtoDetalhe
                .builder()
                .id(pauta.getId())
                .tempo(pauta.getTempo())
                .titulo(pauta.getTitulo())
                .descricao(pauta.getDescricao())
                .pautaSessao(pautaSessaoDto)
                .votoSessao(votoSessaoDto)
                .build();

    }
}
