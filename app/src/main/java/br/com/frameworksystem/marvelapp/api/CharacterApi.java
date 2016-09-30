package br.com.frameworksystem.marvelapp.api;

import android.content.Context;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import br.com.frameworksystem.marvelapp.contantsenums.Constants;
import br.com.frameworksystem.marvelapp.model.Character;
import br.com.frameworksystem.marvelapp.model.MarvelCollection;
import br.com.frameworksystem.marvelapp.model.MarvelResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rogerio.valente on 28/09/2016.
 */

public class CharacterApi extends BaseApi {

  public CharacterApi(Context context) {
    super(context);
  }

  public void characters(final OnCharactersListener onCharactersListener) {
    final Request request = new Request.Builder().url(Constants.API_CHARACTER).get().build();

    okHttpClient.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        if (onCharactersListener != null) {
          onCharactersListener.onCharacters(null, 500);
        }
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        if (onCharactersListener == null) {
          return;
        }

        if (response.isSuccessful()) {
          final Gson gson = new Gson();
          final MarvelResponse<MarvelCollection<Character>> characters = gson.fromJson(response.body().charStream(),
              new TypeToken<MarvelResponse<MarvelCollection<Character>>>() {
              }.getType());

          onCharactersListener.onCharacters(characters.data.results, 0);
        } else {
          onCharactersListener.onCharacters(null, response.code());
        }
      }
    });
  }

  public interface OnCharactersListener {
    void onCharacters(List<Character> characters, int errorCod);
  }
}

