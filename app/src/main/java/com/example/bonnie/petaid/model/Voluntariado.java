package com.example.bonnie.petaid.model;

import java.util.Date;

public class Voluntariado {
    private long idVoluntariado;
    private Date dtVoluntariado;
    private int ativo;
    private long idVoluntario;
    private long idLocal;
    private Voluntario voluntario;
    private Local local;

    public Voluntariado( Date dtVoluntariado, int ativo, long idVoluntario, long idLocal) {
        this.idVoluntariado = idVoluntariado;
        this.dtVoluntariado = dtVoluntariado;
        this.ativo = ativo;
        this.idVoluntario = idVoluntario;
        this.idLocal = idLocal;
    }

    public Voluntariado() {

    }

    public long getIdVoluntariado() {
        return idVoluntariado;
    }

    public void setIdVoluntariado(long idVoluntariado) {
        this.idVoluntariado = idVoluntariado;
    }

    public Date getDtVoluntariado() {
        return dtVoluntariado;
    }

    public void setDtVoluntariado(Date dtVoluntariado) {
        this.dtVoluntariado = dtVoluntariado;
    }

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

    public long getIdVoluntario() {
        return idVoluntario;
    }

    public void setIdVoluntario(long idVoluntario) {
        this.idVoluntario = idVoluntario;
    }

    public long getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(long idLocal) {
        this.idLocal = idLocal;
    }

    public Voluntario getVoluntario() {
        return voluntario;
    }

    public void setVoluntario(Voluntario voluntario) {
        this.voluntario = voluntario;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }
}
