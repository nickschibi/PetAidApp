package com.example.bonnie.petaid.presenter;

import android.app.Activity;

import com.example.bonnie.petaid.ConsomeServico;
import com.example.bonnie.petaid.PetAidApplication;
import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.model.Banco;
import com.example.bonnie.petaid.model.ContaBancaria;
import com.example.bonnie.petaid.model.Endereco;
import com.example.bonnie.petaid.model.Local;
import com.example.bonnie.petaid.model.Voluntario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CadastroLocalPresenter {
    private Endereco endereco;
    private Local local;
    private View view;
    private Activity contexto;

    public CadastroLocalPresenter(View view, Activity contexto){
        this.contexto = contexto;
        this.view = view;
    }



    public void cadastraEnderecoLocal(String logradouro ,String numCasa, String complemento, String bairro, String cidade,
                                      String uf, String cep, String nomeResponsavel ,int idOrganizacao, String telefoneLocal, String redirect){

        endereco = new Endereco(logradouro, numCasa ,complemento,bairro,cidade,uf,cep);

        String requestBody = new Gson().toJson(endereco);
        String urlEndereco = contexto.getString(R.string.web_service_url) + "endereco";

        // A primeira chamada de serviço é assincrona, as posteriores são sincronas
        new ConsomeServico(urlEndereco, ConsomeServico.Metodo.POST, requestBody, new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                if(returnCode == 200){
                    // Caso a chamada assincrona seja feita com sucesso

                    Type foundType = new TypeToken<Endereco>(){}.getType();
                    endereco = new Gson().fromJson(resultado,foundType);

                    try {
                        sequenciaChamadas:
                        {
                            // Primeira chamada sincrona: cadastro de local
                            local = new Local( nomeResponsavel , idOrganizacao,  endereco.getIdEndereco(),  telefoneLocal);
                            String requestBody = new Gson().toJson(local);
                            String urlLocal = contexto.getString(R.string.web_service_url) + "local";
                            ConsomeServico servico = new ConsomeServico(urlLocal, ConsomeServico.Metodo.POST, requestBody);
                            String retorno = servico.executaSincrono();
                            int rc = servico.getReturnCode();
                            if(rc != 200){

                                view.exibeToastMsg("erro ao cadastrar local");
                                break sequenciaChamadas;
                            }

                            foundType = new TypeToken<Local>(){}.getType();
                            local = new Gson().fromJson(retorno,foundType);

                            // Caso tudo tenha corrido certo, exibe sucesso
                            view.exibeToastSucesso(idOrganizacao, local, redirect);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        // ERRO
                    }




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
                                       String nomeResponsavel ,int idOrganizacao, String telefoneLocal, String redirect){


        endereco = new Endereco(idEndereco, logradouro, numCasa ,complemento,bairro,cidade,uf,cep );
        String requestBody = new Gson().toJson(endereco);
        String urlEndereco = contexto.getString(R.string.web_service_url) + "endereco/"+ endereco.getIdEndereco();
        Gson gson = new Gson();
        new ConsomeServico(urlEndereco, ConsomeServico.Metodo.PUT, gson.toJson(endereco), new ConsomeServico.PosExecucao() {
            @Override
            public void executa(String resultado, int returnCode) {
                if(returnCode == 200){

                    Type foundType = new TypeToken<Endereco>(){}.getType();
                    endereco = new Gson().fromJson(resultado,foundType);

                    try {
                        sequenciaChamadas:
                        {
                            local = new Local( idLocal, nomeResponsavel , idOrganizacao,  endereco.getIdEndereco(),  telefoneLocal);
                            String requestBody = new Gson().toJson(local);
                            String urlLocal = contexto.getString(R.string.web_service_url) + "local/"+ local.getIdLocal();
                            Gson gson = new Gson();
                            ConsomeServico servico = new ConsomeServico(urlLocal, ConsomeServico.Metodo.PUT,requestBody);
                            String retorno = servico.executaSincrono();
                            int rc = servico.getReturnCode();
                            if(rc != 200){
                                view.exibeToastMsg("erro ao cadastrar local");
                                break sequenciaChamadas;
                            }
                            foundType = new TypeToken<Local>(){}.getType();
                            local = new Gson().fromJson(retorno,foundType);


                            // Caso tudo tenha corrido certo, exibe sucesso
                            view.exibeToastSucesso(idOrganizacao, local, redirect);
                        }

                    }catch (Exception e) {
                        e.printStackTrace();
                        // ERRO
                    }

                } else {
                    view.exibeToastErro(idOrganizacao);
                }
            }
        }).executa();

        }



    public interface View{

        void preencheCampos(Local local);
        void exibeToastSucesso(int idOrganizacao,Local local, String redirect);
        void exibeToastErro(int idOrganizacao);
        void exibeToastMsg(String msg);

    }
}
