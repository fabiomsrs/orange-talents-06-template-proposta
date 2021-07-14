package br.com.zupacademy.fabiano.proposta.repository;

import br.com.zupacademy.fabiano.proposta.modelo.Cartao;
import br.com.zupacademy.fabiano.proposta.modelo.Carteira;
import br.com.zupacademy.fabiano.proposta.modelo.TipoCarteira;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarteiraRepository extends JpaRepository<Carteira, String> {
    List<Carteira> findByCartaoAndTipoCarteira(Cartao cartao, TipoCarteira tipoCarteira);
}