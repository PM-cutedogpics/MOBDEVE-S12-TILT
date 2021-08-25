package com.mobdeve.s12.tiltosurvive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class StoreActivity extends AppCompatActivity {

    private ImageButton ibtnFreeze;
    private ImageButton ibtnNuke;
    private ImageButton ibtnLazer;
    private ImageButton ibtnForceField;
    private ImageButton ibtnHaste;
    private ImageButton ibtnSpeedDown;

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