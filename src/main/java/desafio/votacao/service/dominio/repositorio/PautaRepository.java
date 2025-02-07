package desafio.votacao.service.dominio.repositorio;

import desafio.votacao.service.dominio.entidade.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PautaRepository extends JpaRepository<Pauta, Long> {
}
