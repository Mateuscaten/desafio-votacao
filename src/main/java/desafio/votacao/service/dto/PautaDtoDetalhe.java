package desafio.votacao.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PautaDtoDetalhe {

    private Long id;

    private String titulo;

    private String descricao;

    private Integer tempo;

    private PautaSessaoDto pautaSessao;

    private VotoSessaoDto votoSessao;
}
