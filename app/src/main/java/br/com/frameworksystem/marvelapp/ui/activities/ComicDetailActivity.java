package br.com.frameworksystem.marvelapp.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import br.com.frameworksystem.marvelapp.R;
import br.com.frameworksystem.marvelapp.model.Comic;
import br.com.frameworksystem.marvelapp.model.ComicPrice;
import br.com.frameworksystem.marvelapp.model.MarvelImage;

/**
 * Created by rogerio.valente on 20/09/2016.
 */

public class ComicDetailActivity extends BaseActivity {

  private Comic comic;

  @Override
  protected void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.comicdetail);

    comic = (Comic) getIntent().getSerializableExtra("comic");

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    setTitle(comic.getTitle());
    ImageView imageView = (ImageView) findViewById(R.id.comic_detail_thumb);
    Picasso.with(this).load(comic.getThumbnail().getImageUrl(MarvelImage.Size.DETAIL))
        .centerCrop().resize(560, 560).into(imageView);

    TextView txtViewDescription = (TextView) findViewById(R.id.comic_detail_description);
    txtViewDescription.setText(comic.getDescription());

    TextView txtViewPrice = (TextView) findViewById(R.id.comic_detail_price);
    ComicPrice comicPrice = comic.getPrices().get(0);
    txtViewPrice.setText(comicPrice.price);

    TextView lstViewLangs = (TextView) findViewById(R.id.comic_detail_languages);
    lstViewLangs.setText(comic.getLanguage());

    Button btnToast = (Button) findViewById(R.id.btn_toast);
    btnToast.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(final View v) {
        Toast.makeText(v.getContext(), "Toast funcionando Wagner!!! =)",
            Toast.LENGTH_LONG).show();
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.detail_share, menu);

    Intent shareIntent = share();
    ShareActionProvider actionProvider = (ShareActionProvider) MenuItemCompat
        .getActionProvider(menu.findItem(R.id.action_share));

    actionProvider.setShareIntent(shareIntent);
    return super.onCreateOptionsMenu(menu);

  }

  @Override
  public final boolean onOptionsItemSelected(final MenuItem item) {
    if (item.getItemId() == R.id.action_share) {
      share();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private Intent share() {
    Uri uri = Uri.parse(comic.getUrls().get(0).url);
    Intent shareIntent = new Intent();
    shareIntent.setAction(Intent.ACTION_VIEW);
    shareIntent.setData(uri);
    return shareIntent;
    //startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.title_share)));
  }

}
