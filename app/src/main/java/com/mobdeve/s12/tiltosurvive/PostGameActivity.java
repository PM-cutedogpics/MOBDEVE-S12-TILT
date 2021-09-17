package com.mobdeve.s12.tiltosurvive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import static android.app.Notification.DEFAULT_SOUND;
import static android.app.Notification.DEFAULT_VIBRATE;

public class PostGameActivity extends AppCompatActivity {
    private ImageButton ibtnHome;
    private ImageButton ibtnPlay;

    private TextView tvScore;
    private TextView tvTime;

    private DatabaseHelper helper;

    private AchievementModel achievement;

    String GROUP_ACHIEVEMENTS = "com.android.mobdeve.achievements";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_game);

        createNotificationChannel();

        this.helper = new DatabaseHelper(PostGameActivity.this);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        this.ibtnHome = findViewById(R.id.ibtn_home);
        this.ibtnPlay = findViewById(R.id.ibtn_play_again);
        this.tvScore = findViewById(R.id.tv_post_game_score);
        this.tvTime = findViewById(R.id.tv_post_game_time);

        this.ibtnHome.setOnClickListener(v -> {
            finish();
        });

        this.ibtnPlay.setOnClickListener(v -> {
            Intent intent = new Intent(PostGameActivity.this, PreGameActivity.class);
            startActivity(intent);
            finish();
        });

        MainActivity.music = MediaPlayer.create(getApplicationContext(), R.raw.menumusic);
        MainActivity.music.setLooping(true);
        MainActivity.music.start();

        this.loadData();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "achievementsChannel";
            String description = "Channel for Tilt to Survive Achievements";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("notifyAchievement", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void loadData(){
        Intent intent = getIntent();
        String loadTvScore = intent.getStringExtra(Keys.KEY_TV_SCORE.name());
        String loadTvTime = intent.getStringExtra(Keys.KEY_TV_TIME.name());

        Cursor cursor = this.helper.readStats();
        while (cursor.moveToNext()){
            Integer highscore = new Integer(cursor.getInt(1));
            if (Integer.parseInt(loadTvScore) > highscore) {
                this.helper.updateStats("1", Integer.parseInt(loadTvScore));
            }
            this.helper.updateBalance("1", cursor.getInt(2) + Integer.parseInt(loadTvScore));
        }

        this.tvScore.setText(loadTvScore);
        this.tvTime.setText(loadTvTime);

        String date = new CustomDate().toStringFull();

        long result = this.helper.addHistory(date, Integer.valueOf(loadTvScore), loadTvTime);

        this.helper.resetPowerupsActive();

        this.finishAddGameStats(result);

        checkAchievements(Integer.valueOf(loadTvScore));
    }

    private void checkAchievements(int score) {
        System.out.println("SCORE: " + score);
        if (score > 50) {
            Cursor cursor = this.helper.readOneAchievement("Giveaway!");
            while (cursor.moveToNext()){
                AchievementModel achievementModel = new AchievementModel(cursor.getString(1), cursor.getString(2), cursor.getInt(3));
                this.achievement = achievementModel;
            }

            if (this.achievement.getAchieved() == 0) {
                this.helper.updateAchievements("Giveaway!", 1);
//                sendNotification("You have earned Giveaway!");
//                Toast.makeText(this, "You have earned Giveaway!", Toast.LENGTH_SHORT).show();
                NotificationCompat.Builder builder1 = new NotificationCompat.Builder(this, "notifyAchievement")
                        .setSmallIcon(R.drawable.notif)
                        .setContentTitle("Achievement Unlocked!")
                        .setContentText("You have earned Giveaway!")
                        .setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE) //Important for heads-up notification
                        .setPriority(NotificationCompat.PRIORITY_MAX);

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
                int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
                notificationManagerCompat.notify(m, builder1.build());
            }
        }

        if (score > 100) {
            Cursor cursor = this.helper.readOneAchievement("Stampede");
            while (cursor.moveToNext()){
                AchievementModel achievementModel = new AchievementModel(cursor.getString(1), cursor.getString(2), cursor.getInt(3));
                this.achievement = achievementModel;
            }

            if (this.achievement.getAchieved() == 0) {
                this.helper.updateAchievements("Stampede", 1);
//                sendNotification("You have earned Stampede!");
//                Toast.makeText(this, "You have earned Stampede!", Toast.LENGTH_SHORT).show();
                NotificationCompat.Builder builder2 = new NotificationCompat.Builder(this, "notifyAchievement")
                        .setSmallIcon(R.drawable.notif)
                        .setContentTitle("Achievement Unlocked!")
                        .setContentText("You have earned Stampede!")
                        .setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE) //Important for heads-up notification
                        .setPriority(NotificationCompat.PRIORITY_MAX);

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
                int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
                notificationManagerCompat.notify(m, builder2.build());

            }
        }

        if (score > 1000) {
            Cursor cursor = this.helper.readOneAchievement("Cattle Driver");
            while (cursor.moveToNext()){
                AchievementModel achievementModel = new AchievementModel(cursor.getString(1), cursor.getString(2), cursor.getInt(3));
                this.achievement = achievementModel;
            }

            if (this.achievement.getAchieved() == 0) {
                this.helper.updateAchievements("Cattle Driver", 1);
//                sendNotification("You have earned Cattle Driver!");
//                Toast.makeText(this, "You have earned Cattle Driver!", Toast.LENGTH_SHORT).show();
                NotificationCompat.Builder builder3 = new NotificationCompat.Builder(this, "notifyAchievement")
                        .setSmallIcon(R.drawable.notif)
                        .setContentTitle("Achievement Unlocked!")
                        .setContentText("You have earned Cattle Driver!")
                        .setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE) //Important for heads-up notification
                        .setPriority(NotificationCompat.PRIORITY_MAX);

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
                int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
                notificationManagerCompat.notify(m, builder3.build());
            }
        }
    }

//    private void sendNotification(String message) {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "notifyAchievement")
//                .setSmallIcon(R.drawable.notif)
//                .setContentTitle("Achievement Unlocked!")
//                .setContentText(message)
//                .setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE) //Important for heads-up notification
//                .setGroup(GROUP_ACHIEVEMENTS)
//                .setPriority(NotificationCompat.PRIORITY_MAX);
//
//        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
//        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
//        notificationManagerCompat.notify(m, builder.build());
//    }

    private void finishAddGameStats(long result) {
        if (result == -1) {
            Toast.makeText(this, "Failed to Add Game Stats to History", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Added Game Stats to History", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.music.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.music.start();
    }
}