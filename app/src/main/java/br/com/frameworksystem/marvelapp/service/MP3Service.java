package br.com.frameworksystem.marvelapp.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by rogerio.valente on 27/09/2016.
 */

public class MP3Service extends Service implements MP3Player {
  public final MP3Binder mp3Binder = new MP3Binder();

  private MediaPlayer mediaPlayer;

  public MP3Service() {

  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return mp3Binder;
  }

  @Override
  public void play(String url) {
    try {
      mediaPlayer = new MediaPlayer();
      mediaPlayer.setDataSource(url);
      mediaPlayer.prepare();
      mediaPlayer.start();
    } catch (Exception e) {
      e.getMessage();
    }
  }

  @Override
  public void stop() {
    mediaPlayer.stop();
  }

  @Override
  public void pause() {

  }

  public class MP3Binder extends Binder {
    public MP3Player getService() {
      return MP3Service.this;
    }
  }
}
