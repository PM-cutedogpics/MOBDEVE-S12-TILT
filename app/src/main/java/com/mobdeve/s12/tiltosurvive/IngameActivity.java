package com.mobdeve.s12.tiltosurvive;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import javax.security.auth.callback.Callback;

public class IngameActivity extends AppCompatActivity {
    private ImageButton ibtnPause;
    public Chronometer timer;
    private ImageButton ibtnClick;
    private ImageButton ibtnEnd;
    private TextView tvScore;
    private int fun;
    private MediaPlayer ingame;
    private ImageButton ibtnFirstPowerup;
    private ImageButton ibtnSecondPowerup;
    private ImageButton ibtnThirdPowerup;
    private GamePanel gamePanel;
    private TextView tvGameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingame);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        this.ibtnPause = findViewById(R.id.btn_pause);
        this.timer = findViewById(R.id.chr_time);
        this.tvScore = findViewById(R.id.tv_ingame_score);
        this.tvGameOver = findViewById(R.id.tv_game_over);
        this.tvGameOver.setOnClickListener(v -> this.gameOver());
        this.gamePanel = new GamePanel(this, this.timer, this.tvGameOver);
        SurfaceView surface = (SurfaceView) findViewById(R.id.sv_game);
        surface.getHolder().addCallback(gamePanel);

        MainActivity.music.pause();

        this.ingame = MediaPlayer.create(getApplicationContext(), R.raw.ingamemusic);
        this.ingame.setLooping(true);
        this.ingame.start();

        timer.start();

        this.loadData();
//        this.ibtnEnd.setOnClickListener(v -> {
//            timer.stop();
//            this.ingame.stop();
//            this.ingame.release();
//            Intent intent = new Intent(IngameActivity.this, PostGameActivity.class);
//            intent.putExtra(Keys.KEY_TV_TIME.name(), timer.getText());
//            intent.putExtra(Keys.KEY_TV_SCORE.name(), tvScore.getText());
//            startActivity(intent);
//            finish();
//        });

//        this.fun = 0;
//        this.ibtnClick.setOnClickListener(v -> {
//            this.fun += 1;
//            this.tvScore.setText(String.valueOf(this.fun));
//        });
    }

    private void loadData(){
        Intent intent = getIntent();
        int powerupSize = Integer.valueOf(intent.getStringExtra(Keys.KEYS_INGAME_SIZE.name()));

        this.ibtnFirstPowerup = findViewById(R.id.ib_first_powerup);
        this.ibtnSecondPowerup = findViewById(R.id.ib_second_powerup);
        this.ibtnThirdPowerup = findViewById(R.id.ib_third_powerup);

        for (int i = 0; i < powerupSize; i++) {
            if (i == 0) {
                ibtnFirstPowerup.setImageResource(intent.getIntExtra(Keys.KEYS_EFFECT_FIRST.name(), 0));
                ibtnFirstPowerup.setOnClickListener(v ->
                        ibtnFirstPowerup.setClickable(false));
                ibtnFirstPowerup.setVisibility(View.VISIBLE);
            } else if (i == 1) {
                ibtnSecondPowerup.setImageResource(intent.getIntExtra(Keys.KEYS_EFFECT_SECOND.name(), 0));
                ibtnSecondPowerup.setOnClickListener(v ->
                        ibtnSecondPowerup.setClickable(false));
                ibtnSecondPowerup.setVisibility(View.VISIBLE);
            } else if (i == 2) {
                ibtnThirdPowerup.setImageResource(intent.getIntExtra(Keys.KEYS_EFFECT_THIRD.name(), 0));
                ibtnThirdPowerup.setOnClickListener(v ->
                        ibtnFirstPowerup.setClickable(false));
                ibtnThirdPowerup.setVisibility(View.VISIBLE);
            }
        }
    }

    public void gameOver() {
        this.ingame.stop();
        this.ingame.release();
        Intent intent = new Intent(IngameActivity.this, PostGameActivity.class);
        intent.putExtra(Keys.KEY_TV_TIME.name(), timer.getText());
        intent.putExtra(Keys.KEY_TV_SCORE.name(), tvScore.getText());
        startActivity(intent);
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        ingame.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.ingame.pause();
    }

    public void stopTimer() {
        this.timer.stop();
    }
}
