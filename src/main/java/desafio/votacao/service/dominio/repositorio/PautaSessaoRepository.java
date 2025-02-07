package desafio.votacao.service.dominio.repositorio;

import desafio.votacao.service.dominio.entidade.PautaSessao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PautaSessaoRepository extends JpaRepository<PautaSessao, Long> {
}
