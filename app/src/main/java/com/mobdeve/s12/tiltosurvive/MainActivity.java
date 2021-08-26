package com.mobdeve.s12.tiltosurvive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton ibtnSettings;
    private ImageButton ibtnStats;
    private ImageButton ibtnAchievments;
    private Button btnStart;
    private Button btnStore;
    private Button btnInstructions;

    public static MediaPlayer music;

    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initComponents();
    }

    private void initComponents() {
        this.helper = new DatabaseHelper(MainActivity.this);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        this.ibtnSettings = findViewById(R.id.ibtn_settings);
        this.ibtnStats = findViewById(R.id.ibtn_stats);
        this.ibtnAchievments = findViewById(R.id.ibtn_achie);
        this.btnStart = findViewById(R.id.btn_start);
        this.btnStore = findViewById(R.id.btn_store);
        this.btnInstructions = findViewById(R.id.btn_instructions);

        this.ibtnAchievments.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AchievementsActivity.class);
            startActivity(intent);
        });

        this.btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PreGameActivity.class);
            startActivity(intent);
            finish();
        });

        this.btnStore.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StoreActivity.class);
            startActivity(intent);
        });

        this.ibtnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        this.music = MediaPlayer.create(getApplicationContext(), R.raw.menumusic);
        this.music.setLooping(true);
        this.music.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        music.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        music.start();
    }
}