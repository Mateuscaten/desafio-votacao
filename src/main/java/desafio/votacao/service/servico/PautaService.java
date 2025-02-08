package desafio.votacao.service.servico;

import desafio.votacao.service.dominio.entidade.Pauta;
import desafio.votacao.service.dominio.repositorio.PautaRepository;
import desafio.votacao.service.dto.PautaDto;
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
    private final PautaRepository pautaRepository;

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

        List<Pauta> pautas = pautaRepository.findAll();
        if (pautas.isEmpty()) {
            return Collections.emptyList();
        }

        return pautas.stream().map(PautaDto::converterEntidade).collect(Collectors.toList());
    }

}
