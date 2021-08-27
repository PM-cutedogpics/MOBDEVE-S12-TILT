package com.mobdeve.s12.tiltosurvive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class AchievementsActivity extends AppCompatActivity {

    private Button btnBack;

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
        this.btnBack = findViewById(R.id.btn_achievements_back);
        this.btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(AchievementsActivity.this, MainActivity.class);
            startActivity(intent);
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
    }

//    private void refreshData() {
//        Cursor cursor = this.helper.readAllData();
//
//
//    }

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