package br.com.zupacademy.fabiano.proposta.dto;

import br.com.zupacademy.fabiano.proposta.modelo.Cartao;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CartaoDto {
    @NotNull
    @NotEmpty
    private String id;
    @NotNull
    @NotEmpty
    private String titular;
    @NotNull
    private Double limite;
    @NotNull
    @NotEmpty
    private String idProposta;
    @NotNull
    private LocalDateTime emitidoEm;

    public CartaoDto() {
    }

    public CartaoDto(@NotNull @NotEmpty String id,
                     @NotNull @NotEmpty String titular,
                     @NotNull Double limite,
                     @NotNull String idProposta,
                     @NotNull LocalDateTime emitidoEm) {
        this.id = id;
        this.titular = titular;
        this.limite = limite;
        this.idProposta = idProposta;
    }

    public String getId() {
        return id;
    }

    public String getTitular() {
        return titular;
    }

    public Double getLimite() {
        return limite;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public Cartao converter(){
        return new Cartao(this.id, this.titular, this.limite, this.emitidoEm);
    }
}
