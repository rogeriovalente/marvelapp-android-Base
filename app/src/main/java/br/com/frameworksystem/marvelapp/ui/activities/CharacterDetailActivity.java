package br.com.frameworksystem.marvelapp.ui.activities;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.frameworksystem.marvelapp.R;
import br.com.frameworksystem.marvelapp.contantsenums.Constants;
import br.com.frameworksystem.marvelapp.db.SQLiteHelper;
import br.com.frameworksystem.marvelapp.model.Character;
import br.com.frameworksystem.marvelapp.service.MP3Player;
import br.com.frameworksystem.marvelapp.service.MP3Service;

/**
 * Created by rogerio.valente on 14/09/2016.
 */
public class CharacterDetailActivity extends BaseActivity {

  private Character character;
  private SQLiteDatabase db;

  private MP3Player mp3Player;

  private ServiceConnection serviceConnection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      mp3Player = ((MP3Service.MP3Binder) service).getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
      mp3Player = null;
    }
  };

  private void connectService() {
    Intent intent = new Intent(this, MP3Service.class);
    this.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
  }

  @Override
  protected void onStop() {
    super.onStop();
    this.unbindService(serviceConnection);
  }

  @Override
  protected void onStart() {
    super.onStart();
    connectService();
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    db = SQLiteHelper.getDatabase(this);
    setContentView(R.layout.characterdetail);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    character = (Character) getIntent().getSerializableExtra("character");
    setTitle(character.getName());

    ImageView imageView = (ImageView) findViewById(R.id.character_detail_thumb);
    Picasso.with(this).load(character.getThumbnailUrl()).centerCrop().resize(600, 600).into(imageView);
    TextView textView = (TextView) findViewById(R.id.character_detail_description);
    textView.setText(character.getDescription());

    Button btnPlay = (Button) findViewById(R.id.btn_play);
    btnPlay.setOnClickListener(new View.OnClickListener(){

      @Override
      public void onClick(View v) {
        mp3Player.play("http://www.instantsfun.es/audio/acdc.mp3");
      }
    });

    Button btnStop = (Button) findViewById(R.id.btn_stop);
    btnStop.setOnClickListener(new View.OnClickListener(){

      @Override
      public void onClick(View v) {
        mp3Player.stop();
      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();

    List<Character> lstDados = carregarDados(character.getName());
    if (lstDados != null && !lstDados.isEmpty()) {
      character = lstDados.get(0);
    }
  }

  private List<Character> carregarDados(String name) {
    List<Character> characterList = new ArrayList<Character>();
    if (!db.isOpen()) {
      db = SQLiteHelper.getDatabase(this);
    }

    Cursor cursor = null;
    String where = "name=?";
    String[] colunas = new String[]{"id", "description", "name", "favorite"};
    String argumentos[] = new String[]{character.getName()};
    cursor = db.query("character", colunas, where, argumentos, null, null, null);
    cursor.moveToFirst();

    while (cursor.moveToNext()) {
      characterList.add(montaDadoCharacter(cursor));
    }

    db.close();
    return characterList;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.detail_share, menu);

    ShareCompat.IntentBuilder intentBuilder = ShareCompat.IntentBuilder.from(this)
        .setText(character.getDescription())
        .setType("text/plain");

    ShareActionProvider actionProvider = (ShareActionProvider) MenuItemCompat
        .getActionProvider(menu.findItem(R.id.action_share));

    actionProvider.setShareIntent(intentBuilder.getIntent());

    MenuItem item = (MenuItem) menu.findItem(R.id.action_favorite);
    if (character.getFavorite() != null && character.getFavorite().intValue() == 1) {
      item.setIcon(R.drawable.ic_action_favorite_on);
    } else {
      item.setIcon(R.drawable.ic_action_favorite_off);
    }

    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_share) {
      share();
      return true;
    }

    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
    }

    if (item.getItemId() == R.id.action_favorite) {
      item.setIcon(R.drawable.ic_action_favorite_on);
      registerRemoveFavorite();
    }
    return super.onOptionsItemSelected(item);
  }

  private void registerRemoveFavorite() {
    List<Character> lstDados = carregarDados(character.getName());

    if (!db.isOpen()) {
      db = SQLiteHelper.getDatabase(this);
    }

    if (lstDados != null && !lstDados.isEmpty()) {
      Character characterDb = lstDados.get(0);
      if (characterDb != null) {
        db.delete(Constants.CHARACTER_TABLE, "name=?", new String[]{characterDb.getName()});
      }
    } else {
      ContentValues dados = new ContentValues();
      dados.put("name", character.getName());
      dados.put("description", character.getDescription());
      dados.put("favorite", 1);

      long insert = db.insert(Constants.CHARACTER_TABLE, null, dados);
    }
    db.close();
  }

  private Character montaDadoCharacter(Cursor cursor) {
    Character locCharacter = new Character();
    locCharacter.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
    locCharacter.setName(cursor.getString(cursor.getColumnIndex("name")));
    locCharacter.setDescription(cursor.getString(cursor.getColumnIndex("description")));
    locCharacter.setFavorite(cursor.getInt(cursor.getColumnIndex("favorite")));

    return locCharacter;
  }

  private void share() {
    Intent shareIntent = new Intent();
    shareIntent.setAction(Intent.ACTION_SEND);
    shareIntent.putExtra(Intent.EXTRA_TITLE, character.getName());
    shareIntent.putExtra(Intent.EXTRA_TEXT, character.getDescription());
    shareIntent.setType("text/plain");
    startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.title_share)));
  }

  private void shareWithProvider() {

  }
}
