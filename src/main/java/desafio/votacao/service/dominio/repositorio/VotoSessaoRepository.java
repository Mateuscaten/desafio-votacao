package desafio.votacao.service.dominio.repositorio;

import desafio.votacao.service.dominio.entidade.VotoSessao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoSessaoRepository extends JpaRepository<VotoSessao, Long> {
}
