package br.com.zupacademy.fabiano.proposta.modelo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Carteira {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Email
    @NotNull
    @NotEmpty
    private String email;
    @Enumerated(value = EnumType.STRING)
    private TipoCarteira tipoCarteira;
    @NotNull
    @ManyToOne
    private Cartao cartao;

    public Carteira() {
    }

    public Carteira(String email, TipoCarteira tipoCarteira, Cartao cartao) {
        this.email = email;
        this.tipoCarteira = tipoCarteira;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public TipoCarteira getTipoCarteira() {
        return tipoCarteira;
    }

    public Cartao getCartao() {
        return cartao;
    }
}
