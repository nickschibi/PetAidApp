package com.example.bonnie.petaid.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.bonnie.petaid.PetAidApplication;
import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.Utils;
import com.example.bonnie.petaid.presenter.CadastroVolPresenter;

import java.util.Locale;



public class CadastroVolActivity extends AppCompatActivity implements CadastroVolPresenter.View {
    private FloatingActionButton btnSave;
    private EditText nome;
    private EditText email;
    private EditText telefone;
    private CadastroVolPresenter presenter;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CadastroVolPresenter(this,this);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#69efad'>Cadastro </font>"));



        setContentView(R.layout.activity_cadastro_vol);
        nome =  findViewById(R.id.nomeEditText);
        email = findViewById(R.id.emailEditText);
        telefone = findViewById(R.id.telefoneEditText);
        btnSave = findViewById(R.id.saveButton);

        email.setText(((PetAidApplication) CadastroVolActivity.this.getApplication()).getEmailSignUser());

        telefone.addTextChangedListener(new PhoneNumberFormattingTextWatcher() {
            int length_before = 0;
            private boolean mFormatting;


            @Override
            public void afterTextChanged(Editable s) {
                // Make sure to ignore calls to afterTextChanged caused by the work done below
                if (!mFormatting) {
                    mFormatting = true;
                    String l = Locale.getDefault().getCountry();

                    String num = s.toString();
                    String data = PhoneNumberUtils.formatNumber(num, l);
                    if(data!=null)
                    {
                        s.clear();
                        s.append(data);
                    }
                    mFormatting = false;
                }
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                boolean validaCampos = true; // okay

                if( Utils.isCampoVazio(nome.getText().toString())){
                    validaCampos = false;
                    nome.requestFocus();
                }
                if( !Utils.isName(nome.getText().toString())){
                    validaCampos = false;
                    nome.requestFocus();
                }
                if(!Utils.isPhone(telefone.getText().toString())){
                    validaCampos = false;
                    telefone.requestFocus();
                }
                if(!Utils.isTelefone(telefone.getText().toString())){
                    validaCampos = false;
                    telefone.requestFocus();
                }
                if(!Utils.isEmailValido(email.getText().toString())) {
                    validaCampos = false;
                    email.requestFocus();
                }
                if(validaCampos == false){

                    AlertDialog.Builder builder = new AlertDialog.Builder(CadastroVolActivity.this, R.style.MyDialog);
                    builder.setTitle(R.string.warning);
                    builder.setMessage(Html.fromHtml("<font color='#FFFFFF'>" + getText(R.string.camposInvalidos)+ "</font>"));
                    builder.setNeutralButton("Ok", null);
                    alerta = builder.create();
                    alerta.show();
                }
                else {
                    presenter.cadastraVoluntario(nome.getText().toString(),
                            email.getText().toString(),
                            telefone.getText().toString());
                }
            }
        });
    }

    @Override
    public void exibeToastSucesso() {
        Toast.makeText(CadastroVolActivity.this, getString(R.string.cadastroSucesso), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(CadastroVolActivity.this, MapsActivity.class);
        startActivity(i);
    }

    @Override
    public void exibeToastErro(){
        Toast.makeText(CadastroVolActivity.this, getString(R.string.cadastroErro), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed(){ //Botão BACK padrão do android
        startActivity(new Intent(this, SplashScreenActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }
}
