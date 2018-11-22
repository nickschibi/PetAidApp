package com.example.bonnie.petaid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#69efad'>Voluntários</font>"));    //Titulo para ser exibido na sua Action Bar em frente à seta

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
        verificaListaVazia();
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

    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                Intent intent = new Intent(VoluntariosActivity.this, CadastroLocalActivity.class);
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
        Intent intent = new Intent(VoluntariosActivity.this, CadastroLocalActivity.class);
        intent.putExtra("idLocal", idLocal);
        startActivity(intent); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

}
