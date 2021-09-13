package com.mobdeve.s12.tiltosurvive;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class IngameActivity extends AppCompatActivity {
    private ImageButton ibtnPause;
    private Chronometer timer;
    private ImageButton ibtnClick;
    private ImageButton ibtnEnd;
    private TextView tvScore;
    private int fun;
    private MediaPlayer ingame;
    private ImageButton ibtnResume;
    private ImageButton ibtnMainMenu;
    private TextView tvResume;
    private TextView tvMainMenu;

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
        this.ibtnClick = findViewById(R.id.ibtn_click);
        this.ibtnEnd = findViewById(R.id.ibtn_end);
        this.timer = findViewById(R.id.chr_time);
        this.tvScore = findViewById(R.id.tv_ingame_score);
        this.ibtnResume = findViewById(R.id.ibtn_ingame_resume);
        this.ibtnMainMenu = findViewById(R.id.ibtn_ingame_mainmenu);
        this.tvResume = findViewById(R.id.tv_resume);
        this.tvMainMenu = findViewById(R.id.tv_mainmenu);

        MainActivity.music.pause();

        this.ingame = MediaPlayer.create(getApplicationContext(), R.raw.ingamemusic);
        this.ingame.setLooping(true);
        this.ingame.start();

        timer.start();

        this.ibtnEnd.setOnClickListener(v -> {
            timer.stop();
            this.ingame.stop();
            this.ingame.release();
            Intent intent = new Intent(IngameActivity.this, PostGameActivity.class);
            intent.putExtra(Keys.KEY_TV_TIME.name(), timer.getText());
            intent.putExtra(Keys.KEY_TV_SCORE.name(), tvScore.getText());
            startActivity(intent);
            finish();
        });

        this.fun = 0;
        this.ibtnClick.setOnClickListener(v -> {
            this.fun += 1;
            this.tvScore.setText(String.valueOf(this.fun));
        });

        this.ibtnPause.setOnClickListener(v -> {
            timer.stop();
            this.ingame.pause();
            this.ibtnResume.setVisibility(View.VISIBLE);
            this.tvResume.setVisibility(View.VISIBLE);
            this.ibtnMainMenu.setVisibility(View.VISIBLE);
            this.tvMainMenu.setVisibility(View.VISIBLE);
        });

        this.ibtnResume.setOnClickListener(v -> {
            timer.start();
            this.ingame.start();
            this.ibtnResume.setVisibility(View.GONE);
            this.tvResume.setVisibility(View.GONE);
            this.ibtnMainMenu.setVisibility(View.GONE);
            this.tvMainMenu.setVisibility(View.GONE);
        });

        this.ibtnMainMenu.setOnClickListener(v -> {
            this.ingame.release();
            Intent intent = new Intent(IngameActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        ingame.start();
    }

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        timer.stop();
        this.ingame.pause();
        this.ibtnResume.setVisibility(View.VISIBLE);
        this.tvResume.setVisibility(View.VISIBLE);
        this.ibtnMainMenu.setVisibility(View.VISIBLE);
        this.tvMainMenu.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ingame.pause();
    }
}
