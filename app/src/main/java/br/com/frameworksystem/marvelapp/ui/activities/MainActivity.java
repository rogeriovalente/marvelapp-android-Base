package br.com.frameworksystem.marvelapp.ui.activities;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;

import br.com.frameworksystem.marvelapp.R;
import br.com.frameworksystem.marvelapp.broadcast.NetworkBroadcast;
import br.com.frameworksystem.marvelapp.fragments.CharacterFragment;
import br.com.frameworksystem.marvelapp.fragments.ComicFragment;
import br.com.frameworksystem.marvelapp.fragments.EventFragment;
import br.com.frameworksystem.marvelapp.fragments.MapsFragment;

public class MainActivity extends BaseActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  @Override
  protected void onResume() {
    super.onResume();

    registerReceiver(networkBroadcast, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
  }

  @Override
  protected void onPause() {
    super.onPause();

    unregisterReceiver(networkBroadcast);
  }

  private NetworkBroadcast networkBroadcast = new NetworkBroadcast() {
    @Override
    public void onConnected(boolean isConnected) {
      if (isConnected) {
        Toast.makeText(MainActivity.this, "Internet voltou!", Toast.LENGTH_LONG).show();
      } else {
        Toast.makeText(MainActivity.this, "Internet caiu!", Toast.LENGTH_LONG).show();
      }
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id != 0) {
      FragmentManager fm = getSupportFragmentManager();
      FragmentTransaction ft = fm.beginTransaction();
      if (id == R.id.nav_personagens) {
        ft.replace(R.id.content_main, new CharacterFragment(0));
        ft.commit();
      } else if (id == R.id.nav_evento) {
        ft.replace(R.id.content_main, new EventFragment());
        ft.commit();
      } else if (id == R.id.nav_comics) {
        ft.replace(R.id.content_main, new CharacterFragment(1));
        ft.commit();
      } else if (id == R.id.nav_maps) {
        ft.replace(R.id.content_main, new MapsFragment());
        ft.commit();
      }
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }
}
