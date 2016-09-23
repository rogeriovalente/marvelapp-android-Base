package br.com.frameworksystem.marvelapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.frameworksystem.marvelapp.Mock;
import br.com.frameworksystem.marvelapp.R;
import br.com.frameworksystem.marvelapp.fragments.ComicFragment;
import br.com.frameworksystem.marvelapp.model.Character;
import br.com.frameworksystem.marvelapp.model.Comic;
import br.com.frameworksystem.marvelapp.ui.activities.BaseActivity;
import br.com.frameworksystem.marvelapp.ui.activities.CharacterComicsActivity;
import br.com.frameworksystem.marvelapp.ui.activities.CharacterDetailActivity;

/**
 * Created by rogerio.valente on 13/09/2016.
 */
public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    private Context context;
    private List<Character> characters;
    private RecyclerView recyclerView;
    private int menuOption;

    public CharacterAdapter(Context parContext, List<Character> parCharacterList,RecyclerView parRecycler, int parMenuOption){
        context = parContext;
        characters = parCharacterList;
        recyclerView = parRecycler;
        menuOption = parMenuOption;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.characteritem, parent, false);
        CharacterAdapter.ViewHolder viewHolder = new CharacterAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CharacterAdapter.ViewHolder holder, int position) {
        Character character = characters.get(position);
        holder.description.setText(character.getDescription());
        holder.name.setText(character.getName());
        Picasso.with(context).load(character.getThumbnailUrl()).centerCrop().resize(400,400).into(holder.thumb);
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView thumb;
        TextView name;
        TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            thumb = (ImageView) itemView.findViewById(R.id.character_thumb);
            name = (TextView) itemView.findViewById(R.id.character_name);
            description = (TextView) itemView.findViewById(R.id.character_description);
            itemView.setOnClickListener(onClickListener);
        }

        View.OnClickListener onClickListener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int position = recyclerView.getChildAdapterPosition(view);
                Character character = characters.get(position);

                Intent intent = new Intent(context, menuOption == 0 ? CharacterDetailActivity.class :
                        CharacterComicsActivity.class);
                intent.putExtra("character", character);
                context.startActivity(intent);
            }
        };

    }
}
