package com.mobdeve.s12.tiltosurvive;

public class PowerUpsModel {
    private String title;
    private String description;
    private int imageId;

    public PowerUpsModel(String title, String description, int imageId) {
        this.title = title;
        this.description = description;
        this.imageId = imageId;

    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImageId() {
        return imageId;
    }
}
