package desafio.votacao.service;

import desafio.votacao.service.dominio.entidade.Pauta;
import desafio.votacao.service.dominio.entidade.PautaSessao;
import desafio.votacao.service.dominio.entidade.VotoSessao;
import desafio.votacao.service.dominio.repositorio.PautaRepository;
import desafio.votacao.service.dominio.repositorio.PautaSessaoRepository;
import desafio.votacao.service.dominio.repositorio.VotoSessaoRepository;
import desafio.votacao.service.dto.PautaSessaoDto;
import desafio.votacao.service.dto.VotarDto;
import desafio.votacao.service.dto.VotoSessaoDto;
import desafio.votacao.service.enums.SimNao;
import desafio.votacao.service.excecao.DadosNaoEncontradosException;
import desafio.votacao.service.excecao.FalhaRequisicaoException;
import desafio.votacao.service.servico.PautaSessaoService;
import desafio.votacao.service.servico.VotoSessaoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PautaSessaoServiceTest {

    @InjectMocks
    private PautaSessaoService pautaSessaoService;

    @Mock
    private PautaSessaoRepository repository;

    @Mock
    private PautaRepository pautaRepository;

    @Test
    void deveRetornarVerdadeiroSeSessaoExiste() {
        Pauta pauta = new Pauta();
        when(repository.existsByPauta(pauta)).thenReturn(true);

        boolean existe = pautaSessaoService.existeSessao(pauta);

        assertTrue(existe);
        verify(repository, times(1)).existsByPauta(pauta);
    }

    @Test
    void deveRetornarFalsoSeSessaoNaoExiste() {
        Pauta pauta = new Pauta();
        when(repository.existsByPauta(pauta)).thenReturn(false);

        boolean existe = pautaSessaoService.existeSessao(pauta);

        assertFalse(existe);
        verify(repository, times(1)).existsByPauta(pauta);
    }

    @Test
    void deveIniciarSessaoComSucesso() {
        Long pautaId = 1L;
        Pauta pauta = new Pauta();
        pauta.setTempo(10);

        when(pautaRepository.findById(pautaId)).thenReturn(Optional.of(pauta));
        when(repository.existsByPauta(pauta)).thenReturn(false);

        PautaSessaoDto pautaSessaoDto = pautaSessaoService.iniciarSessao(pautaId);

        assertNotNull(pautaSessaoDto);
        verify(repository, times(1)).save(any(PautaSessao.class));
    }

    @Test
    void deveLancarErroQuandoPautaNaoExiste() {
        Long pautaId = 1L;
        when(pautaRepository.findById(pautaId)).thenReturn(Optional.empty());

        assertThrows(DadosNaoEncontradosException.class, () -> pautaSessaoService.iniciarSessao(pautaId));
    }

    @Test
    void deveLancarErroQuandoPautaJaTemSessao() {
        Long pautaId = 1L;
        Pauta pauta = new Pauta();

        when(pautaRepository.findById(pautaId)).thenReturn(Optional.of(pauta));
        when(repository.existsByPauta(pauta)).thenReturn(true);

        assertThrows(FalhaRequisicaoException.class, () -> pautaSessaoService.iniciarSessao(pautaId));
    }

    @Test
    void deveBuscarSessaoDaPautaComSucesso() {
        Pauta pauta = new Pauta();
        PautaSessao pautaSessao = new PautaSessao();

        when(repository.findByPauta(pauta)).thenReturn(Optional.of(pautaSessao));

        PautaSessao resultado = pautaSessaoService.buscarSessaoDaPauta(pauta);

        assertNotNull(resultado);
        assertEquals(pautaSessao, resultado);
    }

    @Test
    void deveLancarErroQuandoSessaoNaoExiste() {
        Pauta pauta = new Pauta();

        when(repository.findByPauta(pauta)).thenReturn(Optional.empty());

        assertThrows(DadosNaoEncontradosException.class, () -> pautaSessaoService.buscarSessaoDaPauta(pauta));
    }

    @Test
    void deveValidarSessaoAtivaComSucesso() {
        PautaSessao pautaSessao = new PautaSessao();
        pautaSessao.setFimVotacao(LocalDateTime.now().plusMinutes(5));

        assertDoesNotThrow(() -> pautaSessaoService.validarSessaoAtiva(pautaSessao));
    }

    @Test
    void deveLancarErroSeSessaoJaEncerrada() {
        PautaSessao pautaSessao = new PautaSessao();
        pautaSessao.setFimVotacao(LocalDateTime.now().minusMinutes(5));

        assertThrows(FalhaRequisicaoException.class, () -> pautaSessaoService.validarSessaoAtiva(pautaSessao));
    }

}
