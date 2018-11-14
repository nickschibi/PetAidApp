package com.example.bonnie.petaid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
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
import java.util.HashMap;


public class NecessidadesActivity extends AppCompatActivity  implements NecessidadesPresenter.View {

    private LinearLayout checkboxList;
    private NecessidadesPresenter presenter;
    private HashMap<Integer, Necessidade> hashNecessidades;
    private HashMap<Integer, Necessidade> hashNecessidadesRef;
    private HashMap<Integer, Necessidade> hashNecessidadesOriginais;
    private int idLocal;
    private HashMap<Integer, CheckBox> hashCheckbox;
    private EditText observacaoEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_necessidades);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#69efad'>Necessidades </font>"));   //Titulo para ser exibido na sua Action Bar em frente à seta
        presenter = new NecessidadesPresenter(this, this);

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

        observacaoEditText = findViewById(R.id.observacaoEditText);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvaNecessidadesLocal();

            }
        });
    }

    private void salvaNecessidadesLocal() {
        ArrayList<NecessidadeLocal> listAdd = new ArrayList<>();
        ArrayList<NecessidadeLocal> listRemove = new ArrayList<>();
        for (int idNecessidade : hashNecessidadesRef.keySet()) {
            if (hashNecessidadesOriginais.containsKey(idNecessidade) && !hashNecessidades.containsKey(idNecessidade)) {
                // Tinha antes e nao tem mais, logo, apagar
                listRemove.add(new NecessidadeLocal(idLocal, idNecessidade));
            } else if (!hashNecessidadesOriginais.containsKey(idNecessidade) && hashNecessidades.containsKey(idNecessidade)) {
                // Não tinha antes e tem agora, logo, adicionar
                NecessidadeLocal nl;
                // Se o campo a ser adicionado tem precisaObservacao, cria com texto do EditText
                if (hashNecessidadesRef.get(idNecessidade).getFlagPrecisaObs()) {
                    nl = new NecessidadeLocal(observacaoEditText.getText().toString(), idLocal, idNecessidade);
                } else {
                    nl = new NecessidadeLocal(idLocal, idNecessidade);
                }
                listAdd.add(nl);
            } else if (hashNecessidadesOriginais.containsKey(idNecessidade) && hashNecessidades.containsKey(idNecessidade) && hashNecessidadesRef.get(idNecessidade).getFlagPrecisaObs()) {
                // Caso já existia e continua existindo e tem observação, atualiza a observação
                NecessidadeLocal nl = new NecessidadeLocal(observacaoEditText.getText().toString(), idLocal, idNecessidade);
                presenter.atualizaNecessidadeLocal(nl);

            }
        }
        if (listAdd.size() > 0) {
            presenter.salvaNecessidadesLocal(listAdd);
        }
        if (listRemove.size() > 0) {
            presenter.removeNecessidadesLocal(listRemove);
        }
        hashNecessidadesOriginais = (HashMap<Integer, Necessidade>) hashNecessidades.clone();
    }

    @Override
    public void preencheCheckNecessidades(ArrayList<Necessidade> necessidades) {
        for (Necessidade necessidade : necessidades) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(necessidade.getDescricaoNecessidade());

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox.isChecked()) {
                        hashNecessidades.put(necessidade.getIdNecessidade(), necessidade);
                        if (necessidade.getFlagPrecisaObs()) {
                            observacaoEditText.setVisibility(View.VISIBLE);
                        }
                    } else {
                        hashNecessidades.remove(necessidade.getIdNecessidade());
                        if (necessidade.getFlagPrecisaObs()) {
                            observacaoEditText.setVisibility(View.GONE);
                        }
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
    public void preencheCheckNecessidadesLocal(ArrayList<NecessidadeLocal> necessidadesLocal) {
        for (NecessidadeLocal necessidadeLocal : necessidadesLocal) {
            hashCheckbox.get(necessidadeLocal.getIdNecessidade()).setChecked(true);
            hashNecessidades.put(necessidadeLocal.getIdNecessidade(), hashNecessidadesRef.get(necessidadeLocal.getIdNecessidade()));
            if (hashNecessidadesRef.get(necessidadeLocal.getIdNecessidade()).getFlagPrecisaObs()) {
                observacaoEditText.setVisibility(View.VISIBLE);
                observacaoEditText.setText(necessidadeLocal.getObservacao());
            }
        }
        hashNecessidadesOriginais = (HashMap<Integer, Necessidade>) hashNecessidades.clone();
    }

    @Override
    public void exibeToastSucesso() {
        Intent intent = new Intent(NecessidadesActivity.this, CadastroLocalActivity.class);
        intent.putExtra("idLocal", idLocal);
        startActivity(intent);
        Toast.makeText(this, getString(R.string.sucesso), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void exibeToastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                Intent intent = new Intent(NecessidadesActivity.this, CadastroLocalActivity.class);
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
        Intent intent = new Intent(NecessidadesActivity.this, CadastroLocalActivity.class);
        intent.putExtra("idLocal", idLocal);
        startActivity(intent); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }
}