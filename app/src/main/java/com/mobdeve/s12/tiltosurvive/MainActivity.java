package com.mobdeve.s12.tiltosurvive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AchievementModel achievement;

    private ImageButton ibtnSettings;
    private ImageButton ibtnStats;
    private ImageButton ibtnAchievments;
    private ImageButton ibtnStart;
    private ImageButton ibtnStore;
    private ImageButton ibtnInstructions;
    private ImageButton ibtnMoo;
    private ProgressBar pbMain;

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
        this.ibtnStart = findViewById(R.id.ibtn_start);
        this.ibtnStore = findViewById(R.id.ibtn_store);
        this.ibtnInstructions = findViewById(R.id.ibtn_instructions);
        this.ibtnMoo = findViewById(R.id.ib_moo);
        this.pbMain = findViewById(R.id.pb_main);

        this.ibtnAchievments.setOnClickListener(v -> {
            this.pbMain.setVisibility(View.VISIBLE);
            Intent intent = new Intent(MainActivity.this, AchievementsActivity.class);
            startActivity(intent);
        });

        this.ibtnStart.setOnClickListener(v -> {
            this.pbMain.setVisibility(View.VISIBLE);
            Intent intent = new Intent(MainActivity.this, PreGameActivity.class);
            startActivity(intent);
        });

        this.ibtnStore.setOnClickListener(v -> {
            this.pbMain.setVisibility(View.VISIBLE);
            Intent intent = new Intent(MainActivity.this, StoreActivity.class);
            startActivity(intent);
        });

        this.ibtnSettings.setOnClickListener(v -> {
            this.pbMain.setVisibility(View.VISIBLE);
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        this.ibtnStats.setOnClickListener(v -> {
            this.pbMain.setVisibility(View.VISIBLE);
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        this.ibtnInstructions.setOnClickListener(v -> {
            this.pbMain.setVisibility(View.VISIBLE);
            Intent intent = new Intent(MainActivity.this, HowToPlayActivity.class);
            startActivity(intent);
        });

        this.ibtnMoo.setOnClickListener(v -> {
            Cursor cursor = this.helper.readOneAchievement("Moo");
            while (cursor.moveToNext()){
                AchievementModel achievementModel = new AchievementModel(cursor.getString(1), cursor.getString(2), cursor.getInt(3));
                this.achievement = achievementModel;
            }

            if (this.achievement.getAchieved() == 0) {
                this.helper.updateAchievements("Moo", 1);
                Toast.makeText(this, "You have earned Moo!", Toast.LENGTH_SHORT).show();
            }
            else {
                this.helper.updateAchievements("Moo", 0);
                Toast.makeText(this, "MOOOOO", Toast.LENGTH_SHORT).show();
            }
        });

        this.music = MediaPlayer.create(getApplicationContext(), R.raw.menumusic);
        this.music.setLooping(true);
        this.music.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.music.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.music.start();
        this.pbMain.setVisibility(View.GONE);
    }
}