package br.com.frameworksystem.marvelapp.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import br.com.frameworksystem.marvelapp.contantsenums.Constants;
import br.com.frameworksystem.marvelapp.model.Comic;
import br.com.frameworksystem.marvelapp.model.MarvelCollection;
import br.com.frameworksystem.marvelapp.model.MarvelResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rogerio.valente on 29/09/2016.
 */

public class ComicApi extends BaseApi {

  public ComicApi(Context context) {
    super(context);
  }

  public void comics(final int characterId, final OnComicsListener onComicsListener){
    String urlComics = Constants.API_CHARACTER_COMICS + characterId + "/comics";
    final Request request = new Request.Builder().url(urlComics).get().build();

    okHttpClient.newCall(request).enqueue(new Callback() {

      @Override
      public void onFailure(Call call, IOException e) {
        if (onComicsListener != null) {
          onComicsListener.onComics(0, null, 500);
        }
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        if (onComicsListener == null) {
          return;
        }
        if (response.isSuccessful()) {
          final Gson gson = new Gson();
          final MarvelResponse<MarvelCollection<Comic>> events = gson.fromJson(response.body().charStream(),
              new TypeToken<MarvelResponse<MarvelCollection<Comic>>>() {
              }.getType());

          onComicsListener.onComics(characterId, events.data.results, 0);
        } else {
          onComicsListener.onComics(0, null, response.code());
        }
      }
    });
  }

  public interface OnComicsListener {
     void onComics(int characterId, List<Comic> comics, int errorCode);
  }
}
