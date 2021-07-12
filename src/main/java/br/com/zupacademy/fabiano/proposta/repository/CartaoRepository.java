package br.com.zupacademy.fabiano.proposta.repository;

import br.com.zupacademy.fabiano.proposta.modelo.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {
}
