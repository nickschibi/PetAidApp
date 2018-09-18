package com.example.bonnie.petaid.model;

public class Local {
    private int id_local;
    private String nome_responsavel;

    private int id_organizacao;
    private int id_endereco;
    private String telefone_local;

    public Local(int id_local, String nome_responsavel, int id_organizacao, int id_endereco,String telefone_local) {
        this.id_local = id_local;
        this.nome_responsavel = nome_responsavel;
        this.telefone_local = telefone_local;
        this.id_organizacao = id_organizacao;
        this.id_endereco = id_endereco;
    }

    public Local(){

    };

    public Local(String nome_responsavel, int id_organizacao, int id_endereco,String telefone_local) {
        this.nome_responsavel = nome_responsavel;
        this.telefone_local = telefone_local;
        this.id_organizacao = id_organizacao;
        this.id_endereco = id_endereco;
    }

    public int getId_local() {
        return id_local;
    }

    public void setId_local(int id_local) {
        this.id_local = id_local;
    }

    public String getNome_responsavel() {
        return nome_responsavel;
    }

    public void setNome_responsavel(String nome_responsavel) {
        this.nome_responsavel = nome_responsavel;
    }

    public String getTelefone_local() {
        return telefone_local;
    }

    public void setTelefone_local(String telefone_local) {
        this.telefone_local = telefone_local;
    }

    public int getId_organizacao() {
        return id_organizacao;
    }

    public void setId_organizacao(int id_organizacao) {
        this.id_organizacao = id_organizacao;
    }

    public int getId_endereco() {
        return id_endereco;
    }

    public void setId_endereco(int id_endereco) {
        this.id_endereco = id_endereco;
    }
}
