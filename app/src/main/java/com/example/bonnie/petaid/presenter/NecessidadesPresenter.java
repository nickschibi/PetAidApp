package com.example.bonnie.petaid.presenter;

import android.app.Activity;
import android.view.View;

import com.example.bonnie.petaid.ConsomeServico;
import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.model.Necessidade;
import com.example.bonnie.petaid.model.NecessidadeLocal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NecessidadesPresenter {
    private View view;
    private Activity contexto;

    public NecessidadesPresenter(View view, Activity contexto){
        this.contexto = contexto;
        this.view = view;
    }


    public void trazNecessidades(){
        String trazNecessidades = contexto.getString(R.string.web_service_url) + "necessidades";
        new ConsomeServico(trazNecessidades, ConsomeServico.Metodo.GET,new ConsomeServico.PosExecucao(){
            @Override
            public void executa(String resultado, int returnCode) {
                Type foundType = new TypeToken<List<Necessidade>>(){}.getType();
                ArrayList<Necessidade> necessidades = new Gson().fromJson(resultado,foundType);
                if(necessidades.size()> 0) {

                    view.preencheCheckNecessidades(necessidades);
                }
            }
        }).executa();
    }

    public void trazNecessidadesLocal(int idLocal){
        String trazNecessidadesLocal = contexto.getString(R.string.web_service_url) + "necessidadesLocal?id_local=" + idLocal;
        new ConsomeServico(trazNecessidadesLocal, ConsomeServico.Metodo.GET,new ConsomeServico.PosExecucao(){
            @Override
            public void executa(String resultado, int returnCode) {
                Type foundType = new TypeToken<List<NecessidadeLocal>>(){}.getType();
                ArrayList<NecessidadeLocal> necessidadesLocal = new Gson().fromJson(resultado,foundType);
                view.preencheCheckNecessidadesLocal(necessidadesLocal);
            }
        }).executa();
    }


    public void salvaNecessidadesLocal(List<NecessidadeLocal> necessidadesLocal){
        String requestBody = new Gson().toJson(necessidadesLocal);
        String trazNecessidadesLocal = contexto.getString(R.string.web_service_url) + "necessidadesLocal";
        new ConsomeServico(trazNecessidadesLocal, ConsomeServico.Metodo.POST, requestBody, new ConsomeServico.PosExecucao(){
            @Override
            public void executa(String resultado, int returnCode) {
                view.exibeToastMsg("OK Salvo");
            }
        }).executa();
    }

    public void removeNecessidadesLocal(List<NecessidadeLocal> necessidadesLocal){
        String requestBody = new Gson().toJson(necessidadesLocal);
        String trazNecessidadesLocal = contexto.getString(R.string.web_service_url) + "necessidadesLocal";
        new ConsomeServico(trazNecessidadesLocal, ConsomeServico.Metodo.DELETE_WITH_BODY, requestBody, new ConsomeServico.PosExecucao(){
            @Override
            public void executa(String resultado, int returnCode) {
                view.exibeToastMsg("OK Removido");
            }
        }).executa();
    }

    public interface View{
        void exibeToastMsg(String msg);
        void preencheCheckNecessidades(ArrayList<Necessidade> necessidades);
        void preencheCheckNecessidadesLocal(ArrayList<NecessidadeLocal> necessidadesLocal);
    }


}
