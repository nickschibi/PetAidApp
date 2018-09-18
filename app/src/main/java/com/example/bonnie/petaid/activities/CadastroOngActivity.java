package com.example.bonnie.petaid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.Utils;
import com.example.bonnie.petaid.presenter.CadastroOngPresenter;

public class CadastroOngActivity extends AppCompatActivity implements CadastroOngPresenter.View {
    private EditText razaoSocialEditText;
    private EditText nomeFantasiaEditText;
    private EditText cnpjEditText;
    private EditText emailEditText;
    private EditText descricaoOngEditText;
    private EditText siteEditText;
    private EditText facebookEditText;
    private EditText instagramEditText;
    private CadastroOngPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_ong);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presenter = new CadastroOngPresenter(this,this);


        AlertDialog.Builder dlg = new AlertDialog.Builder(CadastroOngActivity.this, R.style.MyDialog);
        dlg.setMessage(Html.fromHtml("<font color='#FFFFFF'>" + getText(R.string.aviso)+ "</font>"));
        dlg.setNeutralButton("Ok, Entendi", null);
        dlg.show();

        razaoSocialEditText =findViewById(R.id.razaoSocialEditText);
        nomeFantasiaEditText=findViewById(R.id.nomeFantasiaEditText);
        cnpjEditText=findViewById(R.id.cnpjEditText);
        emailEditText=findViewById(R.id.emailEditText);
        descricaoOngEditText=findViewById(R.id.descricaoOngEditText);
        siteEditText=findViewById(R.id.siteEditText);
        facebookEditText=findViewById(R.id.facebookEditText);
        instagramEditText=findViewById(R.id.instagramEditText);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean validaCampos = true; // okay

                if( Utils.isCampoVazio(razaoSocialEditText.getText().toString())){
                    validaCampos = false;
                    razaoSocialEditText.requestFocus();
                }
                if( !Utils.isName(razaoSocialEditText.getText().toString())){
                    validaCampos = false;
                    razaoSocialEditText.requestFocus();
                }
                if( Utils.isCampoVazio(nomeFantasiaEditText.getText().toString())){
                    validaCampos = false;
                    nomeFantasiaEditText.requestFocus();
                }
                if(!Utils.isCNPJ(cnpjEditText.getText().toString())){
                    validaCampos = false;
                    cnpjEditText.requestFocus();
                }
                if(!Utils.isEmailValido(emailEditText.getText().toString())) {
                    validaCampos = false;
                    emailEditText.requestFocus();
                }
                if(validaCampos == false){

                    AlertDialog.Builder dlg = new AlertDialog.Builder(CadastroOngActivity.this);
                    dlg.setTitle(R.string.warning);
                    dlg.setMessage(R.string.camposInvalidos);
                    dlg.setNeutralButton("Ok", null);
                    dlg.show();
                }
                else {
                    presenter.cadastraOrganizacao(razaoSocialEditText.getText().toString(),
                            nomeFantasiaEditText.getText().toString(),
                            cnpjEditText.getText().toString(), emailEditText.getText().toString(),
                            descricaoOngEditText.getText().toString(),siteEditText.getText().toString(),
                            facebookEditText.getText().toString(),
                            instagramEditText.getText().toString());

                }
            }


        });
    }

    @Override
    public void exibeToastSucesso(int idOrganizacao) {
        Toast.makeText(CadastroOngActivity.this, getString(R.string.cadastroSucesso), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(CadastroOngActivity.this, CadastroOngEnderecosActivity.class);
        intent.putExtra("idOrganizacao", idOrganizacao);
        startActivity(intent);
    }

    @Override
    public void exibeToastErro() {
        Toast.makeText(CadastroOngActivity.this, getString(R.string.cadastroErro), Toast.LENGTH_SHORT).show();
    }
}
