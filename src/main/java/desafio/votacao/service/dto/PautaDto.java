package desafio.votacao.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import desafio.votacao.service.dominio.entidade.Pauta;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PautaDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotEmpty(message = "O título é obrigatório.")
    private String titulo;

    @NotEmpty(message = "A descrição é obrigatória.")
    private String descricao;

    private Integer tempo;

    public static PautaDto converterEntidade(Pauta pauta) {
        if (pauta == null) {
            return null;
        }

        return PautaDto
                .builder()
                .id(pauta.getId())
                .descricao(pauta.getDescricao())
                .titulo(pauta.getTitulo())
                .tempo(pauta.getTempo())
                .build();

    }

}
