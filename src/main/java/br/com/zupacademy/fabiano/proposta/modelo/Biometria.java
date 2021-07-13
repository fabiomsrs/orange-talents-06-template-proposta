package br.com.zupacademy.fabiano.proposta.modelo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Biometria {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotNull
    private String fingerprint;
    @NotNull
    @ManyToOne
    private Cartao cartao;

    public Biometria() {
    }

    public Biometria(String fingerprint, Cartao cartao) {
        this.fingerprint = fingerprint;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public Cartao getCartao() {
        return cartao;
    }
}
