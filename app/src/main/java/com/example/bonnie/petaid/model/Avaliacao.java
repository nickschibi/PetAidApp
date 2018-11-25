package com.example.bonnie.petaid.model;

import java.util.Date;

public class Avaliacao {
    private long idAvaliacao;
    private Date dtAvaliacao;
    private float notaAvaliacao;
    private long idVoluntariado;


    public Avaliacao(Date dtAvaliacao, float notaAvaliacao, long idVoluntariado) {
        this.dtAvaliacao = dtAvaliacao;
        this.notaAvaliacao = notaAvaliacao;
        this.idVoluntariado = idVoluntariado;
    }

    public Avaliacao() {
    }

    public long getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(long idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public Date getDtAvaliacao() {
        return dtAvaliacao;
    }

    public void setDtAvaliacao(Date dtAvaliacao) {
        this.dtAvaliacao = dtAvaliacao;
    }

    public float getNotaAvaliacao() {
        return notaAvaliacao;
    }

    public void setNotaAvaliacao(float notaAvaliacao) {
        this.notaAvaliacao = notaAvaliacao;
    }

    public long getIdVoluntariado() {
        return idVoluntariado;
    }

    public void setIdVoluntariado(long idVoluntariado) {
        this.idVoluntariado = idVoluntariado;
    }
}
