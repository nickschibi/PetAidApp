package com.example.bonnie.petaid;

import android.app.Application;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PerfilVol extends AppCompatActivity {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private Voluntario voluntario;
    private EditText nome;
    private EditText email;
    private EditText telefone;
    private Button backBtn;
    private Button deleteBtn;
    private EditText nomediag;
    private EditText telefonediag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_vol);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nome = findViewById(R.id.nomeEditText);
        email = findViewById(R.id.emailEditText);
        telefone = findViewById(R.id.telefoneEditText);
        backBtn = findViewById(R.id.backbtn);
        deleteBtn = findViewById(R.id.deletbtn);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogExcluir();

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PerfilVol.this, MapsActivity.class);
                startActivity(i);
            }
        });


        String trazVoluntario = getString(R.string.web_service_url) + "voluntario?email=" + ((PetAidApplication)this.getApplication()).getEmailSignUser();
        new PerfilVol.ConsomeServico(new PerfilVol.PosExecucao() {
            @Override
            public void executa(String resultado) {
                Type foundType = new TypeToken<ArrayList<Voluntario>>(){}.getType();
                ArrayList<Voluntario> voluntarios = new Gson().fromJson(resultado,foundType);
                voluntario = voluntarios.get(0);

                if(voluntario != null) {
                    nome.setText(voluntario.getNome_voluntario());
                    email.setText(voluntario.getEmail());
                    telefone.setText(voluntario.getTelefone_voluntario());
                }

            }
        }, "get").execute(trazVoluntario);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            updateVoluntario();

            }
        });
    }


    private interface PosExecucao{
        void executa(String resultado);
    }

    private class ConsomeServico extends AsyncTask<String, Void, String> {

        private PerfilVol.PosExecucao posExecucao;
        private String metodo;
        private String requestBody;

        public ConsomeServico(PerfilVol.PosExecucao posExecucao, String metodo){
            this.posExecucao = posExecucao;
            this.metodo = metodo;
        }

        public ConsomeServico(PerfilVol.PosExecucao posExecucao, String metodo, String requestBody){
            this.posExecucao = posExecucao;
            this.metodo = metodo;
            this.requestBody = requestBody;
        }

        @Override
        protected String doInBackground(String... url) {
            OkHttpClient client = new OkHttpClient();
            Request request = null;
            if(metodo.equals("get")){
                request = new Request.Builder().url(url[0]).build();
            } else if (metodo.equals("delete")){
                request = new Request.Builder().url(url[0]).delete().build();
            }else if (metodo.equals("put")){
                RequestBody body = RequestBody.create(JSON, requestBody);
                request = new Request.Builder().url(url[0]).put(body).build();
            }

            okhttp3.Response response = null;
            try {
                response = client.newCall(request).execute();
                String resultado = response.body().string();
                return resultado;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String resultado) {
            this.posExecucao.executa(resultado);
        }

    }

    public AlertDialog alerta;
    private void dialogExcluir(){
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialog);
        builder.setMessage(Html.fromHtml("<font color='#FFFFFF'>" + getText(R.string.confirmaExcluir)+ "</font>"))
                .setPositiveButton(R.string.excluir, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        String deletaVoluntario = getString(R.string.web_service_url) + "voluntario/" + voluntario.getId_voluntario();
                        new ConsomeServico(new PosExecucao() {
                            @Override
                            public void executa(String resultado) {
                                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
                                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(PerfilVol.this, gso);
                                googleSignInClient.revokeAccess();
                                googleSignInClient.signOut();
                                Toast.makeText(PerfilVol.this, "Cadastro excluido", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(PerfilVol.this, SplashScreen.class);
                                startActivity(i);

                            }
                        }, "delete").execute(deletaVoluntario);

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

    void  updateVoluntario(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialog);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialogupdate, null);
        builder.setView(dialogView);
        builder.setTitle("Atulização de Dados");
        builder.setMessage("");
        nomediag = dialogView.findViewById(R.id.nomeEditTextDiag);
        nomediag.setText(voluntario.getNome_voluntario());
        telefonediag = dialogView.findViewById(R.id.telefoneEditTextDiag);
        telefonediag.setText(voluntario.getTelefone_voluntario());

        telefonediag.addTextChangedListener(new PhoneNumberFormattingTextWatcher() {
            int length_before = 0;
            private boolean mFormatting;


            @Override
            public void afterTextChanged(Editable s) {
                // Make sure to ignore calls to afterTextChanged caused by the work done below
                if (!mFormatting) {
                    mFormatting = true;
                    String l = Locale.getDefault().getCountry();

                    String num =s.toString();
                    String data = PhoneNumberUtils.formatNumber(num, l);
                    if(data!=null)
                    {
                        s.clear();
                        s.append(data);
                    }
                    mFormatting = false;
                }
            }});




        builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                boolean validaCampos = true; // okay

                if( Utils.isCampoVazio(nomediag.getText().toString())){
                    validaCampos = false;
                    nome.requestFocus();
                }
                if( !Utils.isName(nomediag.getText().toString())){
                    validaCampos = false;
                    nome.requestFocus();
                }
                if(!Utils.isPhone(telefonediag.getText().toString())){
                    validaCampos = false;
                    telefone.requestFocus();
                }
                if(!Utils.isTelefone(telefonediag.getText().toString())){
                    validaCampos = false;
                    telefone.requestFocus();
                }
                if(!Utils.isEmailValido(email.getText().toString())) {
                    validaCampos = false;
                    email.requestFocus();
                }
                if(validaCampos == false){

                    Toast.makeText(PerfilVol.this, R.string.camposInvalidos, Toast.LENGTH_SHORT).show();
                }
                 else {
                    voluntario.setNome_voluntario(nomediag.getText().toString());
                    voluntario.setTelefone_voluntario(telefonediag.getText().toString());
                    String atualizaVoluntario = getString(R.string.web_service_url) + "voluntario/" + voluntario.getId_voluntario();
                    Gson gson = new Gson();

                    new ConsomeServico(new PosExecucao() {
                        @Override
                        public void executa(String resultado) {
                            nome.setText(voluntario.getNome_voluntario());
                            telefone.setText(voluntario.getTelefone_voluntario());
                            Toast.makeText(PerfilVol.this, "Sucesso nas Alterações ", Toast.LENGTH_SHORT).show();

                        }
                    }, "put", gson.toJson(voluntario)).execute(atualizaVoluntario);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {


            }
        });


        builder.show();
    }

}


