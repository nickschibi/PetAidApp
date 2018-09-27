package com.example.bonnie.petaid.model;

public class Organizacao {

    private int idOrganizacao;
    private String nmCnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private String email;
    private String descricao;
    private String site;
    private String facebook;
    private String instagram;

    public Organizacao() {

    }

    public int getIdOrganizacao() {
        return idOrganizacao;
    }

    public void setIdOrganizacao(int idOrganizacao) {
        this.idOrganizacao = idOrganizacao;
    }

    public String getNmCnpj() {
        return nmCnpj;
    }

    public void setNmCnpj(String nmCnpj) {
        this.nmCnpj = nmCnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Organizacao(int idOrganizacao, String razaoSocial, String nomeFantasia, String nmCnpj, String email,
                       String descricao, String site, String facebook, String instagram) {
        super();
        this.idOrganizacao = idOrganizacao;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.nmCnpj = nmCnpj;
        this.email = email;
        this.descricao = descricao;
        this.site = site;
        this.facebook = facebook;
        this.instagram = instagram;
    }

    public Organizacao(String razaoSocial, String nomeFantasia, String nmCnpj, String email, String descricao, String site, String facebook, String instagram) {
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.nmCnpj = nmCnpj;
        this.email = email;
        this.descricao = descricao;
        this.site = site;
        this.facebook = facebook;
        this.instagram = instagram;
    }
}