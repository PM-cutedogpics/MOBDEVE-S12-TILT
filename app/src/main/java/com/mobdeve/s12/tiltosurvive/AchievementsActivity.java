package com.mobdeve.s12.tiltosurvive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class AchievementsActivity extends AppCompatActivity {

    private ImageButton ibtnBack;

    private ArrayList<AchievementModel> achievements;

    private RecyclerView recyclerView;
    private AchievementsAdapter achievementsAdapter;

    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        this.initRecyclerView();
        this.ibtnBack = findViewById(R.id.ibtn_achie_back);
        this.ibtnBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void initRecyclerView() {
        this.achievements = new ArrayList<AchievementModel>();
        this.helper = new DatabaseHelper(AchievementsActivity.this);
        Cursor cursor = this.helper.readAchievements();
        while (cursor.moveToNext()){
            AchievementModel achievementModel = new AchievementModel(cursor.getString(1), cursor.getString(2), cursor.getInt(3));
            this.achievements.add(achievementModel);
        }
        this.recyclerView = findViewById(R.id.rv_achievements);
        this.achievementsAdapter = new AchievementsAdapter(AchievementsActivity.this, AchievementsActivity.this, this.achievements);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.recyclerView.setAdapter(this.achievementsAdapter);
        this.helper.close();
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