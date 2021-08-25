package com.mobdeve.s12.tiltosurvive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewAchievementActivity extends AppCompatActivity {

    private TextView tvVaTitle, tvVaDesctiption;
    private Button btnVaBack;
    String title, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_achievement);

        tvVaTitle = findViewById(R.id.tv_va_title);
        tvVaDesctiption = findViewById(R.id.tv_va_description);
        btnVaBack = findViewById(R.id.btn_va_back);

        getAndSetIntentData();

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        btnVaBack.setOnClickListener(v -> {
            Intent intent = new Intent(ViewAchievementActivity.this, AchievementsActivity.class);
            startActivity(intent);
            finish();
        });
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("title") && getIntent().hasExtra("description")) {
            title = getIntent().getStringExtra("title");
            description = getIntent().getStringExtra("description");

            tvVaTitle.setText(title);
            tvVaDesctiption.setText(description);
        }
        else {
            Toast.makeText(this, "No data. Return back to achievements.", Toast.LENGTH_SHORT).show();
        }
    }
}