package desafio.votacao.service.dominio.entidade;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class VotoSessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sessao_id", nullable = false)
    private PautaSessao sessao;

    @Column(name = "ref_associado", length = 10, nullable = false)
    private String refAssociado;

    @Column(nullable = false)
    private Boolean voto;
}
