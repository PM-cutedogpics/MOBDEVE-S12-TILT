package com.mobdeve.s12.tiltosurvive;

import android.graphics.Canvas;

import java.util.ArrayList;

public class ObstacleManager {
    //higher index = lower on screen = higher y value
    private ArrayList<Obstacle> obstacles;
    private int color;

    private long startTime;
    private long initTime;

    public ObstacleManager(int color) {
        this.color = color;

        startTime = initTime = System.currentTimeMillis();

        this.obstacles = new ArrayList<Obstacle>();

        populateObstacles();
    }

    private void populateObstacles() {
        //create the walls on the four sides
        //Top
        obstacles.add(new Obstacle(color, Constants.SCREEN_WIDTH / 40 * 2,
                Constants.SCREEN_WIDTH / 40 * 35, Constants.SCREEN_HEIGHT / 60 * 2, Constants.SCREEN_HEIGHT / 60 * 3));
        //Bottom
        obstacles.add(new Obstacle(color, Constants.SCREEN_WIDTH / 40 * 2,
                Constants.SCREEN_WIDTH / 40 * 35, Constants.SCREEN_HEIGHT / 60 * 51, Constants.SCREEN_HEIGHT / 60 * 52));
        //Left
        obstacles.add(new Obstacle(color, Constants.SCREEN_WIDTH / 40 * 2,
                Constants.SCREEN_WIDTH / 40 * 3, Constants.SCREEN_HEIGHT / 60 * 3, Constants.SCREEN_HEIGHT / 60 * 52));
        //Right
        obstacles.add(new Obstacle(color, Constants.SCREEN_WIDTH / 40 * 34,
                Constants.SCREEN_WIDTH / 40 * 35, Constants.SCREEN_HEIGHT / 60 * 3, Constants.SCREEN_HEIGHT / 60 * 52));
    }

    public void draw(Canvas canvas) {
        for(Obstacle ob : obstacles) {
            ob.draw(canvas);
        }
    }
}
