package br.com.frameworksystem.marvelapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

/**
 * Created by rogerio.valente on 28/09/2016.
 */

public class LogIntentService extends IntentService {

  public LogIntentService() {
    super("LOGSERVICE");
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    final String stringToLog = intent.getStringExtra("serviceLog");
    new Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        Log.i("serviceLog", stringToLog);
      }
    }, 5000);
  }
}
