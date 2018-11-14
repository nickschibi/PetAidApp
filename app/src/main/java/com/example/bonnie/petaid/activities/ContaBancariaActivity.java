package com.example.bonnie.petaid.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputFilter;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bonnie.petaid.MaskWatcher;
import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.Utils;
import com.example.bonnie.petaid.model.Banco;
import com.example.bonnie.petaid.model.ContaBancaria;
import com.example.bonnie.petaid.presenter.ContaBancariaPresenter;

import java.util.ArrayList;

public class ContaBancariaActivity extends AppCompatActivity implements ContaBancariaPresenter.View, AdapterView.OnItemSelectedListener {

    private EditText numDocumentoEditText;
    private EditText bancoEditText;
    private EditText agenciaEditText;
    private EditText contaEditText;
    private ContaBancariaPresenter presenter;
    private boolean update = false;
    private int tipoDoc = 0;
    private boolean dadosContaBancariaPreenchidos = false;
    private Spinner bancoSpinner;
    private Banco banco;
    private EditText proprietarioEditText;
    private int tipoConta = 0;
    private int idLocal;
    private ContaBancaria contaBancaria;
    private Button btnDelete;
    public AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta_bancaria);

        presenter = new ContaBancariaPresenter(this,this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#69efad'>Conta Bancária </font>"));    //Titulo para ser exibido na sua Action Bar em frente à seta

        btnDelete = findViewById(R.id.deletbtn);
        numDocumentoEditText = findViewById(R.id.numDocumentoEditText);
        agenciaEditText = findViewById(R.id.agenciaEditText);
        contaEditText = findViewById(R.id.contaEditText);
        bancoSpinner = findViewById(R.id.bancoSpinner);
        proprietarioEditText = findViewById(R.id.proprietarioEditText);

        presenter.trazBancos();


        Bundle bundle = getIntent().getExtras();
        idLocal = bundle.getInt("idLocal");

        if(idLocal!=0) {
            presenter.trazContaBancaria(idLocal);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validaCampos = true; // okay

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

                if(validaCampos == false){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ContaBancariaActivity.this, R.style.MyDialog);
                    builder.setTitle(R.string.warning);
                    builder.setMessage(Html.fromHtml("<font color='#FFFFFF'>" + getText(R.string.camposInvalidos)+ "</font>"));
                    builder.setNeutralButton("Ok", null);
                    alerta = builder.create();
                    alerta.show();

                }
                else {
                    int idBanco = banco.getIdBanco();
                    if(contaBancaria == null && dadosContaBancariaPreenchidos ){
                        presenter.criaContaBancaria(idLocal,
                                numDocumentoEditText.getText().toString(),
                                agenciaEditText.getText().toString(),
                                contaEditText.getText().toString(),
                                proprietarioEditText.getText().toString(),
                                tipoDoc,
                                idBanco, tipoConta);
                    } else if (contaBancaria != null && dadosContaBancariaPreenchidos){
                        presenter.atualizaContaBancaria(contaBancaria.getIdConta(),
                                idLocal,
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

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogExcluir();
            }
        });

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
            numDocumentoEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(18)});
        } else {
            RadioButton btn = findViewById(R.id.radioCpf);
            btn.setChecked(true);
            tipoDoc = 2;
            numDocumentoEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(14)});
        }
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
                numDocumentoEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(18)});
                break;
            case R.id.radioCpf:
                if (checked)
                    tipoDoc = 2;
                numDocumentoEditText.addTextChangedListener(new MaskWatcher("###.###.###-##"));
                numDocumentoEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(14)});
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

    @Override
    public void exibeToastSucesso(ContaBancaria contaBancaria) {
        Intent intent = new Intent(ContaBancariaActivity.this, CadastroLocalActivity.class);
        intent.putExtra("idLocal", idLocal);
        startActivity(intent);
        Toast.makeText(this, getString(R.string.cadastroSucesso), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void exibeToastSucessoExclusao(String msg)  {

        Intent intent = new Intent(ContaBancariaActivity.this, CadastroLocalActivity.class);
        intent.putExtra("idLocal", idLocal);
        startActivity(intent);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }



    @Override
    public void exibeToastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }






    private void dialogExcluir(){
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialog);
        builder.setMessage(Html.fromHtml("<font color='#FFFFFF'>" + getText(R.string.confirmaExcluirContaBancaria)+ "</font>"))
                .setPositiveButton(R.string.excluir, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        presenter.excluiContaBancaria(contaBancaria.getIdConta());
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

    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                Intent intent = new Intent(ContaBancariaActivity.this, CadastroLocalActivity.class);
                intent.putExtra("idLocal", idLocal);
                startActivity(intent);  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    @Override
    public void onBackPressed(){ //Botão BACK padrão do android
        Intent intent = new Intent(ContaBancariaActivity.this, CadastroLocalActivity.class);
        intent.putExtra("idLocal", idLocal);
        startActivity(intent); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

}
