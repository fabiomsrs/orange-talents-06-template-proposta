package br.com.zupacademy.fabiano.proposta.dto;

import br.com.zupacademy.fabiano.proposta.modelo.Proposta;
import br.com.zupacademy.fabiano.proposta.validation.Documento;
import br.com.zupacademy.fabiano.proposta.validation.ValorUnico;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

public class PropostaRegisterDto {
    @NotNull
    @NotEmpty
    @Documento
    @ValorUnico(instanceClass = Proposta.class, field = "documento")
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
    @NotNull
    @Positive
    private Double salario;

    public PropostaRegisterDto(@NotNull @NotEmpty @Documento String documento,
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

    public Proposta converter(){
        return new Proposta(UUID.randomUUID().toString(), this.documento, this.email, this.nome, this.endereco, this.salario);
    }
}
