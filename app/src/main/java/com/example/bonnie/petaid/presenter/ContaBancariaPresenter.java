package com.example.bonnie.petaid.presenter;

import android.app.Activity;

import com.example.bonnie.petaid.ConsomeServico;
import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.model.Banco;
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

    public interface View{
        void preencheBancos(ArrayList<Banco> bancos);
    }
}
