package com.example.bonnie.petaid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.bonnie.petaid.R;

public class CadastroOngActivity extends AppCompatActivity {
    private EditText razaoSocialEditText;
    private EditText nomeFantasiaEditText;
    private EditText cnpjEditText;
    private EditText emailEditText;
    private EditText descricaoOngEditText;
    private EditText siteEditText;
    private EditText facebookEditText;
    private EditText instagrameditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_ong);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        razaoSocialEditText =findViewById(R.id.razaoSocialEditText);
        nomeFantasiaEditText=findViewById(R.id.nomeFantasiaEditText);
        cnpjEditText=findViewById(R.id.cnpjEditText);
        emailEditText=findViewById(R.id.emailEditText);
        descricaoOngEditText=findViewById(R.id.descricaoOngEditText);
        siteEditText=findViewById(R.id.siteEditText);
        facebookEditText=findViewById(R.id.facebookEditText);
        instagrameditText=findViewById(R.id.instagramEditText);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CadastroOngActivity.this, CadastroOngEnderecosActivity.class);
                startActivity(intent);
            }
        });
    }

}
