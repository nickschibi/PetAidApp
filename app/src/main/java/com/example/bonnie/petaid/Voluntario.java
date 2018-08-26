package com.example.bonnie.petaid;

public class Voluntario {

    private int id_voluntario;
    private String nome_voluntario;
    private String email;
    private String telefone_voluntario;
    private String senha_login;

    public Voluntario() {

    }

    public Voluntario(String nome_voluntario, String email, String telefone_voluntario,
                      String senha_login) {
        super();
        this.nome_voluntario = nome_voluntario;
        this.email = email;
        this.telefone_voluntario = telefone_voluntario;
        this.senha_login = senha_login;
    }

    public int getId_voluntario() {
        return id_voluntario;
    }
    public void setId_voluntario(int id_voluntario) {
        this.id_voluntario = id_voluntario;
    }
    public String getNome_voluntario() {
        return nome_voluntario;
    }
    public void setNome_voluntario(String nome_voluntario) {
        this.nome_voluntario = nome_voluntario;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefone_voluntario() {
        return telefone_voluntario;
    }
    public void setTelefone_voluntario(String telefone_voluntario) {
        this.telefone_voluntario = telefone_voluntario;
    }
    public String getSenha_login() {
        return senha_login;
    }
    public void setSenha_login(String senha_login) {
        this.senha_login = senha_login;
    }

}
