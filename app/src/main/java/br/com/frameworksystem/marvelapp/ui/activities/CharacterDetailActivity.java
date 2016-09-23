package br.com.frameworksystem.marvelapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.frameworksystem.marvelapp.R;
import br.com.frameworksystem.marvelapp.model.Character;

/**
 * Created by rogerio.valente on 14/09/2016.
 */
public class CharacterDetailActivity extends BaseActivity {

    private Character character;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.characterdetail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        character = (Character) getIntent().getSerializableExtra("character");
        setTitle(character.getName());

        ImageView imageView = (ImageView) findViewById(R.id.character_detail_thumb);
        Picasso.with(this).load(character.getThumbnailUrl()).centerCrop().resize(600,600).into(imageView);
        TextView textView = (TextView) findViewById(R.id.character_detail_description);
        textView.setText(character.getDescription());

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

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            share();
            return true;
        }

        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
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
