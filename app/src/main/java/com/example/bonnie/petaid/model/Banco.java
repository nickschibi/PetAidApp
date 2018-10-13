package com.example.bonnie.petaid.model;

public class Banco {

    private int idBanco;
    private String nomeBanco;

    public void setIdBanco(int idBanco) {
        this.idBanco = idBanco;
    }

    public void setNomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco;
    }

    public int getIdBanco() {
        return idBanco;
    }
    public String getNomeBanco() {
        return nomeBanco;
    }

    @Override
    public String toString() {
        return nomeBanco ;
    }

    public Banco() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Banco(int idBanco) {
        super();
        this.idBanco = idBanco;
    }

    public Banco(int idBanco, String nomeBanco) {
        super();
        this.idBanco = idBanco;
        this.nomeBanco = nomeBanco;
    }

    @Override
    public boolean equals(Object obj) {
        Banco b = (Banco)obj;
        return this.idBanco == b.getIdBanco();
    }
}
