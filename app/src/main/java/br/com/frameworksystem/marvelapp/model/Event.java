package br.com.frameworksystem.marvelapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wgomes on 17/06/16.
 */

public class Event implements Serializable {

  private String id;
  private String title;
  private String description;
  private String resourceUri;
  private List<MarvelUrl> urls;
  private MarvelImage thumbnail;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<MarvelUrl> getUrls() {
    return urls;
  }

  public void setUrls(List<MarvelUrl> urls) {
    this.urls = urls;
  }

  public String getResourceUri() {
    return resourceUri;
  }

  public void setResourceUri(String resourceUri) {
    this.resourceUri = resourceUri;
  }

  public MarvelImage getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(MarvelImage thumbnail) {
    this.thumbnail = thumbnail;
  }
}
