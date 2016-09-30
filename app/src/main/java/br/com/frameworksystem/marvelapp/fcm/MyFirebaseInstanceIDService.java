package br.com.frameworksystem.marvelapp.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by rogerio.valente on 29/09/2016.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

  public void onTokenRefres() {
    //Get updated InstanceID token.
    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
    Log.d("FIREBASEINSTANCEID", "Refreshed token: " + refreshedToken);

    sendRegistrationToServer(refreshedToken);
  }

  private void sendRegistrationToServer(String refreshedToken) {

  }
}
