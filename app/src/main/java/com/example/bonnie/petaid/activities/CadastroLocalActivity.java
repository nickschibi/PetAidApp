package com.example.bonnie.petaid.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.bonnie.petaid.R;

public class CadastroLocalActivity extends AppCompatActivity {
    private EditText logradouroEditText;
    private EditText numCasaEditText;
    private EditText complementoEdittext;
    private EditText bairroEdittext;
    private EditText cidadeEditText;
    private EditText ufEdittext;
    private EditText cepEditText;
    private EditText responsavelEditText;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_local);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        logradouroEditText = findViewById(R.id.logradouroEditText);
        numCasaEditText = findViewById(R.id.numeroCasaEditText);
        complementoEdittext = findViewById(R.id.complementoEditText);
        bairroEdittext = findViewById(R.id.bairroEditText);
        cidadeEditText = findViewById(R.id.cidadeEditText);
        ufEdittext = findViewById(R.id.ufEditText);
        cepEditText = findViewById(R.id.cepEditText);
        responsavelEditText = findViewById(R.id.responsavelEditText);
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



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
      });
   }

}
