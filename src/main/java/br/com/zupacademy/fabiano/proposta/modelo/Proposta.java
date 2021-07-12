package br.com.zupacademy.fabiano.proposta.modelo;

import br.com.zupacademy.fabiano.proposta.validation.Documento;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Proposta {
    @Id
    @NotNull
    private String id;
    @NotNull
    @NotEmpty
    @Documento
    private String documento;
    @NotNull
    @NotEmpty
    @Email
    private String email;
    @NotNull
    @NotEmpty
    private String nome;
    @NotNull
    @NotEmpty
    private String endereco;
    @Enumerated(value = EnumType.STRING)
    private StatusProposta status = StatusProposta.NAO_ELEGIVEL;
    @NotNull
    @Positive
    private Double salario;
    @OneToOne
    @JoinColumn(unique = true)
    Cartao cartao;

    public Proposta() {
    }

    public Proposta(@NotNull String id,
                    @NotNull @NotEmpty @Documento String documento,
                    @NotNull @NotEmpty @Email String email,
                    @NotNull @NotEmpty String nome,
                    @NotNull @NotEmpty String endereco,
                    @NotNull @Positive Double salario) {
        this.id = id;
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public String getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public Double getSalario() {
        return salario;
    }

    public StatusProposta getStatus() {
        return status;
    }

    public void setStatus(StatusProposta status) {
        this.status = status;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }
}
