package com.mobdeve.s12.tiltosurvive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

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

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;
        setContentView(R.layout.activity_pre_game);

        this.initRecyclerView();

        this.ibtnBack = findViewById(R.id.btn_pregame_back);
        this.ibtnStart = findViewById(R.id.btn_pregame_confirm);

        this.ibtnBack.setOnClickListener(v -> {
            this.helper.resetPowerupsActive();
            finish();
        });

        this.ibtnStart.setOnClickListener(v -> {
            Intent intent = new Intent(PreGameActivity.this, IngameActivity.class);

            int[] imageId = new int[3];
            int[] usedId = new int[3];
            String[] powerupNames = new String[3];
            int j = 0;
            for (int i = 0; i < powerups.size(); i++){
                if(powerups.get(i).isSelected() == 1){
                    imageId[j] = powerups.get(i).getActivatedImageId();
                    usedId[j] = powerups.get(i).getImageId();
                    powerupNames[j] = powerups.get(i).getTitle();
                    j++;
                }
            }
            System.out.println(imageId.length);
            for(int i = 0; i < imageId.length; i++) {
                if (i == 0) {
                    intent.putExtra(Keys.KEYS_ACTIVATED_FIRST.name(), imageId[i]);
                    intent.putExtra(Keys.KEYS_USED_FIRST.name(), usedId[i]);
                    intent.putExtra(Keys.KEYS_IB_FIRST.name(), powerupNames[i]);
                } else if (i == 1) {
                    intent.putExtra(Keys.KEYS_ACTIVATED_SECOND.name(), imageId[i]);
                    intent.putExtra(Keys.KEYS_USED_SECOND.name(), usedId[i]);
                    intent.putExtra(Keys.KEYS_IB_SECOND.name(), powerupNames[i]);
                } else if (i == 2) {
                    intent.putExtra(Keys.KEYS_ACTIVATED_THIRD.name(), imageId[i]);
                    intent.putExtra(Keys.KEYS_USED_THIRD.name(), usedId[i]);
                    intent.putExtra(Keys.KEYS_IB_THIRD.name(), powerupNames[i]);
                }
            }

            intent.putExtra(Keys.KEYS_INGAME_SIZE.name(), "" + imageId.length);

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
            PowerUpsModel powerUpsModel = new PowerUpsModel(cursor.getString(1), cursor.getString(2), icon, iconActivated, cursor.getInt(3), cursor.getInt(4), cursor.getInt(4));
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