package com.mobdeve.s12.tiltosurvive;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private ImageButton btnBack;

    private ArrayList<HistoryModel> history;

    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;

    private TextView historyHighscore;

    private DatabaseHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        this.initRecyclerView();
        this.btnBack = findViewById(R.id.btn_history_back);
        this.btnBack.setOnClickListener(v -> {
            finish();
        });

        this.historyHighscore = findViewById(R.id.tv_history_highscore);
        Cursor cursor = this.helper.readStats();
        while (cursor.moveToNext()) {
            this.historyHighscore.setText("HIGHSCORE: " + cursor.getInt(1));
        }
    }

    private void initRecyclerView() {
        this.history = new ArrayList<HistoryModel>();
        this.helper = new DatabaseHelper(HistoryActivity.this);
        Cursor cursor = this.helper.readHistory();
        while (cursor.moveToNext()){
            HistoryModel newHistory = new HistoryModel(cursor.getString(3), cursor.getString(1), cursor.getInt(2));
            this.history.add(newHistory);
        }
        this.recyclerView = findViewById(R.id.rv_history);
        this.historyAdapter = new HistoryAdapter(HistoryActivity.this, HistoryActivity.this, this.history);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.recyclerView.setAdapter(this.historyAdapter);
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
