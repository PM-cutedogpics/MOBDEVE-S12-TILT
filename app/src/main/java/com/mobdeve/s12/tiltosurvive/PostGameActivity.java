package com.mobdeve.s12.tiltosurvive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class PostGameActivity extends AppCompatActivity {
    private ImageButton ibtnHome;
    private ImageButton ibtnPlay;

    private TextView tvScore;
    private TextView tvTime;

    private DatabaseHelper helper;
    private MediaPlayer music;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_game);
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

        this.music = MediaPlayer.create(getApplicationContext(), R.raw.menumusic);
        this.music.setLooping(true);
        this.music.start();

        this.loadData();
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
        }

        this.tvScore.setText(loadTvScore);
        this.tvTime.setText(loadTvTime);

        String date = new CustomDate().toStringFull();

        long result = this.helper.addHistory(date, Integer.valueOf(loadTvScore), loadTvTime);

        this.helper.resetPowerupsActive();
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
    }
}