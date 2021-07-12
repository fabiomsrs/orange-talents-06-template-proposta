package br.com.zupacademy.fabiano.proposta.modelo;

import br.com.zupacademy.fabiano.proposta.validation.Documento;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Proposta {
    @Id
    @GeneratedValue
    private Long id;
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

    private StatusProposta status;

    @NotNull
    @Positive
    private Double salario;

    public Proposta() {
    }

    public Proposta(@NotNull @NotEmpty @Documento String documento,
                    @NotNull @NotEmpty @Email String email,
                    @NotNull @NotEmpty String nome,
                    @NotNull @NotEmpty String endereco,
                    @NotNull @Positive Double salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Long getId() {
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
}
