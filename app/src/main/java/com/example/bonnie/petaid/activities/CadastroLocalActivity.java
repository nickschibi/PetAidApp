package com.example.bonnie.petaid.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.Utils;
import com.example.bonnie.petaid.presenter.CadastroLocalPresenter;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_local);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
                if( Utils.isCampoVazio(ufEditText.getText().toString())){
                    validaCampos = false;
                    ufEditText.requestFocus();
                }
                if(validaCampos == false){

                    AlertDialog.Builder dlg = new AlertDialog.Builder(CadastroLocalActivity.this);
                    dlg.setTitle(R.string.warning);
                    dlg.setMessage(R.string.camposInvalidos);
                    dlg.setNeutralButton("Ok", null);
                    dlg.show();
                }
                else {
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

            }
      });
   }

    @Override
    public void exibeToastSucesso() {
        Toast.makeText(this, getString(R.string.cadastroSucesso), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void exibeToastErro() {
        Toast.makeText(this, getString(R.string.cadastroErro), Toast.LENGTH_SHORT).show();

    }
}
