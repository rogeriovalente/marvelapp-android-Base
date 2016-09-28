package br.com.frameworksystem.marvelapp.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import br.com.frameworksystem.marvelapp.ui.activities.MainActivity;

/**
 * Created by rogerio.valente on 27/09/2016.
 */

public class BootBroadCastReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    Intent intentMain = new Intent(context, MainActivity.class);
    intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intentMain);
  }
}
