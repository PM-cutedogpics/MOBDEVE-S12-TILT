package com.mobdeve.s12.tiltosurvive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnAchievments;
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initComponents();
    }

    private void initComponents() {
        this.btnAchievments = findViewById(R.id.);
        this.btnStart = findViewById(R.id.btn_start);

        this.btnAchievments.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AchievementsActivity.class);
            startActivity(intent);
            finish();
        });

        this.btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PreGameActivity.class);
            startActivity(intent);
            finish();
        });
    }
}