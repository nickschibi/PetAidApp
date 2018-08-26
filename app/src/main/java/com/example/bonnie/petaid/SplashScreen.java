package com.example.bonnie.petaid;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();


    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this); // se possui conta no google cadastrada
        if (account != null) {
            ((PetAidApplication) this.getApplication()).setEmailSignUser(account.getEmail());
            String tipoUsuario = verificaUsuario(account.getEmail());
            if (!tipoUsuario.equals("nada")) {                                              // verifica se o usuario é cadastrado pelo email
                ((PetAidApplication) this.getApplication()).setTypeUser(tipoUsuario);
                Intent i = new Intent(SplashScreen.this, MapsActivity.class); // se sim, vai para a tela principal
                startActivity(i);
            } else {
                Intent i = new Intent(SplashScreen.this, CadastroVol.class); // se não, vai para a tela de cadastro
                i.putExtra("email", account.getEmail());
                startActivity(i);
            }
        } else {
            Intent i = new Intent(SplashScreen.this, QuestionUser.class); // Se não tem nem a conta do google cadastrada vai para tela de pergunta
            startActivity(i);
        }

    }


    public String verificaUsuario(String email) {

        try {
            String url = getString(R.string.web_service_url) + "user/" + email;
            String s = new SplashScreen.ConsomeServico().execute(url).get();

            return s;

        } catch (Exception e) {
            e.printStackTrace();
            return "nada";
        }

    }



    private class ConsomeServico extends AsyncTask<String, Void, String> {

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

            //this.posExecucao.executa(resultado);


        }
    }
}