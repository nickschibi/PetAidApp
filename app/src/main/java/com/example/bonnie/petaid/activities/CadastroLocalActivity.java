package com.example.bonnie.petaid.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.Utils;
import com.example.bonnie.petaid.model.Local;
import com.example.bonnie.petaid.presenter.CadastroLocalPresenter;

import java.util.Locale;

public class CadastroLocalActivity extends AppCompatActivity implements CadastroLocalPresenter.View {
    private EditText logradouroEditText;
    private EditText numCasaEditText;
    private EditText complementoEditText;
    private EditText bairroEditText;
    private EditText cidadeEditText;
    private EditText ufEditText;
    private EditText cepEditText;
    private EditText responsavelEditText;
    private EditText telefoneResponsavelEditText;
    private CadastroLocalPresenter presenter;
    private Local local;
    private boolean update = false;
    private Button btnContaBancaria;
    private Button btnNecessidades;
    int idOrganizacao;
    int idLocal;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_local);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        presenter = new CadastroLocalPresenter(this,this);
        logradouroEditText = findViewById(R.id.logradouroEditText);
        numCasaEditText = findViewById(R.id.numeroCasaEditText);
        complementoEditText = findViewById(R.id.complementoEditText);
        bairroEditText = findViewById(R.id.bairroEditText);
        cidadeEditText = findViewById(R.id.cidadeEditText);
        ufEditText = findViewById(R.id.ufEditText);
        cepEditText = findViewById(R.id.cepEditText);
        responsavelEditText = findViewById(R.id.responsavelEditText);
        telefoneResponsavelEditText = findViewById(R.id.telefoneResponsavelEditText);
        backBtn = findViewById(R.id.backbtn);

        Bundle bundle = getIntent().getExtras();
        idOrganizacao = bundle.getInt("idOrganizacao");
        idLocal = bundle.getInt("idLocal");


        if(idLocal!=0){
            update = true;
            presenter.trazLocal(idLocal);
        }

        telefoneResponsavelEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher() {
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
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificaCamposECadastraLocal(idOrganizacao, null);
            }
        });

        btnContaBancaria = findViewById(R.id.btnContaBancaria);
        btnContaBancaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificaCamposECadastraLocal(idOrganizacao, "conta_bancaria");
            }
        });

        btnNecessidades = findViewById(R.id.btnNecessidade);
        btnNecessidades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificaCamposECadastraLocal(idOrganizacao, "necessidades");
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                backBtn.setTextColor(Color.parseColor("#FF0000"));
                Intent i = new Intent(CadastroLocalActivity.this, CadastroOngEnderecosActivity.class);
                startActivity(i);
            }
        });

   }


   public void verificaCamposECadastraLocal(int idOrganizacao, String redirect){
       boolean validaCampos = true; // okay

       if( Utils.isCampoVazio(logradouroEditText.getText().toString())){
           validaCampos = false;
           logradouroEditText.requestFocus();
       }
       if( Utils.isCampoVazio(numCasaEditText.getText().toString())){
           validaCampos = false;
           numCasaEditText.requestFocus();
       }
       if( Utils.isCampoVazio(bairroEditText.getText().toString())){
           validaCampos = false;
           bairroEditText.requestFocus();
       }
       if( Utils.isCampoVazio(cidadeEditText.getText().toString())){
           validaCampos = false;
           cidadeEditText.requestFocus();
       }
       if( Utils.isCampoVazio(ufEditText.getText().toString().toUpperCase())||!Utils.isEstado(ufEditText.getText().toString().toUpperCase()) ){
           validaCampos = false;
           ufEditText.requestFocus();
       }
       if( Utils.isCampoVazio(responsavelEditText.getText().toString())){
           validaCampos = false;
           responsavelEditText.requestFocus();
       }

       if(!Utils.isPhone(telefoneResponsavelEditText.getText().toString())){
           validaCampos = false;
           telefoneResponsavelEditText.requestFocus();
       }
       if(!Utils.isTelefone(telefoneResponsavelEditText.getText().toString())){
           validaCampos = false;
           telefoneResponsavelEditText.requestFocus();
       }

       if(validaCampos == false){

           AlertDialog.Builder dlg = new AlertDialog.Builder(CadastroLocalActivity.this);
           dlg.setTitle(R.string.warning);
           dlg.setMessage(R.string.camposInvalidos);
           dlg.setNeutralButton("Ok", null);
           dlg.show();
       }
       else {
           if(update == false) {
               presenter.cadastraEnderecoLocal(logradouroEditText.getText().toString(),
                       numCasaEditText.getText().toString(),
                       complementoEditText.getText().toString(),
                       bairroEditText.getText().toString(),
                       cidadeEditText.getText().toString(),
                       ufEditText.getText().toString(),
                       cepEditText.getText().toString(),
                       responsavelEditText.getText().toString(),
                       idOrganizacao,
                       telefoneResponsavelEditText.getText().toString(),
                       redirect);
           }
           else{
               presenter.atualizaEnderecoLocal(local.getIdEndereco(),
                       local.getIdLocal(),
                       logradouroEditText.getText().toString(),
                       numCasaEditText.getText().toString(),
                       complementoEditText.getText().toString(),
                       bairroEditText.getText().toString(),
                       cidadeEditText.getText().toString(),
                       ufEditText.getText().toString(),
                       cepEditText.getText().toString(),
                       responsavelEditText.getText().toString(),
                       local.getIdOrganizacao(),
                       telefoneResponsavelEditText.getText().toString(),
                       redirect
               );
           }
       }
   }

    @Override
    public void preencheCampos(Local local) {
        this.local = local;
        logradouroEditText.setText(local.getEndereco().getEnd());
        numCasaEditText.setText(local.getEndereco().getNumcasa());
        complementoEditText.setText(local.getEndereco().getComplemento());
        bairroEditText.setText(local.getEndereco().getBairro());
        cidadeEditText.setText(local.getEndereco().getCidade());
        ufEditText.setText(local.getEndereco().getUf());
        cepEditText.setText(local.getEndereco().getCep());
        responsavelEditText.setText(local.getNomeResponsavel());
        telefoneResponsavelEditText.setText(local.getTelefoneLocal());
    }

    @Override
    public void exibeToastSucesso(int idOrganizacao, Local local, String redirect) {

        this.local = local;
        update = true;
        if("conta_bancaria".equals(redirect)){

            Intent i = new Intent(CadastroLocalActivity.this, ContaBancariaActivity.class);
            i.putExtra("idLocal", local.getIdLocal());
            startActivity(i);
        }
        else if("necessidades".equals(redirect)){
            Intent i = new Intent(CadastroLocalActivity.this, NecessidadesActivity.class);
            i.putExtra("idLocal", local.getIdLocal());
            startActivity(i);
        }
        else{
            Toast.makeText(this, getString(R.string.cadastroSucesso), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void exibeToastErro(int idOrganizacao) {
        Toast.makeText(this, getString(R.string.cadastroErro), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void exibeToastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}
