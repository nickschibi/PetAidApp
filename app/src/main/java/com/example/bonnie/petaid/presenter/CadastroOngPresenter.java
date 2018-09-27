package com.example.bonnie.petaid.presenter;

import android.app.Activity;
import android.content.Context;

import com.example.bonnie.petaid.ConsomeServico;
import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.model.Local;
import com.example.bonnie.petaid.model.Organizacao;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CadastroOngPresenter {

    private Organizacao organizacao;
    private View view;
    private Activity contexto;
    private ArrayList<Local> locais;

    public CadastroOngPresenter(View view, Activity contexto){
        this.contexto = contexto;
        this.view = view;
    }

    public void cadastraOrganizacao(String razao_social, String nome_fantasia, String nm_cnpj, String email, String desricao,String site, String facebook, String instagram){

        organizacao = new Organizacao( razao_social,  nome_fantasia, nm_cnpj, email,  desricao, site,  facebook,  instagram);

        String requestBody = new Gson().toJson(organizacao);
        String urlVoluntario = contexto.getString(R.string.web_service_url) + "organizacao";
        new ConsomeServico(urlVoluntario, ConsomeServico.Metodo.POST, requestBody, new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                if(returnCode == 200){
                    Type foundType = new TypeToken<Organizacao>(){}.getType();
                    organizacao = new Gson().fromJson(resultado,foundType);
                    view.exibeToastSucesso(organizacao.getIdOrganizacao());

                } else {
                    view.exibeToastErro();
                }
            }
        }).executa();

    }


    public void atualizaOrganizacao(int idOrganizacao, String razao_social, String nome_fantasia, String nm_cnpj, String email, String desricao,String site, String facebook, String instagram){

        organizacao = new Organizacao( idOrganizacao, razao_social,  nome_fantasia, nm_cnpj, email,  desricao, site,  facebook,  instagram);

        String requestBody = new Gson().toJson(organizacao);
        String urlVoluntario = contexto.getString(R.string.web_service_url) + "organizacao/" + idOrganizacao;
        new ConsomeServico(urlVoluntario, ConsomeServico.Metodo.PUT, requestBody, new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                if(returnCode == 200){
                    Type foundType = new TypeToken<Organizacao>(){}.getType();
                    organizacao = new Gson().fromJson(resultado,foundType);
                    view.exibeToastSucesso(organizacao.getIdOrganizacao());

                } else {
                    view.exibeToastErro();
                }
            }
        }).executa();

    }

    public void trazOrganizacao(String email){

        String requestBody = new Gson().toJson(organizacao);
        String urlVoluntario = contexto.getString(R.string.web_service_url) + "organizacaoByEmail/" + email;
        new ConsomeServico(urlVoluntario, ConsomeServico.Metodo.GET, requestBody, new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                if(returnCode == 200){
                    Type foundType = new TypeToken<Organizacao>(){}.getType();
                    organizacao = new Gson().fromJson(resultado,foundType);
                    view.preencheCampos(organizacao);
                } else if(returnCode == 404) {
                    // Nada, vai cadastrar novo
                } else {
                    view.exibeToastErro();
                }
            }
        }).executa();
    }

    public void excluiOrganizacao(){

        String requestBody = new Gson().toJson(organizacao);
        String urlLocal = contexto.getString(R.string.web_service_url) + "local?id_organizacao=" + organizacao.getIdOrganizacao();

        new ConsomeServico(urlLocal, ConsomeServico.Metodo.GET, requestBody, new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                if(returnCode == 200){
                    Type foundType = new TypeToken<List<Local>>(){}.getType();
                    locais = new Gson().fromJson(resultado,foundType);
                    for (Local l: locais) {


                        String urlEndereco = contexto.getString(R.string.web_service_url) + "endereco/" + l.getEndereco().getIdEndereco();
                        new ConsomeServico(urlEndereco, ConsomeServico.Metodo.DELETE, new ConsomeServico.PosExecucao() {
                            @Override
                            public void executa(String resultado, int returnCode) {
                                if(returnCode == 200){
                                    String urlLocal = contexto.getString(R.string.web_service_url) + "local/" + l.getIdLocal();
                                    new ConsomeServico(urlLocal, ConsomeServico.Metodo.DELETE, new ConsomeServico.PosExecucao() {
                                        @Override
                                        public void executa(String resultado, int returnCode) {
                                            if(returnCode == 200){
                                                //TODO
                                            } else {
                                                //TODO
                                            }
                                        }
                                    }).executa();
                                    //TODO
                                } else {
                                    //TODO
                                }
                            }
                        }).executa();


                    }
                    String deletaOrganizacao = contexto.getString(R.string.web_service_url) + "organizacao/" + organizacao.getIdOrganizacao();
                    new ConsomeServico(deletaOrganizacao, ConsomeServico.Metodo.DELETE, new ConsomeServico.PosExecucao() {
                        @Override
                        public void executa(String resultado, int returnCode) {
                            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
                            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(contexto, gso);
                            googleSignInClient.revokeAccess();
                            googleSignInClient.signOut();
                            view.exibeToastExclusao();
                        }
                    }).executa();

                } else {
                    view.exibeToastErro();
                }
            }
        }).executa();


    }

    public interface View{
        void preencheCampos(Organizacao organizacao);
        void exibeToastSucesso(int idOrganizacao);
        void exibeToastErro();
        void exibeToastExclusao();
    }
}
