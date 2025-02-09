package desafio.votacao.service.controlador;


import desafio.votacao.service.dto.PautaSessaoDto;
import desafio.votacao.service.servico.PautaSessaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pauta/sessao")
public class PautaSessaoController {

    private final PautaSessaoService pautaSessaoService;

    @PostMapping("/iniciar/{pautaId}")
    public ResponseEntity<PautaSessaoDto> iniciarSessao(@PathVariable Long pautaId) {
        return ResponseEntity.ok(pautaSessaoService.iniciarSessao(pautaId));
    }

}
