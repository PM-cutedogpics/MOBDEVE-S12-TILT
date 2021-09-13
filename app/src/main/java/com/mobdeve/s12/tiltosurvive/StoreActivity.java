package com.mobdeve.s12.tiltosurvive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StoreActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StoreAdapter storeAdapter;
    private ArrayList<PowerUpsModel> powerups;

    private TextView tvBalance;
    private ImageButton ibtnBack;

    private DatabaseHelper helper;

    private int balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        initComponents();
        initRecyclerView();
    }

    private void initComponents() {
        this.tvBalance = findViewById(R.id.tv_balance);

        this.helper = new DatabaseHelper(StoreActivity.this);
        Cursor cursor = this.helper.readStats();
        while (cursor.moveToNext()) {
            this.balance = (cursor.getInt(2));
        }

        this.tvBalance.setText(String.valueOf(this.balance));
        this.ibtnBack = findViewById(R.id.ibtn_store_back);
        this.ibtnBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void initRecyclerView() {
        this.recyclerView = findViewById(R.id.rv_store_powerup);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        this.recyclerView.setLayoutManager(gridLayoutManager);

        this.powerups = new ArrayList<PowerUpsModel>();
        this.helper = new DatabaseHelper(StoreActivity.this);
        Cursor cursor = this.helper.readPowerData();
        while (cursor.moveToNext()){
            int icon = getApplicationContext().getResources().getIdentifier(cursor.getString(5).trim(), "drawable", getApplicationContext().getPackageName());
            int iconOwned = getApplicationContext().getResources().getIdentifier(cursor.getString(6).trim(), "drawable",getApplicationContext().getPackageName());

            //                                                                                    title, desc, imageid, activated, isSelected, owned, price
            PowerUpsModel powerUpsModel = new PowerUpsModel(cursor.getString(1), cursor.getString(2), icon, iconOwned, cursor.getInt(3), cursor.getInt(4), cursor.getInt(7));
            this.powerups.add(powerUpsModel);
        }
        this.storeAdapter = new StoreAdapter(StoreActivity.this, StoreActivity.this, this.helper, this.powerups, this.tvBalance);
        this.recyclerView.setAdapter(this.storeAdapter);
        this.helper.close();
    }

    private void finishUpdateStore(long result) {
        if (result == -1) {
            Toast.makeText(this, "Failed to Buy Power Up", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Power Up Successfully Bought", Toast.LENGTH_SHORT).show();
        }
    }

    private void finishUpdateBalance(long result) {
        if (result == -1) {
            Toast.makeText(this, "Failed to Save Balance", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Balance Saved", Toast.LENGTH_SHORT).show();
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

//    @Override
//    protected void onStop(){
//        super.onStop();
//        MainActivity.music.pause();
//    }
}