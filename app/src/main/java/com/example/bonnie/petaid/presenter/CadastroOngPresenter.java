package com.example.bonnie.petaid.presenter;

import android.app.Activity;
import android.content.Context;

import com.example.bonnie.petaid.ConsomeServico;
import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.model.Organizacao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class CadastroOngPresenter {

    private Organizacao organizacao;
    private View view;
    private Activity contexto;

    public CadastroOngPresenter(View view, Activity contexto){
        this.contexto = contexto;
        this.view = view;
    }

    public void cadastraOrganizacao(String nm_cnpj,String razao_social, String nome_fantasia, String email, String desricao,String site, String facebook, String instagram){

        organizacao = new Organizacao( nm_cnpj, razao_social,  nome_fantasia,  email,  desricao, site,  facebook,  instagram);

        String requestBody = new Gson().toJson(organizacao);
        String urlVoluntario = contexto.getString(R.string.web_service_url) + "organizacao";
        new ConsomeServico(urlVoluntario, ConsomeServico.Metodo.POST, requestBody, new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                if(returnCode == 200){
                    Type foundType = new TypeToken<Organizacao>(){}.getType();
                    organizacao = new Gson().fromJson(resultado,foundType);
                    view.exibeToastSucesso(organizacao.getId_organizacao());

                } else {
                    view.exibeToastErro();
                }
            }
        }).executa();

    }


    public interface View{
        void exibeToastSucesso(int idOrganizacao);
        void exibeToastErro();
    }
}
