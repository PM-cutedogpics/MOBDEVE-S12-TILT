package com.mobdeve.s12.tiltosurvive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class PreGameActivity extends AppCompatActivity {

    private Button btnBack;
    private Button btnStart;

    private RecyclerView recyclerView;
    private PreGameAdapter preGameAdapter;
    private ArrayList<PowerUpsModel> powerups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_game);

        this.initRecyclerView();

        this.btnBack = findViewById(R.id.btn_pregame_back);
        this.btnStart = findViewById(R.id.btn_pregame_confirm);

        this.btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(PreGameActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        this.btnStart.setOnClickListener(v -> {
            Toast.makeText(this, "GAME START", Toast.LENGTH_SHORT).show();
        });

    }

    private void initRecyclerView() {
        this.recyclerView = findViewById(R.id.rv_pregame);

        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        this.powerups = new DataHelper().initPowerUps();
        this.preGameAdapter = new PreGameAdapter(PreGameActivity.this, PreGameActivity.this, this.powerups);
        this.recyclerView.setAdapter(this.preGameAdapter);

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