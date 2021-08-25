package com.mobdeve.s12.tiltosurvive;

public class AchievementModel {
    private String title;
    private String description;

    public AchievementModel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
