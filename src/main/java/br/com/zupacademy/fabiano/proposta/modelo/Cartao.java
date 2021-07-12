package br.com.zupacademy.fabiano.proposta.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotEmpty
    private String numeroCartao;
    @NotNull
    @NotEmpty
    private String titular;
    @NotNull
    private Double limite;
    @NotNull
    private LocalDateTime createdAt;

    public Cartao() {
    }

    public Cartao(@NotNull @NotEmpty String numeroCartao,
                  @NotNull @NotEmpty String titular,
                  @NotNull Double limite,
                  @NotNull LocalDateTime createdAt) {
        this.numeroCartao = numeroCartao;
        this.titular = titular;
        this.limite = limite;
        this.createdAt = createdAt;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public String getTitular() {
        return titular;
    }

    public Double getLimite() {
        return limite;
    }
}
