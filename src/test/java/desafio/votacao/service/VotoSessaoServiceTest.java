package desafio.votacao.service;

import desafio.votacao.service.dominio.entidade.Pauta;
import desafio.votacao.service.dominio.entidade.PautaSessao;
import desafio.votacao.service.dominio.entidade.VotoSessao;
import desafio.votacao.service.dominio.repositorio.PautaRepository;
import desafio.votacao.service.dominio.repositorio.VotoSessaoRepository;
import desafio.votacao.service.dto.PautaDto;
import desafio.votacao.service.dto.VotarDto;
import desafio.votacao.service.dto.VotoSessaoDto;
import desafio.votacao.service.enums.SimNao;
import desafio.votacao.service.excecao.DadosNaoEncontradosException;
import desafio.votacao.service.excecao.FalhaRequisicaoException;
import desafio.votacao.service.servico.PautaService;
import desafio.votacao.service.servico.PautaSessaoService;
import desafio.votacao.service.servico.VotoSessaoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VotoSessaoServiceTest {

    @InjectMocks
    private VotoSessaoService votoSessaoService;

    @Mock
    private VotoSessaoRepository repository;

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private PautaSessaoService pautaSessaoService;

    @Test
    void deveVotarComSucesso() {
        Long pautaId = 1L;
        String refAssociado = "123";
        VotarDto votarDto = new VotarDto(refAssociado, SimNao.SIM);

        Pauta pauta = new Pauta();
        PautaSessao pautaSessao = new PautaSessao();

        when(pautaRepository.findById(pautaId)).thenReturn(Optional.of(pauta));
        when(pautaSessaoService.buscarSessaoDaPauta(pauta)).thenReturn(pautaSessao);
        doNothing().when(pautaSessaoService).validarSessaoAtiva(pautaSessao);
        when(repository.existsByRefAssociadoAndAndSessao(refAssociado, pautaSessao)).thenReturn(false);

        assertDoesNotThrow(() -> votoSessaoService.votar(votarDto, pautaId));

        verify(repository, times(1)).save(any(VotoSessao.class));
    }

    @Test
    void deveLancarErroQuandoPautaNaoExiste() {
        Long pautaId = 1L;
        VotarDto votarDto = new VotarDto("123", SimNao.SIM);

        when(pautaRepository.findById(pautaId)).thenReturn(Optional.empty());

        assertThrows(FalhaRequisicaoException.class, () -> votoSessaoService.votar(votarDto, pautaId));
    }

    @Test
    void deveLancarErroQuandoAssociadoJaVotou() {
        Long pautaId = 1L;
        String refAssociado = "123";
        VotarDto votarDto = new VotarDto(refAssociado, SimNao.SIM);

        Pauta pauta = new Pauta();
        PautaSessao pautaSessao = new PautaSessao();

        when(pautaRepository.findById(pautaId)).thenReturn(Optional.of(pauta));
        when(pautaSessaoService.buscarSessaoDaPauta(pauta)).thenReturn(pautaSessao);
        doNothing().when(pautaSessaoService).validarSessaoAtiva(pautaSessao);
        when(repository.existsByRefAssociadoAndAndSessao(refAssociado, pautaSessao)).thenReturn(true);

        assertThrows(FalhaRequisicaoException.class, () -> votoSessaoService.votar(votarDto, pautaId));
    }

    @Test
    void deveRetornarVotosZeradosQuandoNaoHaVotos() {
        PautaSessao pautaSessao = new PautaSessao();

        when(repository.findAllBySessao(pautaSessao)).thenReturn(Collections.emptyList());

        VotoSessaoDto resultado = votoSessaoService.contabilizarVotos(pautaSessao);

        assertEquals(0, resultado.getTotalVotos());
        assertEquals(0, resultado.getVotoSim());
        assertEquals(0, resultado.getVotoNao());
    }
}
