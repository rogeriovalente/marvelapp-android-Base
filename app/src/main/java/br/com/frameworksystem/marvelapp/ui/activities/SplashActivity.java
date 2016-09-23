package br.com.frameworksystem.marvelapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import br.com.frameworksystem.marvelapp.R;
import br.com.frameworksystem.marvelapp.adapters.CustomPagerAdapter;

/**
 * Created by rogerio.valente on 21/09/2016.
 */

public class SplashActivity extends BaseActivity {
  // Splash screen timer
  private static int SPLASH_TIME_OUT = 3000;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash_layout);
    loadGalleryImages();

    RelativeLayout relLayout = (RelativeLayout) findViewById(R.id.skip);
    relLayout.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View v) {
        finish();
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
      }
    });
    /*new Handler().postDelayed(new Runnable() {
            *//*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             *//*
      @Override
      public void run() {
        // This method will be executed once the timer is over
        // Start your app main activity
        Intent i = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(i);

        // close this activity
        finish();
      }
    }, SPLASH_TIME_OUT);*/

  }

  private void loadGalleryImages() {
    CustomPagerAdapter adapter = new CustomPagerAdapter();
    ViewPager myPager = (ViewPager) findViewById(R.id.viewpager);

    myPager.setAdapter(adapter);
    myPager.setCurrentItem(0);
  }
}
