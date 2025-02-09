package desafio.votacao.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import desafio.votacao.service.dominio.entidade.PautaSessao;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PautaSessaoDto {

    private Long id;

    private Long pautaId;

    private LocalDateTime inicioVotacao;

    private LocalDateTime fimVotacao;

    public static PautaSessaoDto converterEntidade(PautaSessao pautaSessao) {
        if (pautaSessao == null) {
            return null;
        }

        return PautaSessaoDto
                .builder()
                .id(pautaSessao.getId())
                .pautaId(pautaSessao.getPauta().getId())
                .inicioVotacao(pautaSessao.getInicioVotacao())
                .fimVotacao(pautaSessao.getFimVotacao())
                .build();

    }

}
