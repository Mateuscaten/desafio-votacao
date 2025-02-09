package desafio.votacao.service;

import desafio.votacao.service.dominio.entidade.Pauta;
import desafio.votacao.service.dominio.entidade.PautaSessao;
import desafio.votacao.service.dominio.repositorio.PautaRepository;
import desafio.votacao.service.dto.PautaDto;
import desafio.votacao.service.dto.PautaDtoDetalhe;
import desafio.votacao.service.dto.VotoSessaoDto;
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
public class PautaServiceTest {

    @Mock
    private PautaRepository repository;

    @Mock
    private PautaSessaoService pautaSessaoService;

    @Mock
    private VotoSessaoService votoSessaoService;

    @InjectMocks
    private PautaService pautaService;

    @Test
    void deveCriarPautaComSucesso() {
        PautaDto pautaDto = new PautaDto(1L, "Título", "Descrição", 5);
        Pauta pauta = new Pauta(1L, "Título", "Descrição", 5);

        when(repository.save(any(Pauta.class))).thenReturn(pauta);

        PautaDto resultado = pautaService.criarPauta(pautaDto);

        assertNotNull(resultado);
        assertEquals("Título", resultado.getTitulo());
        assertEquals(5, resultado.getTempo());
        verify(repository, times(1)).save(any(Pauta.class));
    }

    @Test
    void deveLancarErroSeTempoForMenorQue1() {
        PautaDto pautaDto = new PautaDto(1L, "Título", "Descrição", 0);

        Exception exception = assertThrows(FalhaRequisicaoException.class, () -> {
            pautaService.criarPauta(pautaDto);
        });

        assertEquals("O tempo da votação não pode ser menor que 1 minuto.", exception.getMessage());
    }

    @Test
    void deveDefinirTempoPadraoSeNaoForInformado() {
        PautaDto pautaDto = new PautaDto(1L, "Título", "Descrição", null);
        Pauta pauta = new Pauta(1L, "Título", "Descrição", 1);

        when(repository.save(any(Pauta.class))).thenReturn(pauta);

        PautaDto resultado = pautaService.criarPauta(pautaDto);

        assertEquals(1, resultado.getTempo());
    }

    @Test
    void deveRetornarListaDePautas() {
        List<Pauta> pautas = List.of(new Pauta(1L, "Título", "Descrição", 10));

        when(repository.findAll()).thenReturn(pautas);

        List<PautaDto> resultado = pautaService.buscarPautas();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Título", resultado.get(0).getTitulo());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremPautas() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<PautaDto> resultado = pautaService.buscarPautas();

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveBuscarPautaPorIdComSucesso() {
        Pauta pauta = new Pauta(1L, "Título", "Descrição", 10);
        when(repository.findById(1L)).thenReturn(Optional.of(pauta));

        Pauta resultado = pautaService.buscarPautaPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void deveLancarErroSePautaNaoExistir() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(DadosNaoEncontradosException.class, () -> {
            pautaService.buscarPautaPorId(1L);
        });

        assertEquals("Pauta não encontrada.", exception.getMessage());
    }
}
