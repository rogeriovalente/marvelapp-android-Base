package br.com.frameworksystem.marvelapp.ui.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import br.com.frameworksystem.marvelapp.R;

/**
 * Created by rogerio.valente on 03/10/2016.
 */

public class PlaceActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    LocationListener {

  private GoogleApiClient mGoogleApiClient;
  private Location mCurrentLocation;
  private LocationRequest mLocationRequest;
  private TextView userLocation;
  private Double latitude;
  private Double longitude;
  private LatLng latLng;

  private LatLng ponto1;
  private LatLng ponto2;
  private LatLng ponto3;
  private String distanceBetweenPoints;

  final int REQUEST_ACCESS_COARSE_LOCATION = 1;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.place);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    if (getIntent().hasExtra("latitude")) {
      latitude = getIntent().getDoubleExtra("latitude", 0);
      longitude = getIntent().getDoubleExtra("longitude", 0);
      latLng = new LatLng(latitude, longitude);
    }
    if (getIntent().hasExtra("ponto1")) {
      ponto1 = getIntent().getParcelableExtra("ponto1");
      ponto2 = getIntent().getParcelableExtra("ponto2");
      ponto3 = getIntent().getParcelableExtra("ponto3");

      float[] result = new float[1];
      Location.distanceBetween(ponto1.latitude, ponto1.longitude, ponto2.latitude, ponto2.longitude, result);
      float distanceP1toP2 = (result[0] / 1000);

      Location.distanceBetween(ponto2.latitude, ponto2.longitude, ponto3.latitude, ponto3.longitude, result);
      float distanceP2toP3 = (result[0] / 1000);

      Location.distanceBetween(ponto3.latitude, ponto3.longitude, ponto1.latitude, ponto1.longitude, result);
      float distanceP3toP1 = (result[0] / 1000);

      distanceBetweenPoints = String.format("Distância entre P1 e P2 é de %.0f KM. ", distanceP1toP2) +
          "\n" + String.format("Distância entre P2 e P3 é de %.0f KM. ", distanceP2toP3) +
          "\n" + String.format("Distância entre P3 e P1 é de %.0f KM. ", distanceP3toP1);

    }

    userLocation = (TextView) findViewById(R.id.user_location);
    createLocationRequest();

  }

  @Override
  protected void onResume() {
    super.onResume();
    buildGoogleApiClient();
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
      mGoogleApiClient.disconnect();
    }
  }

  protected void buildGoogleApiClient() {
    mGoogleApiClient = new GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .build();

    mGoogleApiClient.connect();
  }

  private void createLocationRequest() {
    mLocationRequest = new LocationRequest();
    mLocationRequest.setInterval(10000);
    mLocationRequest.setFastestInterval(5000);
    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
  }

  protected void startLocationUpdates() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        == PackageManager.PERMISSION_GRANTED) {
      LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
          mLocationRequest, this);
    }

  }

  protected void stopLocationUpdates() {
    LocationServices.FusedLocationApi.removeLocationUpdates(
        mGoogleApiClient, this);
  }

  @Override
  public void onConnected(@Nullable Bundle bundle) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {  // Only ask for these permissions on runtime when running Android 6.0 or higher
      switch (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
        case PackageManager.PERMISSION_DENIED:
          ((TextView) new AlertDialog.Builder(this)
              .setTitle("Runtime Permissions up ahead")
              .setMessage(Html.fromHtml("<p>To find nearby bluetooth devices please click \"Allow\" on the runtime permissions popup.</p>" +
                  "<p>For more info see <a href=\"http://developer.android.com/about/versions/marshmallow/android-6.0-changes.html#behavior-hardware-id\">here</a>.</p>"))
              .setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  if (ContextCompat.checkSelfPermission(PlaceActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PlaceActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_ACCESS_COARSE_LOCATION);
                  }
                }
              })
              .show()
              .findViewById(android.R.id.message))
              .setMovementMethod(LinkMovementMethod.getInstance());       // Make the link clickable. Needs to be called after show(), in order to generate hyperlinks
          break;
        case PackageManager.PERMISSION_GRANTED:
          break;
      }
    }

    mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    showUserLocation(mCurrentLocation);

    startLocationUpdates();

  }

  private void showUserLocation(Location location) {
    if (location != null) {
      float[] result = new float[1];

      if (latLng != null) {
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
            latLng.latitude, latLng.longitude, result);
        float distance = (result[0] / 1000);
        userLocation.setText(String.format("Você encotra-se a %.0f KM.", distance));
      }
      if (ponto1 != null) {
        Location.distanceBetween(location.getLatitude(), location.getLongitude(), ponto1.latitude, ponto1.longitude, result);
        float distanceMeToP1 = (result[0] / 1000);
        Location.distanceBetween(location.getLatitude(), location.getLongitude(), ponto2.latitude, ponto2.longitude, result);
        float distanceMeToP2 = (result[0] / 1000);
        Location.distanceBetween(location.getLatitude(), location.getLongitude(), ponto3.latitude, ponto3.longitude, result);
        float distanceMeToP3 = (result[0] / 1000);

        String strDistancePoints = "\n" +
            "\n" + String.format("Distância entre mim e P1 é de %.0f KM. ", distanceMeToP1) +
            "\n" + String.format("Distância entre mim e P2 é de %.0f KM. ", distanceMeToP2) +
            "\n" + String.format("Distância entre mim e P3 é de %.0f KM. ", distanceMeToP3);

        userLocation.setText(distanceBetweenPoints + strDistancePoints);
      }
    }
  }

  @Override
  public void onConnectionSuspended(int i) {

  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

  }

  @Override
  public void onLocationChanged(Location location) {

  }
}

