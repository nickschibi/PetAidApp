package com.example.bonnie.petaid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.bonnie.petaid.PetAidApplication;
import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.presenter.SplashScreenPresenter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;


public class LoginVolActivity extends AppCompatActivity implements View.OnClickListener, SplashScreenPresenter.View{
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "LoginVol";
    private SplashScreenPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_vol);
        getSupportActionBar().hide();

        this.presenter = new SplashScreenPresenter(this,this);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            // ...
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            ((PetAidApplication) LoginVolActivity.this.getApplication()).setEmailSignUser(account.getEmail());
            String tipoUsuario = presenter.verificaUsuario(account.getEmail());
            if (tipoUsuario.equals("vol") || tipoUsuario.equals("ong")) { // verifica se o usuario é cadastrado pelo email
                ((PetAidApplication) LoginVolActivity.this.getApplication()).setTypeUser(tipoUsuario);
                Intent i = new Intent(LoginVolActivity.this, MapsActivity.class); // se sim, vai para a tela principal
                startActivity(i);
            } else if(tipoUsuario.equals("nada")) {
                Intent i = new Intent(LoginVolActivity.this, QuestionUserActivity.class); // se não, vai para a tela de cadastro
                startActivity(i);
            } else { //erro
                runOnUiThread(()->Toast.makeText(LoginVolActivity.this,"Erro ao conectar-se com servidor", Toast.LENGTH_LONG).show());
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, "Erro ao se conectar com o sua conta Google", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected  void onStart(){
        super.onStart();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            ((PetAidApplication) LoginVolActivity.this.getApplication()).setEmailSignUser(account.getEmail());
            Intent i = new Intent(LoginVolActivity.this, QuestionUserActivity.class);
            startActivity(i);
        }


    }

    @Override
    public void onBackPressed(){ //Botão BACK padrão do android
        startActivity(new Intent(this, SplashScreenActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }


}

