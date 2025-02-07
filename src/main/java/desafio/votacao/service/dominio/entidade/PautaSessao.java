package desafio.votacao.service.dominio.entidade;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class PautaSessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pauta_id", nullable = false)
    private Pauta pauta;

    @Column(name = "inicio_votacao", nullable = false)
    private LocalDateTime inicioVotacao;

    @Column(name = "fim_votacao", nullable = false)
    private LocalDateTime fimVotacao;

}
