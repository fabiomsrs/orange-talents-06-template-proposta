package br.com.zupacademy.fabiano.proposta.repository;

import br.com.zupacademy.fabiano.proposta.modelo.Biometria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BiometriaRepository extends JpaRepository<Biometria, String> {
}
