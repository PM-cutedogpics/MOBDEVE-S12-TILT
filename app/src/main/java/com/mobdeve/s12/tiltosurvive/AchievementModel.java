package com.mobdeve.s12.tiltosurvive;

public class AchievementModel {
    private String title;
    private String description;
    private int achieved;

    public AchievementModel(String title, String description, int achieved) {
        this.title = title;
        this.description = description;
        this.achieved = achieved;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getAchieved() {
        return achieved;
    }

    public void setAchieved(int achieved) {
        this.achieved = achieved;
    }
}
