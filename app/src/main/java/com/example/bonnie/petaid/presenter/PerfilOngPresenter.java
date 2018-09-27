package com.example.bonnie.petaid.presenter;

import android.app.Activity;
import android.content.Context;

import com.example.bonnie.petaid.ConsomeServico;
import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.model.Organizacao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class PerfilOngPresenter {

    private Organizacao organizacao;
    private View view;
    private Activity contexto;

    public PerfilOngPresenter(View view, Activity contexto){
        this.contexto = contexto;
        this.view = view;
    }




    public interface View{
        void exibeToastSucesso(int idOrganizacao);
        void exibeToastErro();
    }
}
