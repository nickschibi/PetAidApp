package com.example.bonnie.petaid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bonnie.petaid.PetAidApplication;
import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.presenter.SplashScreenPresenter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class SplashScreenActivity extends AppCompatActivity implements SplashScreenPresenter.View{

    private SplashScreenPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SplashScreenPresenter(this, this);
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
                    sleep(2000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(SplashScreenActivity.this); // se possui conta no google cadastrada
                    if (account != null) {
                        ((PetAidApplication) SplashScreenActivity.this.getApplication()).setEmailSignUser(account.getEmail());
                        String tipoUsuario = presenter.verificaUsuario(account.getEmail());
                        if (tipoUsuario.equals("vol") || tipoUsuario.equals("ong")) {                                              // verifica se o usuario é cadastrado pelo email
                            ((PetAidApplication) SplashScreenActivity.this.getApplication()).setTypeUser(tipoUsuario);
                            Intent i = new Intent(SplashScreenActivity.this, MapsActivity.class); // se sim, vai para a tela principal
                            startActivity(i);
                        } else if(tipoUsuario.equals("nada")) {
                            Intent i = new Intent(SplashScreenActivity.this, CadastroOngActivity.class); // se não, vai para a tela de cadastro
                            i.putExtra("email", account.getEmail());
                            startActivity(i);
                        } else { //erro
                            runOnUiThread(()->Toast.makeText(SplashScreenActivity.this,"Erro ao conectar-se com servidor", Toast.LENGTH_LONG).show());
                        }
                    } else {
                        Intent i = new Intent(SplashScreenActivity.this, QuestionUserActivity.class); // Se não tem nem a conta do google cadastrada vai para tela de pergunta
                        startActivity(i);
                    }

                }

            }
        };
        timeThread.start();
    }
}