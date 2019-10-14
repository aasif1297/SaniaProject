package com.example.rubab.slider.models;

public class CategoriesModel {

    private String ImageUrl;
    private String title;
    private String id;

    public CategoriesModel(String imageUrl, String title, String id) {
        ImageUrl = imageUrl;
        this.title = title;
        this.id = id;
    }

    public CategoriesModel() {
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
