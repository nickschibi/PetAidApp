package com.example.bonnie.petaid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.model.Local;
import com.example.bonnie.petaid.presenter.CadastroOngEnderecosPresenter;

import java.util.ArrayList;

public class CadastroOngEnderecosActivity extends AppCompatActivity implements  CadastroOngEnderecosPresenter.View{

    private CadastroOngEnderecosPresenter presenter;
    private ArrayList<Local> listaLocais;
    private ListaLocalAdapter adapter;
    private Button btnFinal;
    private int idOrganizacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CadastroOngEnderecosPresenter(this, this);
        setContentView(R.layout.activity_cadastro_ong_enderecos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        btnFinal= findViewById(R.id.btnFinal);

        Bundle bundle = getIntent().getExtras();
        idOrganizacao = bundle.getInt("idOrganizacao");


        ImageButton fab = (ImageButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CadastroOngEnderecosActivity.this, CadastroLocalActivity.class);
                intent.putExtra("idOrganizacao", idOrganizacao);
                startActivity(intent);
            }
        });

        btnFinal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(CadastroOngEnderecosActivity.this, MapsActivity.class);
                intent.putExtra("idOrganizacao", idOrganizacao);
                startActivity(intent);
            }
        });

        listaLocais = new ArrayList<Local>();
        adapter = new ListaLocalAdapter(listaLocais, this, presenter);

        ListView lView = (ListView)findViewById(R.id.enderecosListView);
        lView.setAdapter(adapter);

        presenter.pegaListaLocais(idOrganizacao);

        verificaListaVazia();
    }


    @Override
    public void atualizaListaLocais(ArrayList<Local> listaLocais) {
        inflaListaLocais(listaLocais);
    }

    public void inflaListaLocais(ArrayList<Local> listaLocais){
        this.listaLocais.clear();
        this.listaLocais.addAll(listaLocais);
        adapter.notifyDataSetChanged();
        verificaListaVazia();
    }

    public void verificaListaVazia(){
        if(!listaLocais.isEmpty()){
            btnFinal.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void exibeToastSucesso() {
        Toast.makeText(CadastroOngEnderecosActivity.this, getString(R.string.cadastroSucesso), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void exibeToastErro() {
        Toast.makeText(CadastroOngEnderecosActivity.this, getString(R.string.cadastroErro), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed(){ //Botão BACK padrão do android
        Intent intent = new Intent(CadastroOngEnderecosActivity.this, CadastroOngActivity.class);
        intent.putExtra("idOrganizacao",idOrganizacao );
        startActivity(intent); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }
}