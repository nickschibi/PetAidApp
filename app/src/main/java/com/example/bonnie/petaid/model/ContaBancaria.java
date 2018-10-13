package com.example.bonnie.petaid.model;

public class ContaBancaria {

    private int idConta;
    private int codAgencia;
    private int codConta;
    private String nomeProprietario;
    private String numDoc;
    private int idBanco;
    private int idCategoriaConta;
    private int idLocal;

    public ContaBancaria(int codAgencia, int codConta, String nomeProprietario, String numDoc, int idBanco, int idCategoriaConta, int idLocal) {
        this.codAgencia = codAgencia;
        this.codConta = codConta;
        this.nomeProprietario = nomeProprietario;
        this.numDoc = numDoc;
        this.idBanco = idBanco;
        this.idCategoriaConta = idCategoriaConta;
        this.idLocal = idLocal;
    }

    public ContaBancaria() {

    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    public int getCodAgencia() {
        return codAgencia;
    }

    public void setCodAgencia(int codAgencia) {
        this.codAgencia = codAgencia;
    }

    public int getCodConta() {
        return codConta;
    }

    public void setCodConta(int codConta) {
        this.codConta = codConta;
    }

    public String getNomeProprietario() {
        return nomeProprietario;
    }

    public void setNomeProprietario(String nomeProprietario) {
        this.nomeProprietario = nomeProprietario;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public int getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(int idBanco) {
        this.idBanco = idBanco;
    }

    public int getIdCategoriaConta() {
        return idCategoriaConta;
    }

    public void setIdCategoriaConta(int idCategoriaConta) {
        this.idCategoriaConta = idCategoriaConta;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }
}
