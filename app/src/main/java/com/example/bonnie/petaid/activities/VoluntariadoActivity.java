package com.example.bonnie.petaid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.model.Voluntariado;
import com.example.bonnie.petaid.presenter.VoluntariadoPresenter;

import java.util.ArrayList;

public class VoluntariadoActivity extends AppCompatActivity implements VoluntariadoPresenter.View{

    private ArrayList<Voluntariado> listaVoluntariados;
    private VoluntariadoPresenter presenter;
    private int idVoluntario;
    private ListaVoluntariadoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voluntariado);

        presenter = new VoluntariadoPresenter(this, this);
        Bundle bundle = getIntent().getExtras();
        idVoluntario = bundle.getInt("idVoluntario");

        listaVoluntariados = new ArrayList<Voluntariado>();
        adapter = new ListaVoluntariadoAdapter(listaVoluntariados, this, presenter);

        ListView lView = (ListView)findViewById(R.id.voluntariadoListView);
        lView.setAdapter(adapter);

        presenter.trazVoluntariados(idVoluntario);
    }

    @Override
    public void atualizaListaVoluntariados(ArrayList<Voluntariado> listaVoluntariados) {
        inflaListaVoluntariados(listaVoluntariados);
    }
    public void inflaListaVoluntariados(ArrayList<Voluntariado> vols){
        this.listaVoluntariados.clear();
        this.listaVoluntariados.addAll(vols);
        adapter.notifyDataSetChanged();

    }
}
