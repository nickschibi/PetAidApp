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
    protected void onPause() {
        super.onPause();
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();

        Thread timeThread = new Thread(){
            public void run(){
                try {
                    sleep(3000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(SplashScreen.this); // se possui conta no google cadastrada
                    if (account != null) {
                        ((PetAidApplication) SplashScreen.this.getApplication()).setEmailSignUser(account.getEmail());
                        String tipoUsuario = verificaUsuario(account.getEmail());
                        if (tipoUsuario.equals("vol") || tipoUsuario.equals("ong")) {                                              // verifica se o usuario é cadastrado pelo email
                            ((PetAidApplication) SplashScreen.this.getApplication()).setTypeUser(tipoUsuario);
                            Intent i = new Intent(SplashScreen.this, MapsActivity.class); // se sim, vai para a tela principal
                            startActivity(i);
                        } else if(tipoUsuario.equals("nada")) {
                            Intent i = new Intent(SplashScreen.this, CadastroVol.class); // se não, vai para a tela de cadastro
                            i.putExtra("email", account.getEmail());
                            startActivity(i);
                        } else { //erro

                                    runOnUiThread(()->Toast.makeText(SplashScreen.this,"Erro ao conectar-se com servidor", Toast.LENGTH_LONG).show());


                        }
                    } else {
                        Intent i = new Intent(SplashScreen.this, QuestionUser.class); // Se não tem nem a conta do google cadastrada vai para tela de pergunta
                        startActivity(i);
                    }

                }

            }
        };
        timeThread.start();


    }


    public String verificaUsuario(String email) {

        try {
            String url = getString(R.string.web_service_url) + "user/" + email;
            String s = new SplashScreen.ConsomeServico().execute(url).get();

            return s;
        }
        catch (Exception e) {
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
                return "erro";
            }
        }

        @Override
        protected void onPostExecute(String resultado) {

            //this.posExecucao.executa(resultado);


        }
    }
}