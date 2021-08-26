package com.mobdeve.s12.tiltosurvive;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class IngameActivity extends AppCompatActivity {
    public static final String KEY_TIME = "KEY_TIME";

    private ImageButton btnPause;
    private Chronometer timer;
    private Button fake;

    private MediaPlayer ingame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingame);

        this.btnPause = findViewById(R.id.btn_pause);
        this.timer = findViewById(R.id.chr_time);
        this.fake = findViewById(R.id.btn_fake);

        MainActivity.music.pause();

        this.ingame = MediaPlayer.create(getApplicationContext(), R.raw.ingamemusic);
        this.ingame.setLooping(true);
        this.ingame.start();

        timer.start();

        this.fake.setOnClickListener(v -> {
            timer.stop();
            this.ingame.stop();
            this.ingame.release();
            Intent intent = new Intent(IngameActivity.this, PostGameActivity.class);
            intent.putExtra(KEY_TIME, timer.getText());
            startActivity(intent);
            finish();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        ingame.start();
    }
}
