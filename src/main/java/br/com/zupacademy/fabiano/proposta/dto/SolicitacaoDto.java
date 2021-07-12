package br.com.zupacademy.fabiano.proposta.dto;

public class SolicitacaoDto {
    private String documento;
    private String nome;
    private String resultadoSolicitacao;
    private String idProposta;

    public SolicitacaoDto() {
    }

    public SolicitacaoDto(String documento, String nome, String resultadoSolicitacao, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.resultadoSolicitacao = resultadoSolicitacao;
        this.idProposta = idProposta;
    }

    public String getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
