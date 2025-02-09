package desafio.votacao.service.dto;

import desafio.votacao.service.enums.SimNao;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VotarDto {

    @NotNull(message = "Campo refAssociado é obrigatório.")
    private String refAssociado;

    @NotNull(message = "Campo voto é obrigatório.")
    private SimNao voto;
}
