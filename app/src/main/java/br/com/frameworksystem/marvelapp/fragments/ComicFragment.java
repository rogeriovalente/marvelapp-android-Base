package br.com.frameworksystem.marvelapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import br.com.frameworksystem.marvelapp.Mock;
import br.com.frameworksystem.marvelapp.R;
import br.com.frameworksystem.marvelapp.adapters.ComicAdapter;
import br.com.frameworksystem.marvelapp.api.ComicApi;
import br.com.frameworksystem.marvelapp.model.Character;
import br.com.frameworksystem.marvelapp.model.Comic;
import br.com.frameworksystem.marvelapp.ui.activities.CharacterComicsActivity;

/**
 * Created by rogerio.valente on 19/09/2016.
 */
public class ComicFragment extends Fragment {

  RecyclerView recyclerView;
  ComicAdapter comicAdapter;
  Character character;

  public Character getCharacter() {
    return character;
  }

  public void setCharacter(Character character) {
    this.character = character;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.event, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

    recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
    recyclerView.setLayoutManager(layoutManager);

    getComics(getCharacter().getId());
    /*comicAdapter = new ComicAdapter(getActivity(), Mock.getCommics(getCharacter().getName()), recyclerView);
    recyclerView.setAdapter(comicAdapter);*/
  }

  private void getComics(String id) {
    int characterId = Integer.valueOf(id);
    ComicApi comicApi = new ComicApi(getActivity());
    comicApi.comics(characterId, new ComicApi.OnComicsListener() {
      @Override
      public void onComics(int characterId, final List<Comic> comics, int errorCode) {
        getActivity().runOnUiThread(new Runnable(){

          @Override
          public void run() {
            if (comics != null){
              comicAdapter = new ComicAdapter(getActivity(), comics, recyclerView);
              recyclerView.setAdapter(comicAdapter);
            } else {
              Toast.makeText(getActivity(),  R.string.msg_error_generic, Toast.LENGTH_SHORT).show();
            }
          }
        });
      }
    });
  }
}
