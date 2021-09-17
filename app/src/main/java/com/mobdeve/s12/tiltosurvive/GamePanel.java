package com.mobdeve.s12.tiltosurvive;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    private final ImageButton ibPause;
    private Chronometer timer;
    public MainThread thread;
    private Rect r = new Rect();
    private boolean paused;

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

    public GamePanel(Context context, Chronometer timer, TextView tvGameOver, ImageButton ibPause) {
        super(context);

        getHolder().addCallback(this);

        Constants.CURRENT_CONTEXT = context;
        this.timer = timer;
        this.tvGameOver = tvGameOver;
        this.ibPause = ibPause;
        this.thread = new MainThread(getHolder(), this);
        this.spaceship = new Spaceship(getResources());
        this.obstacleManager = new ObstacleManager(Color.parseColor("#673AB7"));

        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();

        setFocusable(true);
        cows = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
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
        while (retry) {
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
            if (!paused)
                if (orientationData.getOrientation() != null && orientationData.getStartOrientation() != null) {
                    float pitch = orientationData.getOrientation()[1] - orientationData.getStartOrientation()[1];
                    float roll = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];


                    float xSpeed = 2 * roll * Constants.SCREEN_WIDTH / spaceship.speed;
                    float ySpeed = pitch * Constants.SCREEN_HEIGHT / spaceship.speed;

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

                    if (spaceship.x < Constants.SCREEN_WIDTH / 40 * 3)
                        spaceship.x = Constants.SCREEN_WIDTH / 40 * 3;
                    else if (spaceship.x > Constants.SCREEN_WIDTH / 40 * 32)
                        spaceship.x = Constants.SCREEN_WIDTH / 40 * 32;
                    if (spaceship.y < Constants.SCREEN_HEIGHT / 60 * 3)
                        spaceship.y = Constants.SCREEN_HEIGHT / 60 * 3;
                    else if (spaceship.y > Constants.SCREEN_HEIGHT / 60 * 47)
                        spaceship.y = Constants.SCREEN_HEIGHT / 60 * 47;
                }


            if(counter > 45)
                if (!paused)
                    if (counter % 4 == 0){
                        for (int i = 0; i < cows.size(); i++) {
                            cows.get(i).x += (int) cows.get(i).speed * (spaceship.x - cows.get(i).x) / 100;
                            cows.get(i).y += (int) cows.get(i).speed * (spaceship.y - cows.get(i).y) / 100;
                            if (cows.get(i).x < Constants.SCREEN_WIDTH / 40 * 3)
                                cows.get(i).x = Constants.SCREEN_WIDTH / 40 * 3;
                            else if (cows.get(i).x > Constants.SCREEN_WIDTH / 40 * 32)
                                cows.get(i).x = Constants.SCREEN_WIDTH / 40 * 32;
                            if (cows.get(i).y < Constants.SCREEN_HEIGHT / 60 * 3)
                                cows.get(i).y = Constants.SCREEN_HEIGHT / 60 * 3;
                            else if (cows.get(i).y > Constants.SCREEN_HEIGHT / 60 * 47)
                                cows.get(i).y = Constants.SCREEN_HEIGHT / 60 * 47;

                            if(Rect.intersects(spaceship.getCollisionShape(), cows.get(i).getCollisionShape())) {
                                if (this.spaceship.shielded) {
                                    spaceship.shielded = false;
                                    cows.remove(i);
                                    spaceship.setSpaceship(BitmapFactory.decodeResource(getResources(), R.drawable.spaceship), "no shield");
                                } else {
                                    gameOver = true;
                                    timer.stop();
                                    ((Activity) getContext()).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            tvGameOver.setVisibility(VISIBLE);
                                        }
                                    });
                                    this.removeCallbacks(thread);
                                }
                            }
                        }
                    }
                }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        paint = new Paint();
        if (!gameOver){
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.space_bg_post_game), 0, 0, null);
            canvas.drawBitmap(spaceship.getSpaceship(), spaceship.x, spaceship.y, paint);
            for(int i = 0; i < cows.size(); i++)
                canvas.drawBitmap(cows.get(i).getFlight(), cows.get(i).x, cows.get(i).y, paint);
            this.obstacleManager.draw(canvas);
        }
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

    public void setPause(boolean pause) {
        this.paused = pause;
    }

    public void applyPowerup(String powerupName) {
        System.out.println(powerupName);
        if(powerupName.equals("Force Field")) {
            spaceship.setSpaceship(BitmapFactory.decodeResource(getResources(), R.drawable.spaceship_shield), "shield");
            spaceship.shielded = true; // Add boolean to avoid death
            System.out.println("SHIELD UP");
            new CountDownTimer(10000, 1000) {
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    spaceship.setSpaceship(BitmapFactory.decodeResource(getResources(), R.drawable.spaceship), "no shield");
                    spaceship.shielded = false;
                    System.out.println("SHIELD DOWN");
                }
            }.start();
        } else if(powerupName.equals("Haste")){
            spaceship.speed = 5000f;
            System.out.println("HASTE IN");
            new CountDownTimer(10000, 1000) {
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    spaceship.speed = 3000f;
                    System.out.println("HASTE OUT");
                }
            }.start();
        } else if(powerupName.equals("Speed Down")){
            for(int i = 0; i < cows.size(); i++) {
                cows.get(i).speed -= 4;
            }
            new CountDownTimer(10000, 1000) {
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    for(int i = 0; i < cows.size(); i++) {
                        cows.get(i).speed += 4;
                    }
                }
            }.start();
        }
    }
}
