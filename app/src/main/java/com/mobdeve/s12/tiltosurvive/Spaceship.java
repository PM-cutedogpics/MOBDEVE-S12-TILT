package com.mobdeve.s12.tiltosurvive;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;

public class Spaceship {
    public int x, y, width, height;
    private Bitmap spaceship;
    private float lastDirection;
    public float speed;
    public boolean shielded;

    public Spaceship (Resources res) {
        spaceship = BitmapFactory.decodeResource(res, R.drawable.spaceship);

        width = spaceship.getWidth();
        height = spaceship.getHeight();

        width /= 2;
        height /= 2;

        this.lastDirection = 90;
        this.speed = 3000f;
        this.shielded = false;
        width = Constants.SCREEN_WIDTH / 40 * 2;
        height = Constants.SCREEN_HEIGHT / 60 * 4;

        spaceship = Bitmap.createScaledBitmap(spaceship, width, height, false);

        this.y = (int) (Constants.SCREEN_HEIGHT / 60 * 3 + Constants.SCREEN_HEIGHT / 60 * 51) / 2;
        this.x = (int) (Constants.SCREEN_WIDTH / 40 * 2 + Constants.SCREEN_WIDTH / 40 * 35) / 2;
    }

    public Bitmap getSpaceship() {
        return spaceship;
    }

    public void setSpaceship(Bitmap bitmap, String type) {
        this.spaceship = bitmap;
        if(type.equals("no shield")) {
            this.width = Constants.SCREEN_WIDTH / 40 * 2;
            this.height = Constants.SCREEN_HEIGHT / 60 * 4;
        } else {
            this.width = Constants.SCREEN_WIDTH / 40 * 2;
            this.height = Constants.SCREEN_HEIGHT / 60 * 5;
        }
        this.spaceship = Bitmap.createScaledBitmap(this.spaceship, this.width, this.height, false);
    }

    public Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

    public void RotateBitmap(int newAngle)
    {
        int increment = 0;
        if (this.lastDirection == 0) {
            if (newAngle > 315 || newAngle <= 45) //0
                increment = 0;
            else if (newAngle > 45 && newAngle <= 135) //90
                increment = 90;
            else if (newAngle > 135 && newAngle <= 225) //180
                increment = 180;
            else if (newAngle > 225 && newAngle <= 315) // 270
                increment = 270;
        } else if (this.lastDirection == 90) {
            if (newAngle > 45 && newAngle <= 135) //90
                increment = 0;
            else if (newAngle > 135 && newAngle <= 225) //180
                increment = 90;
            else if (newAngle > 225 && newAngle <= 315) //270
                increment = 180;
            else if (newAngle > 315 || newAngle <= 45) //0
                increment = 270;
        } else if (this.lastDirection == 180) {
            if (newAngle > 45 && newAngle <= 135) //90
                increment = 270;
            else if (newAngle > 135 && newAngle <= 225) //180
                increment = 0;
            else if (newAngle > 225 && newAngle <= 315) //270
                increment = 90;
            else if (newAngle > 315 || newAngle <= 45) // 360 or 0
                increment = 180;
        } else if (this.lastDirection == 270) {
            if (newAngle > 45 && newAngle <= 135) //90
                increment = 180;
            else if (newAngle > 135 && newAngle <= 225) //180
                increment = 270;
            else if (newAngle > 225 && newAngle <= 315) //270
                increment = 0;
            else if (newAngle > 315 || newAngle <= 45) // 360 or 0
                increment = 90;
        }
//
//        Log.d("newAngle", String.valueOf(newAngle));
//        Log.d("last direction", String.valueOf(lastDirection));
//        Log.d("increment", String.valueOf(increment));

        this.lastDirection = this.lastDirection + increment;
        if(lastDirection == 360)
             this.lastDirection = 0;
        else if(lastDirection == 450)
             this.lastDirection = 90;
        else if(lastDirection == 540)
            this.lastDirection = 180;
        else if(lastDirection == 630)
            this.lastDirection = 270;

        Matrix matrix = new Matrix();
        matrix.postRotate(increment);
        this.spaceship = Bitmap.createBitmap(this.spaceship, 0, 0, this.spaceship.getWidth(), this.spaceship.getHeight(), matrix, true);
    }
}
