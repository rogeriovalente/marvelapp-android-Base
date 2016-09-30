package br.com.frameworksystem.marvelapp;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;

import br.com.frameworksystem.marvelapp.api.AuthInterceptor;
import io.fabric.sdk.android.Fabric;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by rogerio.valente on 15/09/2016.
 */
public class MarvelApp extends Application {

  private OkHttpClient okHttpClient;
  private final int cacheSize = 5 * 1024 * 1024;
  private static final String PUBLIC_KEY = "a3534eb05ca603034721f53b47c95721";
  private static final String PRIVATE_KEY = "40d4f24d7b3b12fab92e0bb26619071166663fc9";

  @Override
  public void onCreate() {
    super.onCreate();
    Fabric.with(this, new Crashlytics());
    CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
        .setDefaultFontPath("fonts/Roboto-Regular.ttf")
        .setFontAttrId(R.attr.fontPath)
        .build());

    createOkHttpClient();
  }

  private void createOkHttpClient() {
    Cache cache = new Cache(getCacheDir(), cacheSize);

    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder.interceptors().add(new AuthInterceptor(PUBLIC_KEY, PRIVATE_KEY));
    builder.cache(cache);

    okHttpClient = builder.build();
  }

  public static MarvelApp getInstance(Context context) {
    return (MarvelApp) context.getApplicationContext();
  }

  public OkHttpClient getOkHttpClient() {
    return okHttpClient;
  }
}
