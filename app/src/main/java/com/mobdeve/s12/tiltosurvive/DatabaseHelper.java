package com.mobdeve.s12.tiltosurvive;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "GameInformation.db";
    private static final int DATABASE_VERSION = 1;
    // Power-ups Collection - update
    private static final String TABLE_NAME_POWER = "power_information";
    private static final String COLUMN_ID_POWER = "_id";
    private static final String COLUMN_POWER_NAME = "power_name";
    private static final String COLUMN_POWER_OWNED = "power_owned";
    // History Collection - add
    private static final String TABLE_NAME_HISTORY = "history_information";
    private static final String COLUMN_ID_HISTORY = "_id";
    private static final String COLUMN_HISTORY_DATE = "history_date";
    private static final String COLUMN_HISTORY_SCORE = "history_score";
    private static final String COLUMN_HISTORY_TIME = "history_time";
    // Overall Statistics Collection - update
    private static final String TABLE_NAME_STATS = "stats_information";
    private static final String COLUMN_ID_STATS = "_id";
    private static final String COLUMN_STATS_TIME = "stats_time";           // Total play time
    private static final String COLUMN_STATS_DEATHS = "stats_deaths";
    private static final String COLUMN_STATS_POWERS = "stats_powers";
    private static final String COLUMN_STATS_LONGEST = "stats_longest";
    private static final String COLUMN_STATS_HIGHEST = "stats_highest";
    // Achievements Collection - update
    private static final String TABLE_NAME_ACHIE = "achie_information";
    private static final String COLUMN_ID_ACHIE = "_id";
    private static final String COLUMN_ACHIE_NAME = "achie_name";
    private static final String COLUMN_ACHIE_ACHIEVED = "achie_achieved";
    // Settings Collection - update
    private static final String TABLE_NAME_SETTINGS = "settings_information";
    private static final String COLUMN_ID_SETTINGS = "_id";
    private static final String COLUMN_SETTINGS_MUSIC = "settings_music";
    private static final String COLUMN_SETTINGS_SFX = "settings_sfx";

    public DatabaseHelper(@Nullable Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_query_power =
                "CREATE TABLE " + TABLE_NAME_POWER + " (" +
                COLUMN_ID_POWER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_POWER_NAME + " TEXT, " +
                COLUMN_POWER_OWNED + " INTEGER, " +
                        ");";

        String create_query_history =
                "CREATE TABLE " + TABLE_NAME_HISTORY + " (" +
                        COLUMN_ID_HISTORY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_HISTORY_DATE + " TEXT, " +
                        COLUMN_HISTORY_SCORE + " INTEGER, " +
                        COLUMN_HISTORY_TIME + " TEXT, " +
                        ");";

        String create_query_stats =
                "CREATE TABLE " + TABLE_NAME_STATS + " (" +
                        COLUMN_ID_STATS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_STATS_TIME + " TEXT, " +
                        COLUMN_STATS_DEATHS + " INTEGER, " +
                        COLUMN_STATS_POWERS + " INTEGER, " +
                        COLUMN_STATS_LONGEST + " TEXT, " +
                        COLUMN_STATS_HIGHEST + " INTEGER, " +
                        ");";

        String create_query_achie =
                "CREATE TABLE " + TABLE_NAME_ACHIE + " (" +
                        COLUMN_ID_ACHIE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_ACHIE_NAME + " TEXT, " +
                        COLUMN_ACHIE_ACHIEVED + " INTEGER, " +
                        ");";

        String create_query_settings =
                "CREATE TABLE " + TABLE_NAME_SETTINGS + " (" +
                        COLUMN_ID_SETTINGS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_SETTINGS_MUSIC + " INTEGER, " +
                        COLUMN_SETTINGS_SFX + " INTEGER, " +
                        ");";

        sqLiteDatabase.execSQL(create_query_power);
        sqLiteDatabase.execSQL(create_query_history);
        sqLiteDatabase.execSQL(create_query_stats);
        sqLiteDatabase.execSQL(create_query_achie);
        sqLiteDatabase.execSQL(create_query_settings);
    }

    public long updatePower(String rowId, String name, Boolean owned) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_POWER_NAME, name);
        cv.put(COLUMN_POWER_OWNED, owned);

        long result = db.update(TABLE_NAME_POWER, cv, "_id = ?", new String[]{rowId});

        return result;
    }

    public long addHistory(String rowId, String date, int score, String time) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_HISTORY_DATE, date);
        cv.put(COLUMN_HISTORY_SCORE, date);
        cv.put(COLUMN_HISTORY_TIME, date);

        long result = db.insert(TABLE_NAME_HISTORY, null, cv);

        return result;
    }

    public long updateStats(String rowId, String time, int deaths, int powers, String longest, int highest) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_STATS_TIME, time);
        cv.put(COLUMN_STATS_DEATHS, deaths);
        cv.put(COLUMN_STATS_POWERS, powers);
        cv.put(COLUMN_STATS_LONGEST, longest);
        cv.put(COLUMN_STATS_HIGHEST, highest);

        long result = db.update(TABLE_NAME_STATS, cv, "_id = ?", new String[]{rowId});

        return result;
    }

    public long updateAchie(String rowId, String name, Boolean achieved) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ACHIE_NAME, name);
        cv.put(COLUMN_ACHIE_ACHIEVED, achieved);

        long result = db.update(TABLE_NAME_ACHIE, cv, "_id = ?", new String[]{rowId});

        return result;
    }

    public long updateSettings(String rowId, Boolean music, Boolean sfx) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SETTINGS_MUSIC, music);
        cv.put(COLUMN_SETTINGS_SFX, sfx);

        long result = db.update(TABLE_NAME_SETTINGS, cv, "_id = ?", new String[]{rowId});

        return result;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //What do do here
    }
}
