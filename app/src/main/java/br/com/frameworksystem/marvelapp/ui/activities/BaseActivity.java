package br.com.frameworksystem.marvelapp.ui.activities;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import br.com.frameworksystem.marvelapp.broadcast.NetworkBroadcast;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by rogerio.valente on 15/09/2016.
 */
public class BaseActivity extends AppCompatActivity {

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

}
