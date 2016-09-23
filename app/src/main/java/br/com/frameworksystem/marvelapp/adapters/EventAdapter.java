package br.com.frameworksystem.marvelapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.frameworksystem.marvelapp.R;
import br.com.frameworksystem.marvelapp.model.Event;
import br.com.frameworksystem.marvelapp.ui.activities.EventDetailActivity;

/**
 * Created by rogerio.valente on 13/09/2016.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

  private Context context;
  private List<Event> events;
  private RecyclerView recyclerView;
  private Boolean isTablet;
  private WebView webView;

  public EventAdapter(Context parContext, List<Event> parEventList, RecyclerView parRecyclerView, Boolean parIsTablet, WebView parWebView) {
    context = parContext;
    events = parEventList;
    recyclerView = parRecyclerView;
    isTablet = parIsTablet;
    webView = parWebView;
  }

  @Override
  public void onBindViewHolder(EventAdapter.ViewHolder holder, int position) {
    Event event = events.get(position);
    holder.eventTitle.setText(event.getTitle());
    Picasso.with(context).load(event.getImgUrl()).centerCrop().resize(400, 400).into(holder.eventImg);
  }

  @Override
  public EventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.eventitem, parent, false);
    EventAdapter.ViewHolder viewHolder = new EventAdapter.ViewHolder(view);
    return viewHolder;
  }

  @Override
  public int getItemCount() {
    return events.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    ImageView eventImg;
    TextView eventTitle;

    public ViewHolder(View itemView) {
      super(itemView);

      eventImg = (ImageView) itemView.findViewById(R.id.event_img);
      eventTitle = (TextView) itemView.findViewById(R.id.event_title);
      itemView.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        int position = recyclerView.getChildAdapterPosition(view);
        Event event = events.get(position);

        if (view.getResources().getBoolean(R.bool.isTablet)) {
          webView.loadUrl(event.getUrl());
        } else {
          Intent intent = new Intent(context, EventDetailActivity.class);
          intent.putExtra("event", event);
          context.startActivity(intent);
        }

      }
    };
  }
}
