package br.com.zupacademy.fabiano.proposta.dto;

import br.com.zupacademy.fabiano.proposta.modelo.Cartao;
import br.com.zupacademy.fabiano.proposta.modelo.Proposta;
import br.com.zupacademy.fabiano.proposta.modelo.StatusProposta;
import org.springframework.security.crypto.encrypt.TextEncryptor;

public class PropostaDetalheDto {
    private String documento;
    private String email;
    private String nome;
    private String endereco;
    private StatusProposta status;
    private Double salario;
    Cartao cartao;

    public PropostaDetalheDto(Proposta proposta, TextEncryptor encryptor) {
        this.documento = encryptor.decrypt(proposta.getDocumento());
        this.email = proposta.getEmail();
        this.nome = proposta.getNome();
        this.endereco = proposta.getEndereco();
        this.status = proposta.getStatus();
        this.salario = proposta.getSalario();
        this.cartao = proposta.getCartao();
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

    public StatusProposta getStatus() {
        return status;
    }

    public Double getSalario() {
        return salario;
    }

    public Cartao getCartao() {
        return cartao;
    }
}
