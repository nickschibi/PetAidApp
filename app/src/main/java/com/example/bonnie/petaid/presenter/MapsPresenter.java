package com.example.bonnie.petaid.presenter;

import android.content.Context;

import com.example.bonnie.petaid.ConsomeServico;
import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.model.Endereco;
import com.example.bonnie.petaid.model.Organizacao;
import com.example.bonnie.petaid.model.Voluntario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MapsPresenter {

    private Voluntario voluntario;
    private View view;
    private Context contexto;

    public MapsPresenter(View view, Context contexto){
        this.contexto = contexto;
        this.view = view;
    }

    public void getEnderecos(List<Endereco> enderecos){
        String trazEnderecos = contexto.getString(R.string.web_service_url) + "endereco/";
        new ConsomeServico(trazEnderecos,  ConsomeServico.Metodo.GET, new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                enderecos.clear();
                Type foundListType = new TypeToken<ArrayList<Endereco>>(){}.getType();
                ArrayList<Endereco> e = new Gson().fromJson(resultado,foundListType);
                if(e != null) {
                    enderecos.addAll(e);
                }
                try {
                    view.poeMarcadoresEnderecos();
                }
                catch(IOException msg){
                }
            }
        }).executa();
    }

    public void getOrganizacaoByEndereco(Endereco endereco){
        String trazOng = contexto.getString(R.string.web_service_url) + "organizacao?id_endereco=" + endereco.getIdEndereco();

        new ConsomeServico(trazOng, ConsomeServico.Metodo.GET, new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                Type foundListType = new TypeToken<ArrayList<Organizacao>>(){}.getType();
                ArrayList<Organizacao> organizacoes = new Gson().fromJson(resultado,foundListType);
                Organizacao o = organizacoes.get(0);
                view.setaOrganizacaoSlidingPanel(o.getNomeFantasia());
            }
        }).executa();
    }

    public interface View{
        void poeMarcadoresEnderecos() throws  IOException;
        void setaOrganizacaoSlidingPanel(String nomeFantasia);
    }
}
