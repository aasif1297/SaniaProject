package com.example.rubab.slider.models;

public class CategoriesModel {

    private String ImageUrl;
    private String title;
    private  String description;
    private String price;



    public CategoriesModel(String imageUrl, String title, String description, String price) {
        ImageUrl = imageUrl;
        this.title = title;
        this.description = description;
        this.price = price;
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
        this. description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
