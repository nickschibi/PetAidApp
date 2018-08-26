package com.example.bonnie.petaid;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class PerfilVol extends AppCompatActivity {

    private Voluntario voluntario;
    private EditText nome;
    private EditText email;
    private EditText telefone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_vol);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nome = findViewById(R.id.nomeEditText);
        email = findViewById(R.id.emailEditText);
        telefone = findViewById(R.id.telefoneEditText);


        String trazVoluntario = getString(R.string.web_service_url) + "voluntario?email=" + ((PetAidApplication)this.getApplication()).getEmailSignUser();
        new PerfilVol.ConsomeServico(new PerfilVol.PosExecucao() {
            @Override
            public void executa(String resultado) {
                Type foundType = new TypeToken<ArrayList<Voluntario>>(){}.getType();
                ArrayList<Voluntario> voluntarios = new Gson().fromJson(resultado,foundType);
                voluntario = voluntarios.get(0);

                if(voluntario != null) {
                    nome.setText(voluntario.getNome_voluntario());
                    email.setText(voluntario.getEmail());
                    telefone.setText(voluntario.getTelefone_voluntario());
                }

            }
        }).execute(trazVoluntario);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });
    }


    private interface PosExecucao{
        void executa(String resultado);
    }

    private class ConsomeServico extends AsyncTask<String, Void, String> {

        private PerfilVol.PosExecucao posExecucao;

        public ConsomeServico(PerfilVol.PosExecucao posExecucao){
            this.posExecucao = posExecucao;
        }

        @Override
        protected String doInBackground(String... url) {
            OkHttpClient client = new OkHttpClient();
            Request request =
                    new Request.Builder()
                            .url(url[0])
                            .build();
            okhttp3.Response response = null;
            try {
                response = client.newCall(request).execute();
                String resultado = response.body().string();
                return resultado;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String resultado) {
            this.posExecucao.executa(resultado);
        }

    }

}
