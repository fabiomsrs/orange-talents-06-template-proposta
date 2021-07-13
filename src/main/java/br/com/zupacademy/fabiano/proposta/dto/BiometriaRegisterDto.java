package br.com.zupacademy.fabiano.proposta.dto;

import br.com.zupacademy.fabiano.proposta.modelo.Biometria;
import br.com.zupacademy.fabiano.proposta.modelo.Cartao;
import br.com.zupacademy.fabiano.proposta.validation.IsBase64;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BiometriaRegisterDto {
    @NotNull
    @NotEmpty
    @IsBase64
    private String fingerprint;

    public BiometriaRegisterDto() {
    }

    public BiometriaRegisterDto(@NotNull @NotEmpty String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public Biometria converter(Cartao cartao) {
        return new Biometria(this.fingerprint, cartao);
    }

    public String getFingerprint() {
        return fingerprint;
    }
}
