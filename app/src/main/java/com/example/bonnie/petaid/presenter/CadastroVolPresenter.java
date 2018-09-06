package com.example.bonnie.petaid.presenter;

import android.content.Context;

import com.example.bonnie.petaid.ConsomeServico;
import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.model.Voluntario;
import com.google.gson.Gson;

public class CadastroVolPresenter {

    private Voluntario voluntario;
    private View view;
    private Context contexto;

    public CadastroVolPresenter(View view, Context contexto){
        this.contexto = contexto;
        this.view = view;
    }

    public void cadastraVoluntario(String nome, String email, String telefone){

        voluntario = new Voluntario(nome, email, telefone);

        String requestBody = new Gson().toJson(voluntario);
        String urlVoluntario = contexto.getString(R.string.web_service_url) + "voluntario";
        new ConsomeServico(urlVoluntario, ConsomeServico.Metodo.POST, requestBody, new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                if(returnCode == 200){
                    view.exibeToastSucesso();
                } else {
                    view.exibeToastErro();
                }
            }
        }).executa();

    }


    public interface View{
        void exibeToastSucesso();
        void exibeToastErro();
    }
}
