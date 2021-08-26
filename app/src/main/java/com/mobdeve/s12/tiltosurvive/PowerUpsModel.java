package com.mobdeve.s12.tiltosurvive;

public class PowerUpsModel {
    private String title;
    private String description;
    private int imageId;
    private int activatedImageId;
    private boolean isSelected;

    public PowerUpsModel(String title, String description, int imageId, int activatedImageId, boolean isSelected) {
        this.title = title;
        this.description = description;
        this.imageId = imageId;
        this.activatedImageId = activatedImageId;
        this.isSelected = isSelected;

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

    public int getActivatedImageId() {
        return activatedImageId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
