package com.example.bonnie.petaid;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class CadastroVol extends AppCompatActivity {
    private FloatingActionButton btnSave;
    private EditText nome;
    private EditText email;
    private EditText telefone;
    private String requestJson;

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_vol);
        nome =  findViewById(R.id.nomeEditText);
        email = findViewById(R.id.emailEditText);
        telefone = findViewById(R.id.telefoneEditText);
        btnSave = findViewById(R.id.saveButton);

        Bundle bundle = getIntent().getExtras();
        email.setText(bundle.getString("email"));

        telefone.addTextChangedListener(new PhoneNumberFormattingTextWatcher() {
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
            }
        });


//        new CadastroVol.ConsomeServico(new CadastroVol.PosExecucao() {
//            @Override
//            public void executa(String resultado) {
//
//            }
//        }).execute(urlVol);



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                boolean validaCampos = true; // okay

                if( Utils.isCampoVazio(nome.getText().toString())){
                    validaCampos = false;
                    nome.requestFocus();
                }
                if( !Utils.isName(nome.getText().toString())){
                    validaCampos = false;
                    nome.requestFocus();
                }
                if(!Utils.isPhone(telefone.getText().toString())){
                    validaCampos = false;
                    telefone.requestFocus();
                }
                if(!Utils.isTelefone(telefone.getText().toString())){
                    validaCampos = false;
                    telefone.requestFocus();
                }
                if(!Utils.isEmailValido(email.getText().toString())) {
                    validaCampos = false;
                    email.requestFocus();
                }
                if(validaCampos == false){

                    AlertDialog.Builder dlg = new AlertDialog.Builder(CadastroVol.this);
                    dlg.setTitle(R.string.warning);
                    dlg.setMessage(R.string.camposInvalidos);
                    dlg.setNeutralButton("Ok", null);
                    dlg.show();
                }
                else {
                    Voluntario voluntario = new Voluntario(nome.getText().toString(),
                            email.getText().toString(),
                            telefone.getText().toString());


                    Gson gs = new Gson();
                    String jsonInString = gs.toJson(voluntario);
                    requestJson = jsonInString;
                    String urlVol = getString(R.string.web_service_url) + "voluntario";
                    new CadastroVol.ConsomeServico().execute(urlVol);
                }



            }
        });
    }

//    private interface PosExecucao{
//        void executa(String resultado);
//    }
    private class ConsomeServico extends AsyncTask<String, Void, String> {
        private int codigo;
        //private CadastroVol.PosExecucao posExecucao;

//        public ConsomeServico(CadastroVol.PosExecucao posExecucao){
//            this.posExecucao = posExecucao;
//        }

        @Override
        protected String doInBackground(String... url) {
            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON, requestJson);

            Request request =
                    new Request.Builder()
                            .url(url[0])
                            .post(body)
                            .build();
            okhttp3.Response response = null;
            try {
                response = client.newCall(request).execute();
                codigo = response.code();
                String resultado = response.body().string();
                return resultado;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String resultado) {

            //this.posExecucao.executa(resultado);
            if(codigo == 200){
                Toast.makeText(CadastroVol.this, "Cadastro feito com sucesso!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(CadastroVol.this, MapsActivity.class);
                startActivity(i);

            }


        }

    }



}
