package br.com.zupacademy.fabiano.proposta.repository;

import br.com.zupacademy.fabiano.proposta.modelo.BloqueioCartao;
import br.com.zupacademy.fabiano.proposta.modelo.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BloqueioCartaoRepository extends JpaRepository<BloqueioCartao, String> {
    List<BloqueioCartao> findByCartao(Cartao cartao);
}
