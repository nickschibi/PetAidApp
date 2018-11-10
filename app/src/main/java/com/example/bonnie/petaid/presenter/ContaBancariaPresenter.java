package com.example.bonnie.petaid.presenter;

import android.app.Activity;

import com.example.bonnie.petaid.ConsomeServico;
import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.model.Banco;
import com.example.bonnie.petaid.model.ContaBancaria;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ContaBancariaPresenter {
    private View view;
    private Activity contexto;

    public ContaBancariaPresenter(View view, Activity contexto){
        this.contexto = contexto;
        this.view = view;
    }


    public void trazBancos(){
        String trazBanco = contexto.getString(R.string.web_service_url) + "banco" ;
        new ConsomeServico(trazBanco, ConsomeServico.Metodo.GET, new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                Type foundType = new TypeToken<List<Banco>>(){}.getType();
                ArrayList<Banco> bancos = new Gson().fromJson(resultado,foundType);
                ArrayList<Banco> listaFinal = new ArrayList<>();
                listaFinal.add(new Banco(0, "Banco"));
                listaFinal.addAll(bancos);
                view.preencheBancos(listaFinal);
            }
        }).executa();
    }

    public void trazContaBancaria(int idLocal){
        String trazContaBancaria = contexto.getString(R.string.web_service_url) + "contaBancaria?id_local=" +idLocal;
        new ConsomeServico(trazContaBancaria, ConsomeServico.Metodo.GET,new ConsomeServico.PosExecucao(){
            @Override
            public void executa(String resultado, int returnCode) {
                Type foundType = new TypeToken<List<ContaBancaria>>(){}.getType();
                ArrayList<ContaBancaria> contasBancarias = new Gson().fromJson(resultado,foundType);
                if(contasBancarias.size()> 0) {

                    view.preencheCamposConta(contasBancarias.get(0));
                }
            }
        }).executa();
    }

    public void  criaContaBancaria(int idLocal, String numDoc,
                                       String agencia, String numContaBancaria, String proprietario,int tipoDoc, int idBanco, int tipoConta){
        ContaBancaria contaBancaria = new ContaBancaria(Integer.parseInt(agencia), Integer.parseInt(numContaBancaria), proprietario, tipoDoc, numDoc, idBanco, tipoConta, idLocal);
        String requestBody = new Gson().toJson(contaBancaria);
        String urlContaBancaria = contexto.getString(R.string.web_service_url) + "contaBancaria";
        Gson gson = new Gson();
        new ConsomeServico(urlContaBancaria, ConsomeServico.Metodo.POST, requestBody, new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                if(returnCode == 200){
                    Type foundType = new TypeToken<ContaBancaria>(){}.getType();
                    ContaBancaria contaBancaria = new Gson().fromJson(resultado,foundType);
                    view.exibeToastSucesso(contaBancaria);
                } else {
                    view.exibeToastMsg("Erro na criação da conta bancária");
                }
            }
        }).executa();
    }

    public void  atualizaContaBancaria(int idConta, int idLocal, String numDoc,
                                   String agencia, String numContaBancaria, String proprietario,int tipoDoc, int idBanco, int tipoConta){
        ContaBancaria contaBancaria = new ContaBancaria(Integer.parseInt(agencia), Integer.parseInt(numContaBancaria), proprietario, tipoDoc, numDoc, idBanco, tipoConta, idLocal);
        String requestBody = new Gson().toJson(contaBancaria);
        String urlContaBancaria = contexto.getString(R.string.web_service_url) + "contaBancaria/" + idConta;
        Gson gson = new Gson();
        new ConsomeServico(urlContaBancaria, ConsomeServico.Metodo.PUT, requestBody, new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                if(returnCode == 200){
                    Type foundType = new TypeToken<ContaBancaria>(){}.getType();
                    ContaBancaria contaBancaria = new Gson().fromJson(resultado,foundType);
                    view.exibeToastSucesso(contaBancaria);
                } else {
                    view.exibeToastMsg("Erro na atualização da conta bancária");
                }
            }
        }).executa();
    }

    public interface View{
        void preencheBancos(ArrayList<Banco> bancos);
        void preencheCamposConta(ContaBancaria contaBancaria);
        void exibeToastMsg(String msg);
        void exibeToastSucesso(ContaBancaria contaBancaria);
    }
}
