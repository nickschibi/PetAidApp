package com.example.bonnie.petaid.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bonnie.petaid.PetAidApplication;
import com.example.bonnie.petaid.model.Endereco;
import com.example.bonnie.petaid.R;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener, MapsPresenter.View{

    private GoogleMap mMap;
    private List<Endereco> enderecos;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private TextView nomeFantasiaTextView;
    private Button btn;
    private MapsPresenter presenter;


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
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userType = ((PetAidApplication) MapsActivity.this.getApplication()).getTypeUser();
                Intent i = null;
                if(userType.equals("vol")){
                    i = new Intent(MapsActivity.this, PerfilVolActivity.class);
                } else {
                    i = new Intent(MapsActivity.this, CadastroOngActivity.class);
                }
                startActivity(i);
            }
        });

        enderecos = new ArrayList<>();
        presenter.getEnderecos(enderecos);
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

    @Override
    public void  poeMarcadoresEnderecos() throws IOException {

        int height = 200;
        int width = 200;

        double lat = 0;
        double lng = 0;

        for (Endereco e: enderecos) {
            Geocoder gc = new Geocoder(this);
            List<Address> list = gc.getFromLocationName(e.toString(), 1);
            Address address = list.get(0);

            lat = address.getLatitude();
            lng = address.getLongitude();

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
        goToLocationZoom(lat, lng, 10);
    }

    @Override
    public void setaOrganizacaoSlidingPanel(String nomeFantasia) {
        nomeFantasiaTextView.setText(nomeFantasia);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    @Override
    public boolean onMarkerClick(final Marker marker){
        Endereco endereco = (Endereco)marker.getTag();
        presenter.getOrganizacaoByEndereco(endereco);

        return true;
    }

   @Override
     public void onMapClick(LatLng point){
       slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
   }

}
