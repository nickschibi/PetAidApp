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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bonnie.petaid.MaskWatcher;
import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.Utils;
import com.example.bonnie.petaid.model.Banco;
import com.example.bonnie.petaid.model.ContaBancaria;
import com.example.bonnie.petaid.model.Local;
import com.example.bonnie.petaid.presenter.CadastroLocalPresenter;

import java.util.ArrayList;
import java.util.Locale;

public class CadastroLocalActivity extends AppCompatActivity implements CadastroLocalPresenter.View, AdapterView.OnItemSelectedListener {
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
    private int tipoDoc = 0;
    private boolean dadosContaBancariaPreenchidos = false;
    private Spinner bancoSpinner;
    private Banco banco;
    private EditText proprietarioEditText;
    private int tipoConta = 0;
    private ContaBancaria contaBancaria;




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
        bancoSpinner = findViewById(R.id.bancoSpinner);
        proprietarioEditText = findViewById(R.id.proprietarioEditText);




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



         presenter.trazBancos();





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

                if(Utils.isCampoVazio(numDocumentoEditText.getText().toString())&& Utils.isCampoVazio(agenciaEditText.getText().toString()) && Utils.isCampoVazio(proprietarioEditText.getText().toString())&& Utils.isCampoVazio(contaEditText.getText().toString())){
                    //validaCampos = true;
                } else {
                    if(Utils.isCampoVazio(numDocumentoEditText.getText().toString())){
                        validaCampos = false;
                        numDocumentoEditText.requestFocus();
                    }
                    else if(Utils.isCampoVazio(agenciaEditText.getText().toString())){
                        validaCampos= false;
                        agenciaEditText.requestFocus();
                    }
                    else if(Utils.isCampoVazio(contaEditText.getText().toString())) {
                        validaCampos = false;
                        contaEditText.requestFocus();
                    }
                    else if(Utils.isCampoVazio(proprietarioEditText.getText().toString())){
                         validaCampos = false;
                         proprietarioEditText.requestFocus();

                    }
                    else if(tipoDoc == 1 && !Utils.isCNPJ(numDocumentoEditText.getText().toString())){
                        validaCampos = false;
                        numDocumentoEditText.requestFocus();
                    }
                    else if (tipoDoc == 2 && !Utils.isCPF(numDocumentoEditText.getText().toString())){
                        validaCampos = false;
                        numDocumentoEditText.requestFocus();
                    }
                    else{
                        dadosContaBancariaPreenchidos = true;
                    }
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

                        int idBanco = banco.getIdBanco();

                        boolean createContaBancaria = dadosContaBancariaPreenchidos;

                        presenter.cadastraEnderecoLocal(logradouroEditText.getText().toString(),
                                numCasaEditText.getText().toString(),
                                complementoEdittext.getText().toString(),
                                bairroEditText.getText().toString(),
                                cidadeEditText.getText().toString(),
                                ufEditText.getText().toString(),
                                cepEditText.getText().toString(),
                                responsavelEditText.getText().toString(),
                                idOrganizacao,
                                telefoneResponsavelEditText.getText().toString(),
                                createContaBancaria,
                                numDocumentoEditText.getText().toString(),
                                agenciaEditText.getText().toString(),
                                contaEditText.getText().toString(),
                                proprietarioEditText.getText().toString(),
                                tipoDoc,
                                idBanco,
                                tipoConta);
                    }

                    else{
                            int idBanco = banco.getIdBanco();

                            String acaoContaBancaria = "";

                            if(contaBancaria == null && dadosContaBancariaPreenchidos ){
                                acaoContaBancaria = "create";
                            } else if (contaBancaria != null && dadosContaBancariaPreenchidos){
                                acaoContaBancaria = "update";
                            } else if (contaBancaria != null && !dadosContaBancariaPreenchidos){
                                acaoContaBancaria = "delete";
                            }

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
                                telefoneResponsavelEditText.getText().toString(),
                                contaBancaria,
                                acaoContaBancaria,
                                numDocumentoEditText.getText().toString(),
                                agenciaEditText.getText().toString(),
                                contaEditText.getText().toString(),
                                proprietarioEditText.getText().toString(),
                                tipoDoc,
                                idBanco, tipoConta);


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

        presenter.trazContaBancaria(local.getIdLocal());
    }

    @Override
    public void exibeToastSucesso(int idOrganizacao) {

        Intent intent = new Intent(CadastroLocalActivity.this, CadastroOngEnderecosActivity.class);
        intent.putExtra("idOrganizacao", idOrganizacao);
        startActivity(intent);
        Toast.makeText(this, getString(R.string.cadastroSucesso), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void exibeToastErro(int idOrganizacao) {
        Toast.makeText(this, getString(R.string.cadastroErro), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void exibeToastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void preencheCamposConta(ContaBancaria contaBancaria) {
        this.contaBancaria = contaBancaria;
        agenciaEditText.setText(Integer.toString(contaBancaria.getCodAgencia()));
        contaEditText.setText(Integer.toString(contaBancaria.getCodConta()));
        proprietarioEditText.setText(contaBancaria.getNomeProprietario());
        numDocumentoEditText.setText(contaBancaria.getNumDoc());

        ArrayAdapter<Banco> adapter = (ArrayAdapter<Banco>)bancoSpinner.getAdapter();
        int pos = adapter.getPosition(new Banco(contaBancaria.getIdBanco()));
        bancoSpinner.setSelection(pos);

        if(contaBancaria.getIdCategoriaConta() == 1){
            RadioButton btn = findViewById(R.id.radioCorrente);
            btn.setChecked(true);
            tipoConta = 1;
        } else {
            RadioButton btn = findViewById(R.id.radioPoupanca);
            btn.setChecked(true);
            tipoConta = 2;
        }

        if(contaBancaria.getTipoDoc() == 1){
            RadioButton btn = findViewById(R.id.radioCnpj);
            btn.setChecked(true);
            tipoDoc = 1;
        } else {
            RadioButton btn = findViewById(R.id.radioCpf);
            btn.setChecked(true);
            tipoDoc = 2;
        }
    }

    @Override
    public void preencheBancos(ArrayList<Banco> bancos){
        ArrayAdapter<Banco> adapter = new ArrayAdapter<Banco>(this,
                android.R.layout.simple_spinner_item,bancos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bancoSpinner.setAdapter(adapter);
        bancoSpinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        banco = (Banco)parent.getItemAtPosition(position);
        }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioCnpj:
                if (checked)
                    tipoDoc = 1;
                    numDocumentoEditText.addTextChangedListener(new MaskWatcher("##.###.###/####-##"));
                break;
            case R.id.radioCpf:
                if (checked)
                    tipoDoc = 2;
                    numDocumentoEditText.addTextChangedListener(new MaskWatcher("###.###.###/##"));
                break;
        }
    }


    public void onRadioButtonClicked1(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioPoupanca:
                if (checked)
                    tipoConta = 2;
                break;
            case R.id.radioCorrente:
                if (checked)
                    tipoConta = 1;
                break;
        }
    }


}
