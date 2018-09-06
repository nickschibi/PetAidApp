package com.example.bonnie.petaid.model;

public class Organizacao {

    private int id_organizacao;
    private String nm_cnpj;
    private String razao_social;
    private String nome_fantasia;
    private String email;
    private String senha_login;
    private String descricao;
    private String site;
    private String facebook;
    private String instagram;

    public Organizacao() {

    }

    public int getId_organizacao() {
        return id_organizacao;
    }

    public void setId_organizacao(int id_organizacao) {
        this.id_organizacao = id_organizacao;
    }

    public String getNm_cnpj() {
        return nm_cnpj;
    }

    public void setNm_cnpj(String nm_cnpj) {
        this.nm_cnpj = nm_cnpj;
    }

    public String getRazao_social() {
        return razao_social;
    }

    public void setRazao_social(String razao_social) {
        this.razao_social = razao_social;
    }

    public String getNome_fantasia() {
        return nome_fantasia;
    }

    public void setNome_fantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha_login() {
        return senha_login;
    }

    public void setSenha_login(String senha_login) {
        this.senha_login = senha_login;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public Organizacao(int id_organizacao, String nm_cnpj, String razao_social, String nome_fantasia, String email,
                       String senha_login, String descricao, String site, String facebook, String instagram) {
        super();
        this.id_organizacao = id_organizacao;
        this.nm_cnpj = nm_cnpj;
        this.razao_social = razao_social;
        this.nome_fantasia = nome_fantasia;
        this.email = email;
        this.senha_login = senha_login;
        this.descricao = descricao;
        this.site = site;
        this.facebook = facebook;
        this.instagram = instagram;
    }

}