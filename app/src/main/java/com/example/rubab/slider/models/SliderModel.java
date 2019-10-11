package com.example.rubab.slider.models;

public class SliderModel {
    private String ImageUrl;
    private String title;

    public SliderModel(String imageUrl, String title) {
        ImageUrl = imageUrl;
        this.title = title;
    }

    public SliderModel() {
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
}
