package com.example.bonnie.petaid.presenter;

import android.app.Activity;

import com.example.bonnie.petaid.ConsomeServico;
import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.model.Voluntariado;
import com.example.bonnie.petaid.model.Voluntario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VoluntariosPresenter {
    private View view;
    private Activity contexto;
    private Voluntario voluntario;


    public VoluntariosPresenter(View view, Activity contexto){
        this.contexto = contexto;
        this.view = view;
    }


    public void trazVoluntarios(int idLocal){

        String urlVoluntariado = contexto.getString(R.string.web_service_url) + "voluntariado?id_local="+ idLocal ;
        new ConsomeServico(urlVoluntariado, ConsomeServico.Metodo.GET, new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                if(returnCode == 200){
                    Type foundType = new TypeToken<List<Voluntariado>>(){}.getType();
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss zzz").create();
                     ArrayList<Voluntariado> listaVoluntariado = gson.fromJson(resultado,foundType);
                    ArrayList<Voluntario> listaVoluntarios = new ArrayList<>();
                    for (Voluntariado v :listaVoluntariado) {
                        listaVoluntarios.add(v.getVoluntario());
                    }
                    view.atualizaListaVoluntarios(listaVoluntarios);
                } else {

                }
            }
        }).executa();

    }


    public interface View{

        void atualizaListaVoluntarios(ArrayList<Voluntario> vols);


    }
}
