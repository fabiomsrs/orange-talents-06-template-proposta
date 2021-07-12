package br.com.zupacademy.fabiano.proposta.repository;

import br.com.zupacademy.fabiano.proposta.modelo.Proposta;
import br.com.zupacademy.fabiano.proposta.modelo.StatusProposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropostaRepository extends JpaRepository<Proposta, String> {
    List<Proposta> findByStatus(StatusProposta elegivel);

    List<Proposta> findByStatusAndCartaoIsNull(StatusProposta elegivel);
}
