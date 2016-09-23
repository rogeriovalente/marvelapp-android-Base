package br.com.frameworksystem.marvelapp.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import br.com.frameworksystem.marvelapp.R;

/**
 * Created by rogerio.valente on 22/09/2016.
 */
public class CustomPagerAdapter extends PagerAdapter {

  Context context;

  private Integer[] Imgid = {R.raw.ironman, R.raw.captainamerica, R.raw.characters, R.raw.spiderman};

  @Override
  public int getCount() {
    return Imgid.length;
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {

    ImageView imgView = new ImageView(container.getContext());
    imgView.setImageResource(Imgid[position]);
    ((ViewPager) container).addView(imgView,0);
    return imgView;
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == ((View) object);
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    ((ViewPager) container).removeView((View)object);
  }
}
