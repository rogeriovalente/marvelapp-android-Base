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
import br.com.frameworksystem.marvelapp.adapters.CharacterAdapter;
import br.com.frameworksystem.marvelapp.adapters.EventAdapter;
import br.com.frameworksystem.marvelapp.api.CharacterApi;
import br.com.frameworksystem.marvelapp.model.Character;

/**
 * Created by rogerio.valente on 13/09/2016.
 */
public class CharacterFragment extends Fragment {
  RecyclerView recyclerView;
  CharacterAdapter characterAdapter;
  int menuOption;

  public CharacterFragment() {
    super();
  }

  public CharacterFragment(int caller) {
    menuOption = caller;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.event, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

    recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
    recyclerView.setLayoutManager(layoutManager);
    getCharacters();
    //characterAdapter = new CharacterAdapter(getActivity(), Mock.getCharacters(), recyclerView, menuOption);
    //recyclerView.setAdapter(characterAdapter);
  }

  private void getCharacters() {
    CharacterApi characterApi = new CharacterApi(getActivity());
    characterApi.characters(new CharacterApi.OnCharactersListener(){

      @Override
      public void onCharacters(final List<Character> characters, int errorCod) {
        getActivity().runOnUiThread(new Runnable(){

          @Override
          public void run() {
            if (characters != null){
              characterAdapter = new CharacterAdapter(getActivity(), characters, recyclerView, menuOption);
              recyclerView.setAdapter(characterAdapter);
            } else {
              Toast.makeText(getActivity(), R.string.msg_error_generic, Toast.LENGTH_SHORT).show();
            }
          }
        });
      }
    });

  }
}
