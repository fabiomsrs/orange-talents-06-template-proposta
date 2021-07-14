package br.com.zupacademy.fabiano.proposta.modelo;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class AvisoViagem {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotNull
    @NotEmpty
    private String destinho;
    @NotNull
    private LocalDate terminoViagem;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @NotNull
    @NotEmpty
    private String userAgent;
    @NotNull
    @NotEmpty
    private String ipCliente;
    @NotNull
    @ManyToOne
    private Cartao cartao;

    public AvisoViagem() {
    }

    public AvisoViagem(String destinho,
                       @NotNull LocalDate terminoViagem,
                       @NotNull @NotEmpty String userAgent,
                       @NotNull @NotEmpty String ipCliente,
                       @NotNull Cartao cartao) {
        this.destinho = destinho;
        this.terminoViagem = terminoViagem;
        this.userAgent = userAgent;
        this.ipCliente = ipCliente;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    public String getDestinho() {
        return destinho;
    }

    public LocalDate getTerminoViagem() {
        return terminoViagem;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getIpCliente() {
        return ipCliente;
    }

    public Cartao getCartao() {
        return cartao;
    }
}
