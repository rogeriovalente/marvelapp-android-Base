package br.com.frameworksystem.marvelapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.frameworksystem.marvelapp.R;
import br.com.frameworksystem.marvelapp.model.Character;
import br.com.frameworksystem.marvelapp.model.Comic;
import br.com.frameworksystem.marvelapp.model.MarvelImage;
import br.com.frameworksystem.marvelapp.ui.activities.CharacterComicsActivity;
import br.com.frameworksystem.marvelapp.ui.activities.CharacterDetailActivity;
import br.com.frameworksystem.marvelapp.ui.activities.ComicDetailActivity;

/**
 * Created by rogerio.valente on 19/09/2016.
 */
public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ViewHolder> {

  private Context context;
  private List<Comic> comics;
  private RecyclerView recyclerView;

  public ComicAdapter(Context parContext, List<Comic> parComicList, RecyclerView parRecycler) {
    context = parContext;
    comics = parComicList;
    recyclerView = parRecycler;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.character_comics, parent, false);
    ComicAdapter.ViewHolder viewHolder = new ComicAdapter.ViewHolder(view);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    Comic comic = comics.get(position);
    holder.title.setText(comic.getTitle());

    Picasso.with(context).load(comic.getThumbnail().getImageUrl(MarvelImage.Size.DETAIL))
        .centerCrop().resize(250, 250).into(holder.thumb);
  }

  @Override
  public int getItemCount() {
    return comics.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    ImageView thumb;
    TextView title;

    public ViewHolder(View itemView) {
      super(itemView);

      thumb = (ImageView) itemView.findViewById(R.id.comic_thumb);
      title = (TextView) itemView.findViewById(R.id.comic_title);
      itemView.setOnClickListener(onClickListener);
    }
  }

  View.OnClickListener onClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      int position = recyclerView.getChildAdapterPosition(view);
      Comic comic = comics.get(position);

      Intent intent = new Intent(context, ComicDetailActivity.class);
      intent.putExtra("comic", comic);
      context.startActivity(intent);
    }
  };
}