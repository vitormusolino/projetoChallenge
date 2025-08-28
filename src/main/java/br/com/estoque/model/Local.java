package br.com.estoque.model;

public class Local {

    private Integer idLocal;
    private String nomeLocal;
    private String descricao;

    public Local(){}

    public Local(String nomeLocal, String descricao){
        this.nomeLocal = nomeLocal;
        this.descricao = descricao;
    }

    public Integer getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(Integer idLocal) {
        this.idLocal = idLocal;
    }

    public String getNomeLocal() {
        return nomeLocal;
    }

    public void setNomeLocal(String nomeLocal) {
        this.nomeLocal = nomeLocal;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Local{" + "idLocal= " + idLocal +
                "; nomeLocal= " + nomeLocal +
                "; descricao=" + descricao +
                "}";
    }
}
