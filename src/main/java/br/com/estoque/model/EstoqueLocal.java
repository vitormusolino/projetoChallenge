package br.com.estoque.model;

public class EstoqueLocal {

    private Integer idEstoqueLocal;
    private Integer idProduto;
    private Integer idLocal;
    private Integer quantidade;

    public EstoqueLocal(){}

    public EstoqueLocal(Integer idProduto, Integer idLocal, Integer quantidade){
        this.idProduto = idProduto;
        this.idLocal = idLocal;
        this.quantidade = quantidade;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Integer quantidade) {
        if (quantidade != null && quantidade < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa");
        }
        this.quantidade = quantidade;
    }

    public Integer getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(Integer idLocal) {
        this.idLocal = idLocal;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public Integer getIdEstoqueLocal() {
        return idEstoqueLocal;
    }

    public void setIdEstoqueLocal(Integer idEstoqueLocal) {
        this.idEstoqueLocal = idEstoqueLocal;
    }

    @Override
    public String toString() {
        return "EstoqueLocal{" +
                "idEstoqueLocal=" + idEstoqueLocal +
                ", idProduto=" + idProduto +
                ", idLocal=" + idLocal +
                ", Quantidade=" + quantidade +
                '}';
    }
}
