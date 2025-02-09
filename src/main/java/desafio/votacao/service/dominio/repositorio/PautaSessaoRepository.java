package desafio.votacao.service.dominio.repositorio;

import desafio.votacao.service.dominio.entidade.Pauta;
import desafio.votacao.service.dominio.entidade.PautaSessao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PautaSessaoRepository extends JpaRepository<PautaSessao, Long> {

    boolean existsByPauta(Pauta pauta);

    Optional<PautaSessao> findByPauta(Pauta pauta);
}
