package com.mobdeve.s12.tiltosurvive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class PreGameActivity extends AppCompatActivity {

    private ImageButton ibtnBack;
    private ImageButton ibtnStart;

    private RecyclerView recyclerView;
    private PreGameAdapter preGameAdapter;
    private ArrayList<PowerUpsModel> powerups;

    private DatabaseHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_game);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        this.initRecyclerView();

        this.ibtnBack = findViewById(R.id.btn_pregame_back);
        this.ibtnStart = findViewById(R.id.btn_pregame_confirm);

        this.btnBack.setOnClickListener(v -> {
            this.helper.resetPowerupsActive();
            Intent intent = new Intent(PreGameActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        this.ibtnStart.setOnClickListener(v -> {
            Intent intent = new Intent(PreGameActivity.this, IngameActivity.class);
            startActivity(intent);
            finish();
        });

    }

    private void initRecyclerView() {

        this.recyclerView = findViewById(R.id.rv_pregame);

        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        this.powerups = new ArrayList<PowerUpsModel>();
        this.helper = new DatabaseHelper(PreGameActivity.this);
        Cursor cursor = this.helper.readPowerData();
        while (cursor.moveToNext()){
            int icon = getApplicationContext().getResources().getIdentifier(cursor.getString(5).trim(), "drawable", getApplicationContext().getPackageName());
            int iconActivated = getApplicationContext().getResources().getIdentifier(cursor.getString(6).trim(), "drawable",getApplicationContext().getPackageName());

            //                                                                                    title, desc, imageid, activated, isSelected, owned
            PowerUpsModel powerUpsModel = new PowerUpsModel(cursor.getString(1), cursor.getString(2), icon, iconActivated, cursor.getInt(3), cursor.getInt(4));
            this.powerups.add(powerUpsModel);
        }

        this.preGameAdapter = new PreGameAdapter(PreGameActivity.this, PreGameActivity.this, this.helper, this.powerups);
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