package desafio.votacao.service.dominio.repositorio;

import desafio.votacao.service.dominio.entidade.PautaSessao;
import desafio.votacao.service.dominio.entidade.VotoSessao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VotoSessaoRepository extends JpaRepository<VotoSessao, Long> {

    boolean existsByRefAssociadoAndAndSessao(String refAssociado, PautaSessao pautaSessao);

    List<VotoSessao> findAllBySessao(PautaSessao pautaSessao);
}
