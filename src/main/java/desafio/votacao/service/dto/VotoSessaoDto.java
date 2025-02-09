package desafio.votacao.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VotoSessaoDto {

    private Long totalVotos;

    private Long votoSim;

    private Long votoNao;
}
