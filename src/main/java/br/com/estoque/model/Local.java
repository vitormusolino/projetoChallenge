package br.com.estoque.model;

public class Local {

    private Integer idLocal;
    private String nomeLocal;

    public Local(){}

    public Local(String nomeLocal){
        this.nomeLocal = nomeLocal;
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

    @Override
    public String toString() {
        return "Local{" + "idLocal= " + idLocal + "nomeLocal= " + nomeLocal + "}";
    }
}
