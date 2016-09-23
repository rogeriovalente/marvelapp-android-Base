package br.com.frameworksystem.marvelapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import br.com.frameworksystem.marvelapp.Mock;
import br.com.frameworksystem.marvelapp.R;
import br.com.frameworksystem.marvelapp.adapters.EventAdapter;

/**
 * Created by rogerio.valente on 13/09/2016.
 */
public class EventFragment extends Fragment {

  private RecyclerView recyclerView;
  private EventAdapter eventAdapter;
  private Boolean isTablet;
  private WebView webView;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    isTablet = getResources().getBoolean(R.bool.isTablet);
    if (isTablet){
      return inflater.inflate(R.layout.event_tablet, container, false);
    } else {
      return inflater.inflate(R.layout.event, container, false);
    }
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

    recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
    recyclerView.setLayoutManager(layoutManager);

    if (isTablet){
      webView = (WebView) view.findViewById(R.id.webView_eventdetail_tablet);
    }
    eventAdapter = new EventAdapter(getActivity(), Mock.getEvents(), recyclerView, isTablet, webView);
    recyclerView.setAdapter(eventAdapter);


  }
}
