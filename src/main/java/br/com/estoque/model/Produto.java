package br.com.estoque.model;

import java.time.LocalDate;

public class Produto {

    private Integer idProduto;
    private String nome;
    private String descricao;
    private String codigoBarras;
    private LocalDate validade;
    private String unidade;
    private Integer quantidadeMinima;

    public Produto() {}

    public Produto(String nome, String descricao, String codigoBarras,
                   LocalDate validade, String unidade, Integer quantidadeMinima) {
        this.nome = nome;
        this.descricao = descricao;
        this.codigoBarras = codigoBarras;
        this.validade = validade;
        this.unidade = unidade;
        this.quantidadeMinima = quantidadeMinima;
    }

    public Produto(Integer idProduto, String nome, String descricao, String codigoBarras,
                   LocalDate validade, String unidade, Integer quantidadeMinima) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.descricao = descricao;
        this.codigoBarras = codigoBarras;
        this.validade = validade;
        this.unidade = unidade;
        this.quantidadeMinima = quantidadeMinima;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public Integer getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public void setQuantidadeMinima(Integer quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "idProduto=" + idProduto +
                ", nome='" + nome + '\'' +
                ", codigoBarras='" + codigoBarras + '\'' +
                ", validade=" + validade +
                ", unidade='" + unidade + '\'' +
                ", quantidadeMinima=" + quantidadeMinima +
                '}';
    }
}
