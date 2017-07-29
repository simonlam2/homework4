package com.example.nomis.newsapp.model;

/**
 * Created by Nomis on 6/29/2017.
 */

public class NewsItem {
    private String author;
    private String title;
    private String description;
    private String url;
    private String image;
    private String publishedAt;

    public NewsItem(String author, String title, String description, String url, String image,String publishedAt){
        this.author = author;
        this.title = title;
        this. description = description;
        this.url = url;
        this.image = image;
        this.publishedAt =publishedAt;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage(){return image;}
    public  void setImage(String image){this.image = image;}

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
