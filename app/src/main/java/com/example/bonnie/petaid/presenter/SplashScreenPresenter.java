package com.example.bonnie.petaid.presenter;

import android.app.Activity;

import com.example.bonnie.petaid.ConsomeServico;
import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.model.Voluntario;

public class SplashScreenPresenter {
    private View view;
    private Activity contexto;
    private Voluntario voluntario;

    public SplashScreenPresenter(View view, Activity contexto){
        this.contexto = contexto;
        this.view = view;
    }

    public String verificaUsuario(String email){
        try {
            String url = contexto.getString(R.string.web_service_url) + "user/" + email;
            String s = new ConsomeServico(url, ConsomeServico.Metodo.GET).executaSincrono();
            return s;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "nada";
        }

    }

    public interface View{
    }
}
