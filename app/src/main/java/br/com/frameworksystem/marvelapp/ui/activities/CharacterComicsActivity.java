package br.com.frameworksystem.marvelapp.ui.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.frameworksystem.marvelapp.Mock;
import br.com.frameworksystem.marvelapp.R;
import br.com.frameworksystem.marvelapp.fragments.ComicFragment;
import br.com.frameworksystem.marvelapp.model.Character;
import br.com.frameworksystem.marvelapp.model.Comic;

/**
 * Created by rogerio.valente on 19/09/2016.
 */
public class CharacterComicsActivity extends BaseActivity {

  private Character character;
  private Comic comic;
  private List<Comic> comicList;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.content_main);
    character = (Character) getIntent().getSerializableExtra("character");
    comicList = Mock.getCommics(character.getName());

    setTitle(character.getName() + "'s Comics");

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    ComicFragment comicFragment = new ComicFragment();
    comicFragment.setCharacter(character);
    ft.replace(R.id.content_main, comicFragment);
    ft.commit();
  }

  public List<Comic> getComicList() {
    return comicList;
  }
}
