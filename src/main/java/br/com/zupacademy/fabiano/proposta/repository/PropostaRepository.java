package br.com.zupacademy.fabiano.proposta.repository;

import br.com.zupacademy.fabiano.proposta.modelo.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {
}
