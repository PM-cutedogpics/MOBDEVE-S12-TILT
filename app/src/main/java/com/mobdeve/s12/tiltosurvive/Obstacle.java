package com.mobdeve.s12.tiltosurvive;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Obstacle implements GameObject {
    private Rect rectangle;
    private int color;

    public Rect getRectangle() {
         return this.rectangle;
    }

    public Obstacle(int color, int startX, int endX, int startY, int endY) {
        this.color = color;
        //left, top, right, bottom
        this.rectangle = new Rect(startX, startY, endX, endY);
    }
    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(this.color);
        canvas.drawRect(this.rectangle, paint);
    }

    @Override
    public void update() {

    }
}
