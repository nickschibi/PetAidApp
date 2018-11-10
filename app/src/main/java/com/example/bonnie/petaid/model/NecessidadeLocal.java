package com.example.bonnie.petaid.model;

public class NecessidadeLocal {
    private String observacao;
    private int idLocal;
    private int idNecessidade;

    public NecessidadeLocal(String observacao, int idLocal, int idNecessidade) {
        this.observacao = observacao;
        this.idLocal = idLocal;
        this.idNecessidade = idNecessidade;
    }

    public NecessidadeLocal(int idLocal, int idNecessidade) {
        this.idLocal = idLocal;
        this.idNecessidade = idNecessidade;
    }

    public NecessidadeLocal() {
    }

    public String getObservacao() {
        return observacao;
    }
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    public int getIdLocal() {
        return idLocal;
    }
    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }
    public int getIdNecessidade() {
        return idNecessidade;
    }
    public void setIdNecessidade(int idNecessidade) {
        this.idNecessidade = idNecessidade;
    }
}
