package com.mobdeve.s12.tiltosurvive;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Cow{
    private Bitmap cow;
    public int x, y, width, height;
    public double speed;

    public Cow(Resources res) {
        cow = BitmapFactory.decodeResource(res, R.drawable.cow);

        width = cow.getWidth();
        height = cow.getHeight();

        width /= 2;
        height /= 2;

        width = Constants.SCREEN_WIDTH / 40 * 2;
        height = Constants.SCREEN_HEIGHT / 60 * 3;
        Random random = new Random();
        this.speed = random.nextInt(10 - 5 + 1) + 5;

        this.x = (int) (Constants.SCREEN_WIDTH / 40 * (random.nextInt(28 - 4 + 1) + 4));
        this.y = (int) (Constants.SCREEN_HEIGHT / 60 * (random.nextInt(46 - 3 + 1) + 3));

        cow = Bitmap.createScaledBitmap(cow, width, height, false);
    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

    public Bitmap getFlight () {
        return cow;
    }


}
