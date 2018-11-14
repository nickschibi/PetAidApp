package com.example.bonnie.petaid.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bonnie.petaid.MaskWatcher;
import com.example.bonnie.petaid.PetAidApplication;
import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.Utils;
import com.example.bonnie.petaid.model.Organizacao;
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
    private Organizacao organizacao;
    private boolean update = false;
    private Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_ong);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presenter = new CadastroOngPresenter(this,this);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão

        String email = ((PetAidApplication) CadastroOngActivity.this.getApplication()).getEmailSignUser();

        boolean isCadastro = getIntent().getBooleanExtra("cadastro", false);

        razaoSocialEditText =findViewById(R.id.razaoSocialEditText);
        nomeFantasiaEditText=findViewById(R.id.nomeFantasiaEditText);
        cnpjEditText=findViewById(R.id.cnpjEditText);
        emailEditText=findViewById(R.id.emailEditText);
        descricaoOngEditText=findViewById(R.id.descricaoOngEditText);
        siteEditText=findViewById(R.id.siteEditText);
        facebookEditText=findViewById(R.id.facebookEditText);
        instagramEditText=findViewById(R.id.instagramEditText);
        btnDelete = findViewById(R.id.deletbtn);
        cnpjEditText.addTextChangedListener(new MaskWatcher("##.###.###/####-##"));

        emailEditText.setText(((PetAidApplication) CadastroOngActivity.this.getApplication()).getEmailSignUser());

        if(!isCadastro && email != null && !email.equals("")){
            presenter.trazOrganizacao(email);
        } else {
            AlertDialog.Builder dlg = new AlertDialog.Builder(CadastroOngActivity.this, R.style.MyDialog);
            dlg.setMessage(Html.fromHtml("<font color='#FFFFFF'>" + getText(R.string.aviso)+ "</font>"));
            dlg.setNeutralButton("Ok, Entendi", null);
            dlg.show();
            btnDelete.setVisibility(View.INVISIBLE);

        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogExcluir();
            }
        });

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

                    AlertDialog.Builder builder = new AlertDialog.Builder(CadastroOngActivity.this, R.style.MyDialog);
                    builder.setTitle(R.string.warning);
                    builder.setMessage(Html.fromHtml("<font color='#FFFFFF'>" + getText(R.string.camposInvalidos)+ "</font>"));
                    builder.setNeutralButton("Ok", null);
                    alerta = builder.create();
                    alerta.show();

                }
                else {

                    if(update == false) {
                        presenter.cadastraOrganizacao(razaoSocialEditText.getText().toString(),
                                nomeFantasiaEditText.getText().toString(),
                                cnpjEditText.getText().toString(),
                                emailEditText.getText().toString(),
                                descricaoOngEditText.getText().toString(),
                                siteEditText.getText().toString(),
                                facebookEditText.getText().toString(),
                                instagramEditText.getText().toString());
                    } else {
                        presenter.atualizaOrganizacao(organizacao.getIdOrganizacao(),
                                razaoSocialEditText.getText().toString(),
                                nomeFantasiaEditText.getText().toString(),
                                cnpjEditText.getText().toString(),
                                emailEditText.getText().toString(),
                                descricaoOngEditText.getText().toString(),
                                siteEditText.getText().toString(),
                                facebookEditText.getText().toString(),
                                instagramEditText.getText().toString());
                    }
                }
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
                        presenter.excluiOrganizacao();
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

    @Override
    public void preencheCampos(Organizacao organizacao) {
        this.organizacao = organizacao;
        razaoSocialEditText.setText(organizacao.getRazaoSocial());
        nomeFantasiaEditText.setText(organizacao.getNomeFantasia());
        cnpjEditText.setText(organizacao.getNmCnpj());
        emailEditText.setText(organizacao.getEmail());
        descricaoOngEditText.setText(organizacao.getDescricao());
        siteEditText.setText(organizacao.getSite());
        facebookEditText.setText(organizacao.getFacebook());
        instagramEditText.setText(organizacao.getInstagram());
        update = true;
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

    @Override
    public void exibeToastExclusao() {
        Toast.makeText(CadastroOngActivity.this, "Cadastro excluido", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(CadastroOngActivity.this, SplashScreenActivity.class);
        startActivity(i);
    }

    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                Intent intent = new Intent(CadastroOngActivity.this, MapsActivity.class);

                startActivity(intent);  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }
}
