package com.example.bonnie.petaid.presenter;

import android.app.Activity;

import com.example.bonnie.petaid.ConsomeServico;
import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.model.Endereco;
import com.example.bonnie.petaid.model.Local;
import com.example.bonnie.petaid.model.Organizacao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CadastroOngEnderecosPresenter {
    private ArrayList<Local> listaLocais;

    private View view;
    private Activity contexto;

    public CadastroOngEnderecosPresenter(View view, Activity contexto){
        this.contexto = contexto;
        this.view = view;
    }

    public void pegaListaLocais(int idOrganizacao){

        String urlLocal = contexto.getString(R.string.web_service_url) + "local?id_organizacao="+ idOrganizacao ;
        new ConsomeServico(urlLocal, ConsomeServico.Metodo.GET, new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                if(returnCode == 200){
                    Type foundType = new TypeToken<List<Local>>(){}.getType();
                    listaLocais = new Gson().fromJson(resultado,foundType);
                    view.exibeToastSucesso();
                    view.atualizaListaLocais(listaLocais);
                } else {
                    view.exibeToastErro();
                }
            }
        }).executa();

    }


    public  void apagaLocal(Local local){


//        String urlEndereco = contexto.getString(R.string.web_service_url) + "endereco/" + local.getEndereco().getIdEndereco();
//        new ConsomeServico(urlEndereco, ConsomeServico.Metodo.DELETE, new ConsomeServico.PosExecucao() {
//            @Override
//            public void executa(String resultado, int returnCode) {
//                if(returnCode == 200){
//                    String urlLocal = contexto.getString(R.string.web_service_url) + "local/" + local.getIdLocal() + "?force=true";
//                    new ConsomeServico(urlLocal, ConsomeServico.Metodo.DELETE, new ConsomeServico.PosExecucao() {
//                        @Override
//                        public void executa(String resultado, int returnCode) {
//                            if(returnCode == 200){
//                                //TODO
//                            } else {
//                                //TODO
//                            }
//                        }
//                    }).executa();
//                    //TODO
//                } else {
//                    //TODO
//                }
//            }
//        }).executa();


        String urlLocal = contexto.getString(R.string.web_service_url) + "local/" + local.getIdLocal() + "?force=true";
        new ConsomeServico(urlLocal, ConsomeServico.Metodo.DELETE, new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                if(returnCode == 200){
                    //TODO
                } else {
                    //TODO
                }
            }
        }).executa();
    }

    public interface View{
        void atualizaListaLocais(ArrayList<Local> listaLocais);
        void exibeToastSucesso();
        void exibeToastErro();
    }

    public void excluiContaBancaria(int idConta)throws ExecutionException, InterruptedException{
        String urlConta = contexto.getString(R.string.web_service_url) + "contaBancaria/"+idConta;
        ConsomeServico servico = new ConsomeServico(urlConta, ConsomeServico.Metodo.DELETE);
        String retorno = servico.executaSincrono();
        int rc = servico.getReturnCode();


    }


}
