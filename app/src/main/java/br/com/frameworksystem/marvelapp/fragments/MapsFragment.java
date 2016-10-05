package br.com.frameworksystem.marvelapp.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import br.com.frameworksystem.marvelapp.R;
import br.com.frameworksystem.marvelapp.ui.activities.PlaceActivity;

/**
 * Created by rogerio.valente on 03/10/2016.
 */

public class MapsFragment extends Fragment {

  GoogleMap googleMap;
  LatLng latLng;

  LatLng ponto1 = new LatLng(-19.949053, -43.919964);
  LatLng ponto2 = new LatLng(-19.922742, -43.945114);
  LatLng ponto3 = new LatLng(-19.865833, -43.970833);

  public MapsFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.mapfragment, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
        .findFragmentById(R.id.google_maps);

    mapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(GoogleMap googleMap) {
        MapsFragment.this.googleMap = googleMap;
        setup();
      }
    });

    Button btnDistance = (Button) view.findViewById(R.id.btn_distance);
    btnDistance.setOnClickListener(onBtnDistanceClickListener);
  }

  private View.OnClickListener onBtnDistanceClickListener = new View.OnClickListener(){
    @Override
    public void onClick(View v) {
      Intent mapIntent = new Intent(getActivity(), PlaceActivity.class);
      mapIntent.putExtra("ponto1", ponto1);
      mapIntent.putExtra("ponto2", ponto2);
      mapIntent.putExtra("ponto3", ponto3);
      getContext().startActivity(mapIntent);
    }
  };

  private void setup(){
    googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

    if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
      googleMap.setMyLocationEnabled(true);
      googleMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    googleMap.setOnInfoWindowClickListener(onInfoWindowClickListener);
    UiSettings settings = googleMap.getUiSettings();
    settings.setMyLocationButtonEnabled(true);
    settings.setZoomControlsEnabled(true);
    settings.setCompassEnabled(true);

    latLng = new LatLng(10, 10);
    MarkerOptions markerOptions = new MarkerOptions();
    markerOptions.position(latLng).title("Framework Systems").snippet("Rua Rio de Janeiro, 1278 - Centro - Belo Horizonte/MG");
    googleMap.addMarker(markerOptions);

    adicionarMarcador(googleMap, ponto1, "Ponto 1", "Esse é o ponto 1");
    adicionarMarcador(googleMap, ponto2, "Ponto 2", "Esse é o ponto 2");
    adicionarMarcador(googleMap, ponto3, "Ponto 3", "Esse é o ponto 3");

    adicionaPolyline(googleMap, ponto1, ponto2, ponto3);
  }

  private void adicionaPolyline(GoogleMap googleMap, LatLng ponto1, LatLng ponto2, LatLng ponto3) {
    PolylineOptions line = new PolylineOptions();
    line.add(ponto1);
    line.add(ponto2);
    line.add(ponto3);
    line.add(ponto1);
    line.color(Color.BLUE);
    Polyline polyline = googleMap.addPolyline(line);
    polyline.setGeodesic(Boolean.TRUE);
  }

  private Marker adicionarMarcador(GoogleMap googleMap, LatLng latLng, String latTitle, String latSnippet){
    MarkerOptions markerOptions = new MarkerOptions();
    markerOptions.position(latLng).title(latTitle).snippet(latSnippet);
    Marker marker = googleMap.addMarker(markerOptions);
    return marker;
  }

  private GoogleMap.OnInfoWindowClickListener onInfoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
    @Override
    public void onInfoWindowClick(Marker marker) {
      Intent mapIntent = new Intent(getActivity(), PlaceActivity.class);
      mapIntent.putExtra("latitude", marker.getPosition().latitude);
      mapIntent.putExtra("longitude", marker.getPosition().longitude);
      getContext().startActivity(mapIntent);
    }
  };
}
