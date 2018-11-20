package com.example.bonnie.petaid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.model.Voluntario;
import com.example.bonnie.petaid.presenter.VoluntariosPresenter;

import java.util.ArrayList;

public class VoluntariosActivity extends AppCompatActivity implements VoluntariosPresenter.View{
    private ArrayList<Voluntario> listaVoluntarios;
    private ListaVoluntariosAdapter adapter;
    private VoluntariosPresenter presenter;
    private int idLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voluntarios);
        presenter = new VoluntariosPresenter(this, this);
        Bundle bundle = getIntent().getExtras();
        idLocal = bundle.getInt("idLocal");

        listaVoluntarios = new ArrayList<Voluntario>();
        adapter = new ListaVoluntariosAdapter(listaVoluntarios, this, presenter);

        ListView lView = (ListView)findViewById(R.id.voluntariosListView);
        lView.setAdapter(adapter);

        presenter.trazVoluntarios(idLocal);


    }

    @Override
    public void atualizaListaVoluntarios(ArrayList<Voluntario> vols) {
        inflaListaVoluntarios(vols);

    }
    public void inflaListaVoluntarios(ArrayList<Voluntario> vols){
        this.listaVoluntarios.clear();
        this.listaVoluntarios.addAll(vols);
        adapter.notifyDataSetChanged();

    }
    public void verificaListaVazia(){
        if(!listaVoluntarios.isEmpty()){
            Toast.makeText(this, "Ops! Nenhum voluntariado até o momento", Toast.LENGTH_SHORT).show();
        }
    }
}
