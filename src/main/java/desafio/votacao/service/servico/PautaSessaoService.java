package desafio.votacao.service.servico;

import desafio.votacao.service.dominio.entidade.Pauta;
import desafio.votacao.service.dominio.entidade.PautaSessao;
import desafio.votacao.service.dominio.repositorio.PautaRepository;
import desafio.votacao.service.dominio.repositorio.PautaSessaoRepository;
import desafio.votacao.service.dto.PautaSessaoDto;
import desafio.votacao.service.excecao.DadosNaoEncontradosException;
import desafio.votacao.service.excecao.FalhaRequisicaoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PautaSessaoService {

    private final PautaSessaoRepository repository;
    private final PautaRepository pautaRepository;

    public boolean existeSessao(Pauta pauta) {
        return repository.existsByPauta(pauta);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PautaSessaoDto iniciarSessao(Long pautaId) {

        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new DadosNaoEncontradosException("Pauta não encontrada"));

        if (existeSessao(pauta)) {
            throw new FalhaRequisicaoException("Pauta informada já possui sessão.");
        }

        PautaSessao pautaSessao = PautaSessao
                .builder()
                .pauta(pauta)
                .inicioVotacao(LocalDateTime.now())
                .fimVotacao(LocalDateTime.now().plusMinutes(pauta.getTempo()))
                .build();

        repository.save(pautaSessao);

        return PautaSessaoDto.converterEntidade(pautaSessao);
    }

    @Transactional(readOnly = true)
    public PautaSessao buscarSessaoDaPauta(Pauta pauta) {
        return repository.findByPauta(pauta)
                .orElseThrow(() -> new DadosNaoEncontradosException("Sessão não encontrada para a pauta informada."));
    }


    public void validarSessaoAtiva(PautaSessao pautaSessao) {
        if (LocalDateTime.now().isAfter(pautaSessao.getFimVotacao())) {
            throw new FalhaRequisicaoException("Não é possível votar.A votação foi encerrada.");
        }
    }

}
