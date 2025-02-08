package desafio.votacao.service.controlador;

import desafio.votacao.service.dto.PautaDto;
import desafio.votacao.service.servico.PautaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pauta")
public class PautaController {

    private final PautaService pautaService;

    @PostMapping(value = "")
    public ResponseEntity<PautaDto> criarPauta(@RequestBody @Valid PautaDto pautaDto) {
        return ResponseEntity.ok(pautaService.criarPauta(pautaDto));
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PautaDto>> buscarPautas() {
        return ResponseEntity.ok(pautaService.buscarPautas());
    }


}
