package com.example.bonnie.petaid.model;

public class CategoriaConta {
    private int idCategoria;
    private String tipoConta;

    public CategoriaConta() {
        super();
    }

    public CategoriaConta(int idCategoria, String tipoConta) {
        super();
        this.idCategoria = idCategoria;
        this.tipoConta = tipoConta;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    @Override
    public String toString() {
        return tipoConta;
    }

    @Override
    public boolean equals(Object obj) {
        CategoriaConta  cc = (CategoriaConta)obj;
        return this.idCategoria == cc.getIdCategoria();
    }
}
