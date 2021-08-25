package com.mobdeve.s12.tiltosurvive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class StoreActivity extends AppCompatActivity {

    private ImageButton ibtnFreeze;
    private ImageButton ibtnNuke;
    private ImageButton ibtnLazer;
    private ImageButton ibtnForceField;
    private ImageButton ibtnHaste;
    private ImageButton ibtnSpeedDown;

    private TextView tvPriceFreeze;
    private TextView tvPriceNuke;
    private TextView tvPriceLazer;
    private TextView tvPriceForceField;
    private TextView tvPriceHaste;
    private TextView tvPriceSpeedDown;

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
    }

    private void initComponents() {
        this.ibtnFreeze = findViewById(R.id.ibtn_freeze);
        this.ibtnNuke = findViewById(R.id.ibtn_nuke);
        this.ibtnLazer = findViewById(R.id.ibtn_lazer);
        this.ibtnForceField = findViewById(R.id.ibtn_shield);
        this.ibtnHaste = findViewById(R.id.ibtn_speed_up);
        this.ibtnSpeedDown = findViewById(R.id.ibtn_speed_down);

        this.tvPriceFreeze = findViewById(R.id.tv_price_freeze);
        this.tvPriceNuke = findViewById(R.id.tv_price_nuke);
        this.tvPriceLazer = findViewById(R.id.tv_price_lazer);
        this.tvPriceForceField = findViewById(R.id.tv_price_shield);
        this.tvPriceHaste = findViewById(R.id.tv_price_speed_up);
        this.tvPriceSpeedDown = findViewById(R.id.tv_price_speed_down);

        this.ibtnFreeze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(StoreActivity.this);
                long result = db.updatePower("0", true);
                finishUpdateStore(result);
                ibtnFreeze.setClickable(false);
                ibtnFreeze.setColorFilter(Color.argb(80, 43, 43, 43));
                tvPriceFreeze.setText("SOLD");
            }
        });

        this.ibtnNuke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(StoreActivity.this);
                long result = db.updatePower("1", true);
                finishUpdateStore(result);
                ibtnNuke.setClickable(false);
                ibtnNuke.setColorFilter(Color.argb(80, 43, 43, 43));
                tvPriceNuke.setText("SOLD");
            }
        });

        this.ibtnLazer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(StoreActivity.this);
                long result = db.updatePower("2", true);
                finishUpdateStore(result);
                ibtnLazer.setClickable(false);
                ibtnLazer.setColorFilter(Color.argb(80, 43, 43, 43));
                tvPriceLazer.setText("SOLD");
            }
        });

        this.ibtnForceField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(StoreActivity.this);
                long result = db.updatePower("3", true);
                finishUpdateStore(result);
                ibtnForceField.setClickable(false);
                ibtnForceField.setColorFilter(Color.argb(80, 43, 43, 43));
                tvPriceForceField.setText("SOLD");
            }
        });

        this.ibtnHaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(StoreActivity.this);
                long result = db.updatePower("4", true);
                finishUpdateStore(result);
                ibtnHaste.setClickable(false);
                ibtnHaste.setColorFilter(Color.argb(80, 43, 43, 43));
                tvPriceHaste.setText("SOLD");
            }
        });

        this.ibtnSpeedDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(StoreActivity.this);
                long result = db.updatePower("5", true);
                finishUpdateStore(result);
                ibtnSpeedDown.setClickable(false);
                ibtnSpeedDown.setColorFilter(Color.argb(80, 43, 43, 43));
                tvPriceSpeedDown.setText("SOLD");
            }
        });
    }

    private void finishUpdateStore(long result) {
        if (result == -1) {
            Toast.makeText(this, "Failed to Buy Power Up", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Power Up Succesfully Bought", Toast.LENGTH_SHORT).show();
        }
    }
}