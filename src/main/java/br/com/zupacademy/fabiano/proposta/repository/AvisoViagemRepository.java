package br.com.zupacademy.fabiano.proposta.repository;

import br.com.zupacademy.fabiano.proposta.modelo.AvisoViagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvisoViagemRepository extends JpaRepository<AvisoViagem, String> {
}
