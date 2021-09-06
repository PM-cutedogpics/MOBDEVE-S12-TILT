package com.mobdeve.s12.tiltosurvive;

import java.util.ArrayList;

public class PowerUpsModel {
    private String title;
    private String description;
    private int imageId;
    private int activatedImageId;
    private int isSelected;
    private int owned;

    public PowerUpsModel(String title, String description, int imageId, int activatedImageId, int isSelected, int owned) {
        this.title = title;
        this.description = description;
        this.imageId = imageId;
        this.activatedImageId = activatedImageId;
        this.isSelected = isSelected;
        this.owned = owned;

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

    public int isSelected() {
        return isSelected;
    }

    public void setSelected(int selected) {
        isSelected = selected;
    }

    public Integer getOwned() {
        return owned;
    }

    public void setOwned(Integer owned) {
        this.owned = owned;
    }

    public int getSelected() { return this.isSelected;
    }
}
