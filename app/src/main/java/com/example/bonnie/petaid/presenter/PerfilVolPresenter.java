package com.example.bonnie.petaid.presenter;

import android.app.Activity;

import com.example.bonnie.petaid.ConsomeServico;
import com.example.bonnie.petaid.PetAidApplication;
import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.model.Voluntario;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PerfilVolPresenter {
    private View view;
    private Activity contexto;
    private Voluntario voluntario;

    public PerfilVolPresenter(View view, Activity contexto){
        this.contexto = contexto;
        this.view = view;
    }

    public void getVoluntario(){

        String trazVoluntario = contexto.getString(R.string.web_service_url) + "voluntario?email=" + ((PetAidApplication)contexto.getApplication()).getEmailSignUser();
        new ConsomeServico(trazVoluntario, ConsomeServico.Metodo.GET, new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                Type foundType = new TypeToken<ArrayList<Voluntario>>(){}.getType();
                ArrayList<Voluntario> voluntarios = new Gson().fromJson(resultado,foundType);
                voluntario = voluntarios.get(0);

                if(voluntario != null) {
                    view.atualizaVoluntario(voluntario);
                }

            }
        }).executa();
    }

    public void atualizaVoluntario(String nome, String telefone){
        voluntario.setNome_voluntario(nome);
        voluntario.setTelefone_voluntario(telefone);
        String atualizaVoluntario = contexto.getString(R.string.web_service_url) + "voluntario/" + voluntario.getId_voluntario();
        Gson gson = new Gson();

        new ConsomeServico(atualizaVoluntario, ConsomeServico.Metodo.PUT, gson.toJson(voluntario), new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                view.atualizaCamposExibeToastSucesso(voluntario);
            }
        }).executa();
    }

    public void excluiVoluntario(){
        String deletaVoluntario = contexto.getString(R.string.web_service_url) + "voluntario/" + voluntario.getId_voluntario();
        new ConsomeServico(deletaVoluntario, ConsomeServico.Metodo.DELETE, new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(contexto, gso);
                googleSignInClient.revokeAccess();
                googleSignInClient.signOut();
                view.exibeToastExclusao();
            }
        }).executa();
    }

    public interface View{
        void atualizaVoluntario(Voluntario voluntario);
        void atualizaCamposExibeToastSucesso(Voluntario voluntario);
        void exibeToastExclusao();
    }
}
