package com.example.bonnie.petaid.model;

public class Necessidade {
    private int idNecessidade;
    private String descricaoNecessidade;
    private boolean flagPrecisaObs;

    public Necessidade() {
    }

    public Necessidade(int idNecessidade, String descricaoNecessidade) {
        this.idNecessidade = idNecessidade;
        this.descricaoNecessidade = descricaoNecessidade;

    }

    public int getIdNecessidade() {
        return idNecessidade;
    }

    public void setIdNecessidade(int idNecessidade) {
        this.idNecessidade = idNecessidade;
    }

    public String getDescricaoNecessidade() {
        return descricaoNecessidade;
    }

    public void setDescricaoNecessidade(String descricaoNecessidade) {
        this.descricaoNecessidade = descricaoNecessidade;
    }

    public boolean getFlagPrecisaObs() {
        return flagPrecisaObs;
    }

    public void setFlagPrecisaObs(boolean flagPrecisaObs) {
        this.flagPrecisaObs = flagPrecisaObs;
    }
}
