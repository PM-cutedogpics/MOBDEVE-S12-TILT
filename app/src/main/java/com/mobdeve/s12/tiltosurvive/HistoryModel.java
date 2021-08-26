package com.mobdeve.s12.tiltosurvive;

import java.util.ArrayList;

public class HistoryModel {

    private String time;
    private String date;
    private int score;

    public HistoryModel(String time, String date, int score) {
        this.time = time;
        this.date = date;
        this.score = score;
    }

    public String getTime() { return time; }

    public String getDate() { return date; }

    public int getScore() { return score; }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
