package com.example.bonnie.petaid.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.Utils;
import com.example.bonnie.petaid.model.Voluntario;
import com.example.bonnie.petaid.presenter.PerfilVolPresenter;
import java.util.Locale;

public class PerfilVolActivity extends AppCompatActivity implements PerfilVolPresenter.View{

    private EditText nome;
    private EditText email;
    private EditText telefone;
    private Button backBtn;
    private Button deleteBtn;
    private EditText nomediag;
    private EditText telefonediag;
    private PerfilVolPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PerfilVolPresenter(this, this);
        setContentView(R.layout.activity_perfil_vol);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nome = findViewById(R.id.nomeEditText);
        email = findViewById(R.id.emailEditText);
        telefone = findViewById(R.id.telefoneEditText);
        backBtn = findViewById(R.id.backbtn);
        deleteBtn = findViewById(R.id.deletbtn);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogExcluir();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PerfilVolActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });

        presenter.getVoluntario();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateVoluntario();
            }
        });
    }

    public AlertDialog alerta;

    private void dialogExcluir(){
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialog);
        builder.setMessage(Html.fromHtml("<font color='#FFFFFF'>" + getText(R.string.confirmaExcluir)+ "</font>"))
                .setPositiveButton(R.string.excluir, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        presenter.excluiVoluntario();
                    }
                })
                .setNegativeButton(R.string.cancel,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        alerta.cancel();
                    }
                });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

    void  updateVoluntario(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialog);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialogupdate, null);
        builder.setView(dialogView);
        builder.setTitle("Atulização de Dados");
        builder.setMessage("");
        nomediag = dialogView.findViewById(R.id.nomeEditTextDiag);
        nomediag.setText(nome.getText().toString());
        telefonediag = dialogView.findViewById(R.id.telefoneEditTextDiag);
        telefonediag.setText(telefone.getText().toString());

        telefonediag.addTextChangedListener(new PhoneNumberFormattingTextWatcher() {
            int length_before = 0;
            private boolean mFormatting;


            @Override
            public void afterTextChanged(Editable s) {
                // Make sure to ignore calls to afterTextChanged caused by the work done below
                if (!mFormatting) {
                    mFormatting = true;
                    String l = Locale.getDefault().getCountry();

                    String num =s.toString();
                    String data = PhoneNumberUtils.formatNumber(num, l);
                    if(data!=null)
                    {
                        s.clear();
                        s.append(data);
                    }
                    mFormatting = false;
                }
            }});

        builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                boolean validaCampos = true; // okay

                if( Utils.isCampoVazio(nomediag.getText().toString())){
                    validaCampos = false;
                    nome.requestFocus();
                }
                if( !Utils.isName(nomediag.getText().toString())){
                    validaCampos = false;
                    nome.requestFocus();
                }
                if(!Utils.isPhone(telefonediag.getText().toString())){
                    validaCampos = false;
                    telefone.requestFocus();
                }
                if(!Utils.isTelefone(telefonediag.getText().toString())){
                    validaCampos = false;
                    telefone.requestFocus();
                }
                if(!Utils.isEmailValido(email.getText().toString())) {
                    validaCampos = false;
                    email.requestFocus();
                }
                if(validaCampos == false){

                    Toast.makeText(PerfilVolActivity.this, R.string.camposInvalidos, Toast.LENGTH_SHORT).show();
                }
                 else {
                    presenter.atualizaVoluntario(nomediag.getText().toString(), telefonediag.getText().toString());
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });


        builder.show();
    }

    @Override
    public void atualizaVoluntario(Voluntario voluntario) {
        nome.setText(voluntario.getNome_voluntario());
        email.setText(voluntario.getEmail());
        telefone.setText(voluntario.getTelefone_voluntario());
    }

    @Override
    public void atualizaCamposExibeToastSucesso(Voluntario voluntario) {
        nome.setText(voluntario.getNome_voluntario());
        telefone.setText(voluntario.getTelefone_voluntario());
        Toast.makeText(PerfilVolActivity.this, "Sucesso nas Alterações", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void exibeToastExclusao() {
        Toast.makeText(PerfilVolActivity.this, "Cadastro excluido", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(PerfilVolActivity.this, SplashScreenActivity.class);
        startActivity(i);
    }
}


