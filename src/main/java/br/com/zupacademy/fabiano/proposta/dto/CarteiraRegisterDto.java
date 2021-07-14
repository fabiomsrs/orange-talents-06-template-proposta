package br.com.zupacademy.fabiano.proposta.dto;

import br.com.zupacademy.fabiano.proposta.modelo.Cartao;
import br.com.zupacademy.fabiano.proposta.modelo.Carteira;
import br.com.zupacademy.fabiano.proposta.modelo.TipoCarteira;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CarteiraRegisterDto {
    @Email
    @NotNull
    @NotEmpty
    private String email;
    @NotNull
    private TipoCarteira tipoCarteira;

    public CarteiraRegisterDto() {
    }

    public CarteiraRegisterDto(@Email @NotNull @NotEmpty String email,
                               @NotNull TipoCarteira tipoCarteira) {
        this.email = email;
        this.tipoCarteira = tipoCarteira;
    }

    public Carteira converter(Cartao cartao) {
        return new Carteira(this.email, this.tipoCarteira, cartao);
    }

    public TipoCarteira getTipoCarteira() {
        return tipoCarteira;
    }

    public String getEmail() {
        return email;
    }
}
