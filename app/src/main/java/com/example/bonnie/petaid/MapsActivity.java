package com.example.bonnie.petaid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener{

    private GoogleMap mMap;
    private List<Endereco> enderecos;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private String url;
    private TextView nomeFantasiaTextView;
    private Button btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        slidingUpPanelLayout = findViewById(R.id.sliding_layout);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        nomeFantasiaTextView = findViewById(R.id.nomeFantasiaTextView);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapsActivity.this, QuestionUser.class);
                startActivity(i);
            }
        });

        enderecos = new ArrayList<>();

        String trazEnderecos = getString(R.string.web_service_url) + "endereco/";
        new ConsomeServico(new PosExecucao() {
            @Override
            public void executa(String resultado) {
                enderecos.clear();
                Type foundListType = new TypeToken<ArrayList<Endereco>>(){}.getType();
                ArrayList<Endereco> e = new Gson().fromJson(resultado,foundListType);
                if(e != null) {
                    enderecos.addAll(e);
                }
                try {
                    pegaGeoLocalizacao();
                }
                catch(IOException msg){
                }
            }
        }).execute(trazEnderecos);

    }

    //Consome Serviço

    private interface PosExecucao{
        void executa(String resultado);
    }

    private class ConsomeServico extends AsyncTask<String, Void, String> {

        private PosExecucao posExecucao;

        public ConsomeServico(PosExecucao posExecucao){
            this.posExecucao = posExecucao;
        }

        @Override
        protected String doInBackground(String... url) {
            OkHttpClient client = new OkHttpClient();
            Request request =
                    new Request.Builder()
                            .url(url[0])
                            .build();
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

    // Mapa

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //um ponto estático na paulista para iniciar o mapa sem a localização do usuário.
        LatLng saoPaulo = new LatLng(-23.563987128451217,-46.60400390625);
        //mMap.addMarker(new MarkerOptions().position(saoPaulo).title("Você está aqui"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(saoPaulo));
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
    }


    private void goToLocationZoom (double lat, double lng, float zoom){
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,zoom);
        mMap.moveCamera(update);
    }

    public void  pegaGeoLocalizacao () throws IOException {

        int height = 200;
        int width = 200;

        for (Endereco e: enderecos) {
            Geocoder gc = new Geocoder(this);
            List<Address> list = gc.getFromLocationName(e.toString(), 1);
            Address address = list.get(0);

            double lat = address.getLatitude();
            double lng = address.getLongitude();
            goToLocationZoom(lat, lng, 10);

            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.pata, null);
            Bitmap b = bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

            MarkerOptions options = new MarkerOptions()
                    //.title(address.getCountryName())
                    .position(new LatLng(lat, lng))
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker));

            Marker marker = mMap.addMarker(options);
            marker.setTag(e);
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker){

        Endereco e = (Endereco)marker.getTag();

       String trazOng = getString(R.string.web_service_url) + "organizacao?id_endereco=" + e.getIdEndereco();

       new ConsomeServico(new PosExecucao() {
           @Override
           public void executa(String resultado) {

               Type foundListType = new TypeToken<ArrayList<Organizacao>>(){}.getType();
              ArrayList<Organizacao> organizacoes= new Gson().fromJson(resultado,foundListType);
                Organizacao o = organizacoes.get(0);
               nomeFantasiaTextView.setText(o.getNome_fantasia());
               slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
           }
       }).execute(trazOng);



        return true;
    }

   @Override
     public void onMapClick(LatLng point){
       slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
   }

}
