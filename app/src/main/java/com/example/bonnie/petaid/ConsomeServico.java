package com.example.bonnie.petaid;

import android.os.AsyncTask;
import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ConsomeServico extends AsyncTask<String, Void, String> {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private int returnCode;
    private String url;
    private String requestBody;
    private PosExecucao posExecucao;
    private Metodo metodo;

    public ConsomeServico(String url, Metodo metodo){
        this.url = url;
        this.metodo = metodo;
    }

    public ConsomeServico(String url, Metodo metodo, PosExecucao posExecucao){
        this.url = url;
        this.metodo = metodo;
        this.posExecucao = posExecucao;
    }

    public ConsomeServico(String url, Metodo metodo, String requestBody){
        this.url = url;
        this.metodo = metodo;
        this.requestBody = requestBody;
    }

    public ConsomeServico(String url, Metodo metodo, String requestBody, PosExecucao posExecucao){
        this.url = url;
        this.metodo = metodo;
        this.posExecucao = posExecucao;
        this.requestBody = requestBody;
    }

    public int getReturnCode() {
        return returnCode;
    }

    @Override
    protected String doInBackground(String... url) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = null;
        if(requestBody != null) {
            body = RequestBody.create(JSON, requestBody);
        }
        Request request = null;

        if(metodo.equals(Metodo.GET)){
            request = new Request.Builder().url(url[0]).build();
        } else if (metodo.equals(Metodo.DELETE)){
            request = new Request.Builder().url(url[0]).delete().build();
        } else if (metodo.equals(Metodo.PUT)){
            request = new Request.Builder().url(url[0]).put(body).build();
        } else if (metodo.equals(Metodo.POST)){
            request = new Request.Builder().url(url[0]).post(body).build();
        }

        okhttp3.Response response = null;
        try {
            response = client.newCall(request).execute();
            returnCode = response.code();
            String resultado = response.body().string();
            return resultado;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String resultado) {
        if(posExecucao != null){
            this.posExecucao.executa(resultado, returnCode);
        }
    }

    public void executa(){
        this.execute(url);
    }

    public String executaSincrono() throws ExecutionException, InterruptedException,CancellationException {
        String resultado = null;
        try {
            resultado = this.execute(url).get();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return resultado;
    }

    public interface PosExecucao{
        void executa(String resultado, int returnCode);
    }

    public enum Metodo{
        GET,
        POST,
        PUT,
        DELETE
    }
}
