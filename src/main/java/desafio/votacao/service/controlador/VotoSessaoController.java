package desafio.votacao.service.controlador;

import desafio.votacao.service.dto.VotarDto;
import desafio.votacao.service.servico.VotoSessaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/voto/sessao")
public class VotoSessaoController {

    private final VotoSessaoService votoSessaoService;

    @PostMapping("/{pautaId}")
    public ResponseEntity<Void> votar(@PathVariable Long pautaId, @Valid @RequestBody VotarDto votarDto) {
        votoSessaoService.votar(votarDto, pautaId);
        return ResponseEntity.ok().build();
    }

}
