package com.mobdeve.s12.tiltosurvive;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.ArrayList;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    private Chronometer timer;
    private MainThread thread;
    private Rect r = new Rect();

//    private RectPlayer player;
    private Spaceship spaceship;
    private ArrayList<Cow> cows;
//    private Point playerPoint;
    private ObstacleManager obstacleManager;
    private TextView tvGameOver;

    private Paint paint;

    private boolean movingPlayer = false;

    private boolean gameOver = false;
    private long gameOverTime;

    private OrientationData orientationData;
    private long frameTime;
    private double counter;

    public GamePanel(Context context, Chronometer timer, TextView tvGameOver) {
        super(context);

        getHolder().addCallback(this);

        Constants.CURRENT_CONTEXT = context;
        this.timer = timer;
        this.tvGameOver = tvGameOver;
        this.thread = new MainThread(getHolder(), this);
        this.spaceship = new Spaceship(getResources());
        this.obstacleManager = new ObstacleManager(Color.parseColor("#673AB7"));

        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();

        setFocusable(true);
        cows = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            Cow cow = new Cow(getResources());
            cows.add(cow);
        }
    }

    public void reset() {
        this.obstacleManager = new ObstacleManager(Color.GRAY);
        movingPlayer = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(holder, this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (true) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update() {
        if(!gameOver) {
            counter++;
            if (frameTime < Constants.INIT_TIME)
                frameTime = Constants.INIT_TIME;
            int elapsedTime = (int) (System.currentTimeMillis() - frameTime);
            frameTime = System.currentTimeMillis();
            System.out.println(elapsedTime);
            if (orientationData.getOrientation() != null && orientationData.getStartOrientation() != null) {
                float pitch = orientationData.getOrientation()[1] - orientationData.getStartOrientation()[1];
                float roll = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];

                float xSpeed = 2 * roll * Constants.SCREEN_WIDTH / 3000f;
                float ySpeed = pitch * Constants.SCREEN_HEIGHT / 3000f;

//                playerPoint.x += Math.abs(xSpeed * elapsedTime) > 5 ? xSpeed * elapsedTime : 0;
//                playerPoint.y -= Math.abs(ySpeed * elapsedTime) > 5 ? ySpeed * elapsedTime : 0;
//                Log.d("X:", String.valueOf(xSpeed));
//                Log.d("Y:", String.valueOf(ySpeed));
//
//                int angle = (int) Math.toDegrees(Math.atan(ySpeed/xSpeed));
//
//                if (angle >= 0)
//                    this.spaceship.RotateBitmap(angle);
//                else
//                    this.spaceship.RotateBitmap(360 + angle);
//                if(angle >= -45 && angle <= 45) {
//                    this.spaceship.RotateBitmap(0);
//                } else if(angle <= 135 && angle > 45) {
//                    this.spaceship.RotateBitmap(90);
//                } else if(angle <= 215 && angle > 135) {
//                    this.spaceship.RotateBitmap(180);
//                } else if(angle < -45 && angle > -135) {
//                    this.spaceship.RotateBitmap(270);
//                }
                spaceship.x += Math.abs(xSpeed * elapsedTime) > 5 ? xSpeed * elapsedTime : 0;
                spaceship.y -= Math.abs(ySpeed * elapsedTime) > 5 ? ySpeed * elapsedTime : 0;
            }

            if (spaceship.x < Constants.SCREEN_WIDTH / 40 * 2)
                spaceship.x = Constants.SCREEN_WIDTH / 40 * 2;
            else if (spaceship.x > Constants.SCREEN_WIDTH / 40 * 29)
                spaceship.x = Constants.SCREEN_WIDTH / 40 * 29;
            if (spaceship.y < Constants.SCREEN_HEIGHT / 60 * 10)
                spaceship.y = Constants.SCREEN_HEIGHT / 60 * 10;
            else if (spaceship.y > Constants.SCREEN_HEIGHT / 60 * 54)
                spaceship.y = Constants.SCREEN_HEIGHT / 60 * 54;
            if(counter > 40)
                if (frameTime % 3 == 0){
                    for (int i = 0; i < cows.size(); i++) {
                        cows.get(i).x += (int) cows.get(i).speed * (spaceship.x - cows.get(i).x) / 100;
                        cows.get(i).y += (int) cows.get(i).speed * (spaceship.y - cows.get(i).y) / 100;
                        if (cows.get(i).x < Constants.SCREEN_WIDTH / 40 * 2)
                            cows.get(i).x = Constants.SCREEN_WIDTH / 40 * 2;
                        else if (cows.get(i).x > Constants.SCREEN_WIDTH / 40 * 29)
                            cows.get(i).x = Constants.SCREEN_WIDTH / 40 * 29;
                        if (cows.get(i).y < Constants.SCREEN_HEIGHT / 60 * 6)
                            cows.get(i).y = Constants.SCREEN_HEIGHT / 60 * 6;
                        else if (cows.get(i).y > Constants.SCREEN_HEIGHT / 60 * 50)
                            cows.get(i).y = Constants.SCREEN_HEIGHT / 60 * 50;

                        if(Rect.intersects(spaceship.getCollisionShape(), cows.get(i).getCollisionShape())) {
                            gameOver = true;
                            timer.stop();
                            this.tvGameOver.setVisibility(VISIBLE);
                        }
                    }
                }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        paint = new Paint();
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.space_bg_post_game), 0, 0, null);
        canvas.drawBitmap(spaceship.getSpaceship(), spaceship.x, spaceship.y, paint);
        for(int i = 0; i < cows.size(); i++)
            canvas.drawBitmap(cows.get(i).getFlight(), cows.get(i).x, cows.get(i).y, paint);
        this.obstacleManager.draw(canvas);

//        if(gameOver) {
//            Paint paint = new Paint();
//            paint.setTextSize(100);
//            paint.setColor(Color.MAGENTA);
//            drawCenterText(canvas, paint, "Game Over");
//            getHolder().unlockCanvasAndPost(canvas);
//        }
    }
//
//    private void drawCenterText(Canvas canvas, Paint paint, String text) {
//        paint.setTextAlign(Paint.Align.LEFT);
//        canvas.getClipBounds(r);
//        int cHeight = r.height();
//        int cWidth = r.width();
//        paint.getTextBounds(text, 0, text.length(), r);
//        float x = cWidth / 2f - r.width() / 2f - r.left;
//        float y = cHeight / 2f + r.height() / 2f - r.bottom;
//        canvas.drawText(text, x, y, paint);
//    }


}
