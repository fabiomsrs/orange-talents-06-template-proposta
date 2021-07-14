package br.com.zupacademy.fabiano.proposta.dto;

import br.com.zupacademy.fabiano.proposta.modelo.AvisoViagem;
import br.com.zupacademy.fabiano.proposta.modelo.Cartao;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemRegisterDto {
    @NotNull
    @NotEmpty
    private String destino;
    @NotNull
    @Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate terminoViagem;

    public AvisoViagemRegisterDto() {
    }

    public AvisoViagemRegisterDto(@NotNull @NotEmpty String destino,
                                  @NotNull @Future @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate terminoViagem) {
        this.destino = destino;
        this.terminoViagem = terminoViagem;
    }

    public AvisoViagem converter(String userAgent, String remoteAddr, Cartao cartao) {
        return new AvisoViagem(this.destino, this.terminoViagem, userAgent, remoteAddr, cartao);
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getTerminoViagem() {
        return terminoViagem;
    }
}
