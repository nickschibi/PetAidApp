package com.example.bonnie.petaid.presenter;

import android.content.Context;

import com.example.bonnie.petaid.ConsomeServico;
import com.example.bonnie.petaid.PetAidApplication;
import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.model.ContaBancaria;
import com.example.bonnie.petaid.model.Endereco;
import com.example.bonnie.petaid.model.Local;
import com.example.bonnie.petaid.model.NecessidadeLocal;
import com.example.bonnie.petaid.model.Organizacao;
import com.example.bonnie.petaid.model.Voluntariado;
import com.example.bonnie.petaid.model.Voluntario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
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

     public void getLocais(List<Local> locais){
        String trazEnderecos = contexto.getString(R.string.web_service_url) + "local/";
        new ConsomeServico(trazEnderecos,  ConsomeServico.Metodo.GET, new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                locais.clear();
                Type foundListType = new TypeToken<ArrayList<Local>>(){}.getType();
                ArrayList<Local> l = new Gson().fromJson(resultado,foundListType);
                if(l != null) {
                    locais.addAll(l);
                }
                try {
                    view.poeMarcadoresEnderecos();
                }
                catch(IOException msg){
                }
            }
        }).executa();
    }

    public void getOrganizacaoByEndereco(Endereco endereco, Local local){
        String trazOng = contexto.getString(R.string.web_service_url) + "organizacao?id_endereco=" + endereco.getIdEndereco();

        new ConsomeServico(trazOng, ConsomeServico.Metodo.GET, new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                Type foundListType = new TypeToken<ArrayList<Organizacao>>(){}.getType();
                ArrayList<Organizacao> organizacoes = new Gson().fromJson(resultado,foundListType);
                Organizacao o = organizacoes.get(0);
                if(returnCode == 200 ) {
                    String trazNecessidades = contexto.getString(R.string.web_service_url) + "necessidadesLocal?id_endereco=" + endereco.getIdEndereco();
                    new ConsomeServico(trazNecessidades, ConsomeServico.Metodo.GET, new ConsomeServico.PosExecucao() {
                        @Override
                        public void executa(String resultado, int returnCode) {
                            Type foundListType = new TypeToken<ArrayList<NecessidadeLocal>>(){}.getType();
                            ArrayList<NecessidadeLocal> necessidadesLocal = new Gson().fromJson(resultado, foundListType);
                            view.setaNecessidades(necessidadesLocal);
                        }
                    }).executa();

                    view.setaOrganizacaoSlidingPanel(o.getNomeFantasia(), o.getDescricao(), o.getRazaoSocial());
                }
            }
        }).executa();
    }


    public void trazVoluntariado(Local local, Voluntario voluntario){
        String trazVoluntariado = contexto.getString(R.string.web_service_url) + "voluntariado?id_local=" + local.getIdLocal() + "&id_voluntario=" +  voluntario.getId_voluntario();
        new ConsomeServico(trazVoluntariado, ConsomeServico.Metodo.GET, new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                if(returnCode == 200){
                    Type foundListType = new TypeToken<ArrayList<Voluntariado>>(){}.getType();
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss zzz").create();
                    ArrayList<Voluntariado> voluntariados = gson.fromJson(resultado, foundListType);
                    if(voluntariados.size() > 0){
                        view.mudaEstadoBotoes(true);
                    } else {
                        view.mudaEstadoBotoes(false);
                    }
                }
            }
        }).executa();
    }



    public void criaVoluntariadoOld(int idLocal, String emailSignUser) {
        String trazVoluntario = contexto.getString(R.string.web_service_url) + "voluntario?email=" +  emailSignUser;
        new ConsomeServico(trazVoluntario, ConsomeServico.Metodo.GET, new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                Type foundType = new TypeToken<ArrayList<Voluntario>>(){}.getType();
                ArrayList<Voluntario> voluntarios = new Gson().fromJson(resultado,foundType);
                voluntario = voluntarios.get(0);

                if(voluntario != null) {
                    String criaVoluntariado = contexto.getString(R.string.web_service_url) + "/voluntariado";
                    Voluntariado voluntariado = new Voluntariado();
                    voluntariado.setDtVoluntariado(new Date());
                    voluntariado.setIdLocal(idLocal);
                    voluntariado.setIdVoluntario(voluntario.getId_voluntario());
                    voluntariado.setAtivo(1);
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss zzz").create();
                    String requestBody = gson.toJson(voluntariado);
                    new ConsomeServico(criaVoluntariado, ConsomeServico.Metodo.POST, requestBody, new ConsomeServico.PosExecucao() {
                        @Override
                        public void executa(String resultado, int returnCode) {
                            if(returnCode == 200){
                                Type foundType = new TypeToken<Voluntariado>(){}.getType();
                                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss zzz").create();
                                Voluntariado voluntariado = gson.fromJson(resultado,foundType);
                                view.mudaEstadoBotoes(true);
                            } else {
                                view.exibeToastMsg("Erro ao volutariar-se");
                            }
                        }
                    }).executa();
                }

            }
        }).executa();

    }


    public void criaVoluntariado(int idLocal, Voluntario voluntario) {
        String criaVoluntariado = contexto.getString(R.string.web_service_url) + "/voluntariado";
        Voluntariado voluntariado = new Voluntariado();
        voluntariado.setDtVoluntariado(new Date());
        voluntariado.setIdLocal(idLocal);
        voluntariado.setIdVoluntario(voluntario.getId_voluntario());
        voluntariado.setAtivo(1);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss zzz").create();
        String requestBody = gson.toJson(voluntariado);
        new ConsomeServico(criaVoluntariado, ConsomeServico.Metodo.POST, requestBody, new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                if(returnCode == 200){
                    Type foundType = new TypeToken<Voluntariado>(){}.getType();
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss zzz").create();
                    Voluntariado voluntariado = gson.fromJson(resultado,foundType);
                    view.mudaEstadoBotoes(true);
                } else {
                    view.exibeToastMsg("Erro ao volutariar-se");
                }
            }
        }).executa();
    }



    public void pegaVoluntario(String emailSignUser) {
        String trazVoluntario = contexto.getString(R.string.web_service_url) + "voluntario?email=" +  emailSignUser;
        new ConsomeServico(trazVoluntario, ConsomeServico.Metodo.GET, new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                Type foundType = new TypeToken<ArrayList<Voluntario>>(){}.getType();
                ArrayList<Voluntario> voluntarios = new Gson().fromJson(resultado,foundType);
                voluntario = voluntarios.get(0);

                if(voluntario != null) {
                    view.setaVoluntario(voluntario);
                }

            }
        }).executa();

    }



    public interface View{
        void poeMarcadoresEnderecos() throws  IOException;
        void setaOrganizacaoSlidingPanel(String nomeFantasia, String descricao, String razaoSocial);
        void setaNecessidades(ArrayList<NecessidadeLocal> necessidadesLocal);
        void exibeToastMsg(String s);
        void mudaEstadoBotoes(Boolean flag);
        void setaVoluntario(Voluntario voluntario);
    }
}
