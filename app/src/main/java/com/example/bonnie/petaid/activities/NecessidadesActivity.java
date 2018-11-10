package com.example.bonnie.petaid.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.model.Necessidade;
import com.example.bonnie.petaid.model.NecessidadeLocal;
import com.example.bonnie.petaid.presenter.NecessidadesPresenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class NecessidadesActivity extends AppCompatActivity  implements NecessidadesPresenter.View{

    private LinearLayout checkboxList;
    private NecessidadesPresenter presenter;
    private HashMap<Integer,Necessidade> hashNecessidades;
    private HashMap<Integer,Necessidade> hashNecessidadesRef;
    private HashMap<Integer,Necessidade> hashNecessidadesOriginais;
    private int idLocal;
    private HashMap<Integer,CheckBox> hashCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_necessidades);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presenter = new NecessidadesPresenter(this,this);

        // Hash para armazenar as necessidades selecionadas
        hashNecessidades = new HashMap<>();
        // Hash para armazenar as necessidades com suas informaçoes
        hashNecessidadesRef = new HashMap<>();
        // Hash pára armazenar referencias para os checkboxes
        hashCheckbox = new HashMap<>();
        // Hash pára armazenar as necessidades originais do local
        hashNecessidadesOriginais = new HashMap<>();

        Bundle bundle = getIntent().getExtras();
        idLocal = bundle.getInt("idLocal");

        checkboxList = findViewById(R.id.checkboxList);
        presenter.trazNecessidades();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvaNecessidadesLocal();
            }
        });
    }

    private void salvaNecessidadesLocal(){
        ArrayList<NecessidadeLocal> listAdd = new ArrayList<>();
        ArrayList<NecessidadeLocal> listRemove = new ArrayList<>();
        for (int idNecessidade : hashNecessidadesRef.keySet()){
            if(hashNecessidadesOriginais.containsKey(idNecessidade) && !hashNecessidades.containsKey(idNecessidade)){
                // Tinha antes e nao tem mais, logo, apagar
                listRemove.add(new NecessidadeLocal(idLocal, idNecessidade));
            } else if(!hashNecessidadesOriginais.containsKey(idNecessidade) && hashNecessidades.containsKey(idNecessidade)) {
                // Não tinha antes e tem agora, logo, adicionar
                listAdd.add(new NecessidadeLocal(idLocal, idNecessidade));
            }
        }
        if(listAdd.size() > 0){
            presenter.salvaNecessidadesLocal(listAdd);
        }
        if(listRemove.size() > 0){
            presenter.removeNecessidadesLocal(listRemove);
        }
        hashNecessidadesOriginais = (HashMap<Integer, Necessidade>)hashNecessidades.clone();
    }

    @Override
    public void preencheCheckNecessidades(ArrayList<Necessidade> necessidades){
        for (Necessidade necessidade : necessidades){
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(necessidade.getDescricaoNecessidade());

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkBox.isChecked()){
                        hashNecessidades.put(necessidade.getIdNecessidade(), necessidade);
                    } else {
                        hashNecessidades.remove(necessidade.getIdNecessidade());
                    }
                }
            });

            checkboxList.addView(checkBox);
            hashCheckbox.put(necessidade.getIdNecessidade(), checkBox);
            hashNecessidadesRef.put(necessidade.getIdNecessidade(), necessidade);

        }
        presenter.trazNecessidadesLocal(idLocal);
    }


    @Override
    public void preencheCheckNecessidadesLocal(ArrayList<NecessidadeLocal> necessidadesLocal){
        for (NecessidadeLocal necessidadeLocal : necessidadesLocal){
            hashCheckbox.get(necessidadeLocal.getIdNecessidade()).setChecked(true);
            hashNecessidades.put(necessidadeLocal.getIdNecessidade(), hashNecessidadesRef.get(necessidadeLocal.getIdNecessidade()));
        }
        hashNecessidadesOriginais = (HashMap<Integer, Necessidade>)hashNecessidades.clone();
    }

    @Override
    public void exibeToastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
