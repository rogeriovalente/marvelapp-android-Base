package br.com.frameworksystem.marvelapp.ui.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import br.com.frameworksystem.marvelapp.R;
import br.com.frameworksystem.marvelapp.model.Event;
import br.com.frameworksystem.marvelapp.model.MarvelUrl;
import br.com.frameworksystem.marvelapp.service.LogIntentService;

/**
 * Created by rogerio.valente on 14/09/2016.
 */
public class EventDetailActivity extends BaseActivity {

  private Event event;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.eventdetail);

    if (getIntent().hasExtra("event")) {
      event = (Event) getIntent().getSerializableExtra("event");
    }

    WebView webView = (WebView) findViewById(R.id.webView_eventdetail);
    MarvelUrl url = event.getUrls().get(0);
    webView.loadUrl(url.url);

    final Intent logIntent = new Intent(EventDetailActivity.this, LogIntentService.class);
    logIntent.putExtra("serviceLog", event.getTitle());
    startService(logIntent);
  }
}
