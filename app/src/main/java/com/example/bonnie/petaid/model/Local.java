package com.example.bonnie.petaid.model;

public class Local {
    private int idLocal;
    private String nomeResponsavel;

    private int idOrganizacao;
    private int idEndereco;
    private String telefoneLocal;
    private float mediaNota;
    private Endereco endereco;

    public Local(int idLocal, String nomeResponsavel, int idOrganizacao, int idEndereco,String telefoneLocal) {
        this.idLocal = idLocal;
        this.nomeResponsavel = nomeResponsavel;
        this.telefoneLocal = telefoneLocal;
        this.idOrganizacao = idOrganizacao;
        this.idEndereco = idEndereco;
    }

    public Local(){

    };

    public Local(String nomeResponsavel, int idOrganizacao, int idEndereco,String telefoneLocal) {
        this.nomeResponsavel = nomeResponsavel;
        this.telefoneLocal = telefoneLocal;
        this.idOrganizacao = idOrganizacao;
        this.idEndereco = idEndereco;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public float getMediaNota() {
        return mediaNota;
    }

    public void setMediaNota(float mediaNota) {
        this.mediaNota = mediaNota;
    }

    public String getTelefoneLocal() {
        return telefoneLocal;
    }

    public void setTelefoneLocal(String telefoneLocal) {
        this.telefoneLocal = telefoneLocal;
    }

    public int getIdOrganizacao() {
        return idOrganizacao;
    }

    public void setIdOrganizacao(int idOrganizacao) {
        this.idOrganizacao = idOrganizacao;
    }

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String toString(){
        return this.getNomeResponsavel() + "," + this.getIdEndereco() + "," + this.getTelefoneLocal();
    }
}
