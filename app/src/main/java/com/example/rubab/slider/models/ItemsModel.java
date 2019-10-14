package com.example.rubab.slider.models;

public class ItemsModel {
    private String id;
    private String catid;
    private String ImageUrl;
    private String title;
    private  String description;
    private String price;

    public ItemsModel(String id, String catid, String imageUrl, String title, String description, String price) {
        this.id = id;
        this.catid = catid;
        ImageUrl = imageUrl;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public ItemsModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
