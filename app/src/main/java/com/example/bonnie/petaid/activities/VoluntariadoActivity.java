package com.example.bonnie.petaid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#69efad'>Voluntariado</font>"));    //Titulo para ser exibido na sua Action Bar em frente à seta

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

    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                Intent intent = new Intent(VoluntariadoActivity.this, PerfilVolActivity.class);
                intent.putExtra("idVoluntario", idVoluntario);
                startActivity(intent);  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    @Override
    public void onBackPressed(){ //Botão BACK padrão do android
        Intent intent = new Intent(VoluntariadoActivity.this, PerfilVolActivity.class);
        intent.putExtra("idVoluntario", idVoluntario);
        startActivity(intent); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }
}
