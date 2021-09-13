package com.mobdeve.s12.tiltosurvive;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Build;
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

    private final static String TAG = "MainActivity";
    public final static String PREFS = "PrefsFile";

    private SharedPreferences settings = null;
    private SharedPreferences.Editor editor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initComponents();
        createNotificationChannel();
        // Save time of run:
        settings = getSharedPreferences(PREFS, MODE_PRIVATE);
        editor = settings.edit();

        // First time running app?
        if (!settings.contains("lastRun"))
            enableNotification(null);
        else
            recordRunTime();

        Log.v(TAG, "Starting CheckRecentRun service...");
        startService(new Intent(this,  CheckRecentRun.class));
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "remindUserChannel";
            String description = "Channel for Tilt to Survive Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyUser", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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

    public void recordRunTime() {
        editor.putLong("lastRun", System.currentTimeMillis());
        editor.commit();
        Log.v(TAG, "Recording Time");
    }

    public void enableNotification(View v) {
        editor.putLong("lastRun", System.currentTimeMillis());
        editor.putBoolean("enabled", true);
        editor.commit();
        Log.v(TAG, "Notifications enabled");
    }

    public void disableNotification(View v) {
        editor.putBoolean("enabled", false);
        editor.commit();
        Log.v(TAG, "Notifications disabled");
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

//    @Override
//    protected void onStop(){
//        super.onStop();
//        MainActivity.music.pause();
//        MainActivity.music.release();
//    }

}