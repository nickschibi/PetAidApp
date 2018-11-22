package com.example.bonnie.petaid.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bonnie.petaid.PetAidApplication;
import com.example.bonnie.petaid.model.Endereco;
import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.model.Local;
import com.example.bonnie.petaid.model.NecessidadeLocal;
import com.example.bonnie.petaid.model.Voluntario;
import com.example.bonnie.petaid.presenter.MapsPresenter;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener, MapsPresenter.View {

    private GoogleMap mMap;
    private List<Local> locais;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private Button btn;
    private MapsPresenter presenter;
    private TextView descricaoTextView;
    private TextView nomeFantasiaTextView;
    private TextView razaoSocialTextView;
    private TextView necessidadesTextView;
    private TextView observaçaoTextView;
    private Button btnVoluntariarse;
    private Button btnAvaliar;
    private Local local;
    private Voluntario voluntario;
    private RatingBar ratingBar;
    private double avaliacao;
    private TextView avaliacaoTextView;
    private TextView mediaNota;
    private LinearLayout linearNota;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MapsPresenter(this, this);
        setContentView(R.layout.activity_maps);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        slidingUpPanelLayout = findViewById(R.id.sliding_layout);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        nomeFantasiaTextView = findViewById(R.id.nomeFantasiaTextView);
        descricaoTextView = findViewById(R.id.descricaoOngTextView);
        razaoSocialTextView = findViewById(R.id.razaoSocialTextView);
        necessidadesTextView = findViewById(R.id.necessidadesTextView);
        observaçaoTextView = findViewById(R.id.observacaoTextView);
        btn = findViewById(R.id.btn);
        btnVoluntariarse = findViewById(R.id.btnVoluntariar);
        btnAvaliar = findViewById(R.id.btnAvaliar);
        mediaNota = findViewById(R.id.notaLocal);
        linearNota = findViewById(R.id.linearNota);

        if (((PetAidApplication) MapsActivity.this.getApplication()).getTypeUser().equals("vol")) {
            btnVoluntariarse.setVisibility(View.VISIBLE);
            presenter.pegaVoluntario(((PetAidApplication)MapsActivity.this.getApplication()).getEmailSignUser());
        }


        btnVoluntariarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.criaVoluntariado(local.getIdLocal(), voluntario);
            }
        });

        btnAvaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avaliaOng();
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userType = ((PetAidApplication) MapsActivity.this.getApplication()).getTypeUser();
                Intent i = null;
                if (userType.equals("vol")) {
                    i = new Intent(MapsActivity.this, PerfilVolActivity.class);
                } else {
                    i = new Intent(MapsActivity.this, CadastroOngActivity.class);
                }
                startActivity(i);
            }
        });

        locais = new ArrayList<>();
        presenter.getLocais(locais);
    }



    void avaliaOng(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialog);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.avaliacao_dialog, null);
        builder.setView(dialogView);
        builder.setTitle("Avaliação");
        builder.setMessage("");
        avaliacaoTextView = dialogView.findViewById(R.id.avaliacaoTextView);
        ratingBar = dialogView.findViewById(R.id.ratingBarAvaliacao);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating == 0){
                    avaliacao = 0;
                }
                else if(rating == 0.5){
                    avaliacao = 0.5;
                }
                else if(rating == 1.0){
                    avaliacao = 1.0;
                }
                else if(rating == 1.5){
                    avaliacao = 1.5;
                }
                else if(rating == 2.0){
                    avaliacao = 2.0;
                }
                else if(rating == 2.5){
                    avaliacao = 2.5;
                }
                else if(rating == 3.0){
                    avaliacao = 3.0;
                }
                else if(rating == 3.5){
                    avaliacao = 3.5;
                }
                else if(rating == 4.0){
                    avaliacao = 4.0;
                }
                else if(rating == 4.5){
                    avaliacao = 4.5;
                }
                else if(rating == 5.0){
                    avaliacao = 5.0;
                }

                String value = Double.toString(avaliacao);
                avaliacaoTextView.setText(value);
            }
        });


        builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        builder.show();
    }



    // Mapa
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //um ponto estático na paulista para iniciar o mapa sem a localização do usuário.
        LatLng saoPaulo = new LatLng(-23.563987128451217, -46.60400390625);
        //mMap.addMarker(new MarkerOptions().position(saoPaulo).title("Você está aqui"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(saoPaulo));
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
    }


    private void goToLocationZoom(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);
    }

    @Override
    public void poeMarcadoresEnderecos() throws IOException {

        int height = 200;
        int width = 200;

        double lat = 0;
        double lng = 0;

        for (Local l : locais) {
            Geocoder gc = new Geocoder(this);
            List<Address> list = gc.getFromLocationName(l.getEndereco().toString(), 1);
            Address address = list.get(0);

            lat = address.getLatitude();
            lng = address.getLongitude();

            if(l.getCountNecessidades()>=6){
                BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.patanecessidade, null);
                Bitmap b = bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                MarkerOptions options = new MarkerOptions()
                        //.title(address.getCountryName())
                        .position(new LatLng(lat, lng))
                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker));

                Marker marker = mMap.addMarker(options);
                marker.setTag(l);
            }else {
                BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.pata, null);
                Bitmap b = bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                MarkerOptions options = new MarkerOptions()
                        //.title(address.getCountryName())
                        .position(new LatLng(lat, lng))
                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker));

                Marker marker = mMap.addMarker(options);
                marker.setTag(l);
            }

        }
        goToLocationZoom(lat, lng, 10);
    }

    @Override
    public void setaOrganizacaoSlidingPanel(String nomeFantasia, String descricao, String razaoSocial, float notaLocal) {
        nomeFantasiaTextView.setText(nomeFantasia);
        descricaoTextView.setText(descricao);
        razaoSocialTextView.setText(razaoSocial);
        mediaNota.setText((Float.toString(notaLocal)));
        if(notaLocal>0.0){
            linearNota.setVisibility(View.VISIBLE); // Só funciona na primeira vez que clica, verificar isso.
        } else {
            linearNota.setVisibility(View.GONE);
        }
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    @Override
    public void setaNecessidades(ArrayList<NecessidadeLocal> necessidadesLocal) {
        if (necessidadesLocal.size() == 0) {
            necessidadesTextView.setText("Não possuimos nenhuma necessidade especifica no momento");
        } else {
            String sb = "";
            for (NecessidadeLocal nl : necessidadesLocal) {
                if (!nl.getNecessidade().getDescricaoNecessidade().equals("Outros")) {
                    sb += (nl.getNecessidade().getDescricaoNecessidade()) + ", ";
                } else {
                    observaçaoTextView.setText(nl.getObservacao());
                }
            }
            sb = sb.substring(0, sb.length() - 2);
            necessidadesTextView.setText(sb);
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        local = (Local) marker.getTag();
        presenter.getOrganizacaoByEndereco(local.getEndereco(), local);

        if(((PetAidApplication) MapsActivity.this.getApplication()).getTypeUser().equals("vol")){
            presenter.trazVoluntariado(local, voluntario);
        }

        return true;
    }

    @Override
    public void onMapClick(LatLng point) {
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }

    @Override
    public void exibeToastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void mudaEstadoBotoes(Boolean flag) {
        if (flag.equals(true)) {
            btnVoluntariarse.setText("Desvoluntariar-se");
            btnAvaliar.setVisibility(View.VISIBLE);



        } else if (flag.equals(false)) {
            btnVoluntariarse.setText("Voluntariar-se");
            btnAvaliar.setVisibility(View.GONE);

        }

    }

    @Override
    public void setaVoluntario(Voluntario voluntario){
        this.voluntario = voluntario;
    }
}

