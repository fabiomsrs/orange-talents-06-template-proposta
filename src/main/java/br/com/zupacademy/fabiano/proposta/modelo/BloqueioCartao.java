package br.com.zupacademy.fabiano.proposta.modelo;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class BloqueioCartao {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotNull
    @NotEmpty
    private String userAgent;
    @NotNull
    @NotEmpty
    private String ipCliente;
    @OneToOne
    @NotNull
    private Cartao cartao;
    @CreationTimestamp
    private LocalDateTime createdAt;

    public BloqueioCartao() {
    }

    public BloqueioCartao(@NotNull @NotEmpty String userAgent,
                          @NotNull @NotEmpty String ipCliente,
                          @NotNull Cartao cartao) {
        this.userAgent = userAgent;
        this.ipCliente = ipCliente;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getIpCliente() {
        return ipCliente;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
