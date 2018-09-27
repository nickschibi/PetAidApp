package com.example.bonnie.petaid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.Utils;
import com.example.bonnie.petaid.model.Local;
import com.example.bonnie.petaid.presenter.CadastroLocalPresenter;

import java.util.Locale;

public class CadastroLocalActivity extends AppCompatActivity implements CadastroLocalPresenter.View {
    private EditText logradouroEditText;
    private EditText numCasaEditText;
    private EditText complementoEdittext;
    private EditText bairroEditText;
    private EditText cidadeEditText;
    private EditText ufEditText;
    private EditText cepEditText;
    private EditText responsavelEditText;
    private EditText telefoneResponsavelEditText;
    private EditText numDocumentoEditText;
    private EditText bancoEditText;
    private EditText agenciaEditText;
    private EditText contaEditText;
    private CheckBox larCheckBox ;
    private CheckBox resgateCheckBox;
    private CheckBox vetCheckBox;
    private CheckBox advogadoCheckbox;
    private CheckBox remedioCheckBox;
    private CheckBox racaoCaoCheckBox;
    private CheckBox racaoGatoCheckBox;
    private CheckBox areiaCheckBox;
    private EditText observacaoEdittext;
    private CadastroLocalPresenter presenter;
    private Local local;
    private boolean update = false;




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
        complementoEdittext = findViewById(R.id.complementoEditText);
        bairroEditText = findViewById(R.id.bairroEditText);
        cidadeEditText = findViewById(R.id.cidadeEditText);
        ufEditText = findViewById(R.id.ufEditText);
        cepEditText = findViewById(R.id.cepEditText);
        responsavelEditText = findViewById(R.id.responsavelEditText);
        telefoneResponsavelEditText = findViewById(R.id.telefoneResponsavelEditText);
        numDocumentoEditText = findViewById(R.id.numDocumentoEditText);
        bancoEditText = findViewById(R.id.bancoEditText);
        agenciaEditText = findViewById(R.id.agenciaEditText);
        contaEditText = findViewById(R.id.contaEditText);
        larCheckBox  = findViewById(R.id.larCheckbox);
        resgateCheckBox = findViewById(R.id.resgateCheckbox);
        vetCheckBox = findViewById(R.id.vetCheckbox);
        advogadoCheckbox = findViewById(R.id.advogadoCheckbox);
        remedioCheckBox = findViewById(R.id.remedioCheckbox);
        racaoCaoCheckBox = findViewById(R.id.racaoCaoCheckbox);
        racaoGatoCheckBox = findViewById(R.id.racaoGatoCheckbox);
        areiaCheckBox = findViewById(R.id.areiaCheckbox);
        observacaoEdittext = findViewById(R.id.observacaoEditText);


        Bundle bundle = getIntent().getExtras();
        int idOrganizacao = bundle.getInt("idOrganizacao");

        Bundle bundle1 = getIntent().getExtras();
        int idLocal = bundle.getInt("idLocal");


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
            }});




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                complementoEdittext.getText().toString(),
                                bairroEditText.getText().toString(),
                                cidadeEditText.getText().toString(),
                                ufEditText.getText().toString(),
                                cepEditText.getText().toString(),
                                responsavelEditText.getText().toString(),
                                idOrganizacao,
                                telefoneResponsavelEditText.getText().toString());
                    }

                    else{
                        presenter.atualizaEnderecoLocal(local.getIdEndereco(),
                                local.getIdLocal(),
                                logradouroEditText.getText().toString(),
                                numCasaEditText.getText().toString(),
                                complementoEdittext.getText().toString(),
                                bairroEditText.getText().toString(),
                                cidadeEditText.getText().toString(),
                                ufEditText.getText().toString(),
                                cepEditText.getText().toString(),
                                responsavelEditText.getText().toString(),
                                local.getIdOrganizacao(),
                                telefoneResponsavelEditText.getText().toString());
                    }


                }

            }
      });
   }

    @Override
    public void preencheCampos(Local local) {
        this.local = local;
        logradouroEditText.setText(local.getEndereco().getEnd());
        numCasaEditText.setText(local.getEndereco().getNumcasa());
        complementoEdittext.setText(local.getEndereco().getComplemento());
        bairroEditText.setText(local.getEndereco().getBairro());
        cidadeEditText.setText(local.getEndereco().getCidade());
        ufEditText.setText(local.getEndereco().getUf());
        cepEditText.setText(local.getEndereco().getCep());
        responsavelEditText.setText(local.getNomeResponsavel());
        telefoneResponsavelEditText.setText(local.getTelefoneLocal());
    }

    @Override
    public void exibeToastSucesso(int idOrganizacao) {
        Toast.makeText(this, getString(R.string.cadastroSucesso), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(CadastroLocalActivity.this, CadastroOngEnderecosActivity.class);
        intent.putExtra("idOrganizacao", idOrganizacao);
        startActivity(intent);

    }

    @Override
    public void exibeToastErro(int idOrganizacao) {
        Toast.makeText(this, getString(R.string.cadastroErro), Toast.LENGTH_SHORT).show();
    }
}
