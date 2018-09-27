package com.example.bonnie.petaid.presenter;

import android.app.Activity;

import com.example.bonnie.petaid.ConsomeServico;
import com.example.bonnie.petaid.PetAidApplication;
import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.model.Endereco;
import com.example.bonnie.petaid.model.Local;
import com.example.bonnie.petaid.model.Voluntario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CadastroLocalPresenter {
    private Endereco endereco;
    private Local local;
    private View view;
    private Activity contexto;

    public CadastroLocalPresenter(View view, Activity contexto){
        this.contexto = contexto;
        this.view = view;
    }

    public void cadastraEnderecoLocal(String logradouro ,String numCasa, String complemento, String bairro, String cidade,String uf, String cep,
                                      String nomeResponsavel ,int idOrganizacao, String telefoneLocal){
        endereco = new Endereco(logradouro, numCasa ,complemento,bairro,cidade,uf,cep);

        String requestBody = new Gson().toJson(endereco);
        String urlEndereco = contexto.getString(R.string.web_service_url) + "endereco";
        new ConsomeServico(urlEndereco, ConsomeServico.Metodo.POST, requestBody, new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                if(returnCode == 200){
                    Type foundType = new TypeToken<Endereco>(){}.getType();
                    endereco = new Gson().fromJson(resultado,foundType);

                    local = new Local( nomeResponsavel , idOrganizacao,  endereco.getIdEndereco(),  telefoneLocal);

                    String requestBody = new Gson().toJson(local);
                    String urlLocal = contexto.getString(R.string.web_service_url) + "local";
                    new ConsomeServico(urlLocal, ConsomeServico.Metodo.POST, requestBody, new ConsomeServico.PosExecucao() {
                        @Override
                        public void executa(String resultado, int returnCode) {
                            if(returnCode == 200){
                                Type foundType = new TypeToken<Local>(){}.getType();
                                local = new Gson().fromJson(resultado,foundType);
                                view.exibeToastSucesso(idOrganizacao);

                            } else {
                                view.exibeToastErro(idOrganizacao);
                            }
                        }
                    }).executa();

                } else {
                    view.exibeToastErro(idOrganizacao);
                }
            }
        }).executa();



    }
    public void trazLocal(int idLocal){
        String trazVoluntario = contexto.getString(R.string.web_service_url) + "local/" +idLocal;
        new ConsomeServico(trazVoluntario, ConsomeServico.Metodo.GET, new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                Type foundType = new TypeToken<Local>(){}.getType();
                local = new Gson().fromJson(resultado,foundType);
                view.preencheCampos(local);
            }
        }).executa();
    }

    public void  atualizaEnderecoLocal(int idEndereco, int idLocal, String logradouro ,String numCasa, String complemento, String bairro, String cidade,String uf, String cep,
                                       String nomeResponsavel ,int idOrganizacao, String telefoneLocal){
        endereco = new Endereco(idEndereco, logradouro, numCasa ,complemento,bairro,cidade,uf,cep);
        String requestBody = new Gson().toJson(endereco);
        String urlEndereco = contexto.getString(R.string.web_service_url) + "endereco/"+ endereco.getIdEndereco();
        Gson gson = new Gson();

        new ConsomeServico(urlEndereco, ConsomeServico.Metodo.PUT, gson.toJson(endereco), new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                if(returnCode == 200){
                    local = new Local( idLocal, nomeResponsavel , idOrganizacao,  endereco.getIdEndereco(),  telefoneLocal);
                    String requestBody = new Gson().toJson(local);
                    String urlLocal = contexto.getString(R.string.web_service_url) + "local/"+ local.getIdLocal();
                    Gson gson = new Gson();
                    new ConsomeServico(urlLocal, ConsomeServico.Metodo.PUT, gson.toJson(local), new ConsomeServico.PosExecucao() {
                        @Override
                        public void executa(String resultado, int returnCode) {
                            if(returnCode == 200){
                                view.exibeToastSucesso(idOrganizacao);

                            } else {
                                view.exibeToastErro(idOrganizacao);
                            }
                        }
                    }).executa();

                } else {
                    view.exibeToastErro(idOrganizacao);
                }
            }
        }).executa();

        }



    public interface View{
        void preencheCampos(Local local);
        void exibeToastSucesso(int idOrganizacao);
        void exibeToastErro(int idOrganizacao);
    }
}
