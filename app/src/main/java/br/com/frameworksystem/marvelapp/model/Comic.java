package br.com.frameworksystem.marvelapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wgomes on 29/06/16.
 */

public class Comic implements Serializable {

  private String title;
  private String description;
  private String language;
  private String detailUrl;
  private String thumbnailUrl;
  private List<MarvelUrl> urls;
  private MarvelImage thumbnail;
  private List<ComicPrice> prices;

  public List<MarvelUrl> getUrls() {
    return urls;
  }

  public void setUrls(List<MarvelUrl> urls) {
    this.urls = urls;
  }

  public List<ComicPrice> getPrices() {
    return prices;
  }

  public void setPrices(List<ComicPrice> prices) {
    this.prices = prices;
  }

  public MarvelImage getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(MarvelImage thumbnail) {
    this.thumbnail = thumbnail;
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

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getDetailUrl() {
    return detailUrl;
  }

  public void setDetailUrl(String detailUrl) {
    this.detailUrl = detailUrl;
  }

  public String getThumbnailUrl() {
    return thumbnailUrl;
  }

  public void setThumbnailUrl(String thumbnailUrl) {
    this.thumbnailUrl = thumbnailUrl;
  }
}