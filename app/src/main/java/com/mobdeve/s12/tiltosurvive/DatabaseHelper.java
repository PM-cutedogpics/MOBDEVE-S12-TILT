package com.mobdeve.s12.tiltosurvive;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "GameInformation.db";
    private static final int DATABASE_VERSION = 1;
    // Power-ups Collection - update
    private static final String TABLE_NAME_POWER = "power_information";
    private static final String COLUMN_ID_POWER = "_id";
    private static final String COLUMN_POWER_NAME = "power_name";
    private static final String COLUMN_POWER_DESCRIPTION = "power_description";
    private static final String COLUMN_POWER_SELECTED = "power_selected";
    private static final String COLUMN_POWER_OWNED = "power_owned";
    private static final String COLUMN_POWER_ICON = "power_icon";
    private static final String COLUMN_POWER_ICON_ACTIVATED = "power_icon_activated";
    private static final String COLUMN_POWER_PRICE = "power_price";
    // History Collection - add
    private static final String TABLE_NAME_HISTORY = "history_information";
    private static final String COLUMN_ID_HISTORY = "_id";
    private static final String COLUMN_HISTORY_DATE = "history_date";
    private static final String COLUMN_HISTORY_SCORE = "history_score";
    private static final String COLUMN_HISTORY_TIME = "history_time";
    // Overall Statistics Collection - update
    private static final String TABLE_NAME_STATS = "stats_information";
    private static final String COLUMN_ID_STATS = "_id";
    private static final String COLUMN_STATS_HIGHEST = "stats_highest";
    private static final String COLUMN_STATS_BALANCE = "stats_balance";
    // Achievements Collection - update
    private static final String TABLE_NAME_ACHIE = "achie_information";
    private static final String COLUMN_ID_ACHIE = "_id";
    private static final String COLUMN_ACHIE_NAME = "achie_name";
    private static final String COLUMN_ACHIE_DESCRIPTION = "achie_description";
    private static final String COLUMN_ACHIE_ACHIEVED = "achie_achieved";
    // Settings Collection - update
    private static final String TABLE_NAME_SETTINGS = "settings_information";
    private static final String COLUMN_ID_SETTINGS = "_id";
    private static final String COLUMN_SETTINGS_MUSIC = "settings_music";
    private static final String COLUMN_SETTINGS_SFX = "settings_sfx";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_query_power =
                "CREATE TABLE " + TABLE_NAME_POWER + " (" +
                        COLUMN_ID_POWER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_POWER_NAME + " TEXT, " +
                        COLUMN_POWER_DESCRIPTION + " TEXT, "+
                        COLUMN_POWER_SELECTED + " INTEGER," +
                        COLUMN_POWER_OWNED + " INTEGER, " +
                        COLUMN_POWER_ICON + " TEXT, " +
                        COLUMN_POWER_ICON_ACTIVATED + " TEXT, " +
                        COLUMN_POWER_PRICE + " TEXT" +
                        ");";

        String create_query_history =
                "CREATE TABLE " + TABLE_NAME_HISTORY + " (" +
                        COLUMN_ID_HISTORY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_HISTORY_DATE + " TEXT, " +
                        COLUMN_HISTORY_SCORE + " INTEGER, " +
                        COLUMN_HISTORY_TIME + " TEXT" +
                        ");";

        String create_query_stats =
                "CREATE TABLE " + TABLE_NAME_STATS + " (" +
                        COLUMN_ID_STATS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_STATS_HIGHEST + " INTEGER, " +
                        COLUMN_STATS_BALANCE + " INTEGER" +
                        ");";

        String create_query_achie =
                "CREATE TABLE " + TABLE_NAME_ACHIE + " (" +
                        COLUMN_ID_ACHIE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_ACHIE_NAME + " TEXT, " +
                        COLUMN_ACHIE_DESCRIPTION + " TEXT, " +
                        COLUMN_ACHIE_ACHIEVED + " INTEGER" +
                        ");";

        String create_query_settings =
                "CREATE TABLE " + TABLE_NAME_SETTINGS + " (" +
                        COLUMN_ID_SETTINGS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_SETTINGS_MUSIC + " INTEGER, " +
                        COLUMN_SETTINGS_SFX + " INTEGER" +
                        ");";

        sqLiteDatabase.execSQL(create_query_power);
        sqLiteDatabase.execSQL(create_query_history);
        sqLiteDatabase.execSQL(create_query_stats);
        sqLiteDatabase.execSQL(create_query_achie);
        sqLiteDatabase.execSQL(create_query_settings);

        // Initialize Default Values for Power-Ups
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME_POWER + "(" + COLUMN_ID_POWER + ", "
                + COLUMN_POWER_NAME + ", " + COLUMN_POWER_DESCRIPTION + ", " + COLUMN_POWER_SELECTED
                + ", " + COLUMN_POWER_OWNED + ", " + COLUMN_POWER_ICON + ", " + COLUMN_POWER_ICON_ACTIVATED + ", " + COLUMN_POWER_PRICE
                + ") values(1, 'Freeze', 'Freeze cows around the spaceship.', 0, 2, 'freeze_box_grey', 'freeze_box', '500')");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME_POWER + "(" + COLUMN_ID_POWER + ", "
                + COLUMN_POWER_NAME + ", " + COLUMN_POWER_DESCRIPTION + ", " + COLUMN_POWER_SELECTED
                + ", " + COLUMN_POWER_OWNED + ", " + COLUMN_POWER_ICON + ", " + COLUMN_POWER_ICON_ACTIVATED + ", " + COLUMN_POWER_PRICE
                + ") values(2, 'Nuke', 'Nukes around the spaceship.', 0, 2, 'nuke_box_grey', 'nuke_box', '500')");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME_POWER + "(" + COLUMN_ID_POWER + ", "
                + COLUMN_POWER_NAME + ", " + COLUMN_POWER_DESCRIPTION + ", " + COLUMN_POWER_SELECTED
                + ", " + COLUMN_POWER_OWNED + ", " + COLUMN_POWER_ICON + ", " + COLUMN_POWER_ICON_ACTIVATED + ", " + COLUMN_POWER_PRICE
                + ") values(3, 'Laser', 'Shoots a laser depending on where the spaceship is aimed at.', 0, 2, 'laser_box_grey', 'laser_box', '500')");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME_POWER + "(" + COLUMN_ID_POWER + ", "
                + COLUMN_POWER_NAME + ", " + COLUMN_POWER_DESCRIPTION + ", " + COLUMN_POWER_SELECTED
                + ", " + COLUMN_POWER_OWNED + ", " + COLUMN_POWER_ICON + ", " + COLUMN_POWER_ICON_ACTIVATED + ", " + COLUMN_POWER_PRICE
                + ") values(4, 'Force Field', 'Gains a shield for 10 seconds.', 0, 0, 'force_field_box_grey', 'force_field_box', '400')");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME_POWER + "(" + COLUMN_ID_POWER + ", "
                + COLUMN_POWER_NAME + ", " + COLUMN_POWER_DESCRIPTION + ", " + COLUMN_POWER_SELECTED
                + ", " + COLUMN_POWER_OWNED + ", " + COLUMN_POWER_ICON + ", " + COLUMN_POWER_ICON_ACTIVATED + ", " + COLUMN_POWER_PRICE
                + ") values(5, 'Haste', 'Increases speed of the spaceship for 5 seconds.', 0, 0, 'haste_box_grey', 'haste_box', '350')");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME_POWER + "(" + COLUMN_ID_POWER + ", "
                + COLUMN_POWER_NAME + ", " + COLUMN_POWER_DESCRIPTION + ", " + COLUMN_POWER_SELECTED
                + ", " + COLUMN_POWER_OWNED + ", " + COLUMN_POWER_ICON + ", " + COLUMN_POWER_ICON_ACTIVATED + ", " + COLUMN_POWER_PRICE
                + ") values(6, 'Speed Down', 'Slows cows down for 5 seconds.', 0, 0, 'slow_box_grey', 'slow_box', '350')");
        // Initialize Default Values for Statistics
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME_STATS + "(" +
                COLUMN_STATS_HIGHEST + ", " + COLUMN_STATS_BALANCE + ") " +
                "values(0, 1000)");

        sqLiteDatabase.execSQL("insert into " + TABLE_NAME_ACHIE +
                "(" + COLUMN_ACHIE_NAME + ", " + COLUMN_ACHIE_DESCRIPTION
                + ", " + COLUMN_ACHIE_ACHIEVED + ") values('Giveaway!', 'Score atleast 50 points', 0)");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME_ACHIE + "(" + COLUMN_ACHIE_NAME + ", " + COLUMN_ACHIE_DESCRIPTION
                + ", " + COLUMN_ACHIE_ACHIEVED + ") values('Stampede', 'Score atleast 100 points', 0)");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME_ACHIE + "(" + COLUMN_ACHIE_NAME + ", " + COLUMN_ACHIE_DESCRIPTION
                + ", " + COLUMN_ACHIE_ACHIEVED + ") values('Cattle Driver', 'Score atleast 1000 pointss', 0)");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME_ACHIE + "(" + COLUMN_ACHIE_NAME + ", " + COLUMN_ACHIE_DESCRIPTION
                + ", " + COLUMN_ACHIE_ACHIEVED + ") values('Moo', 'Hit the cow', 0)");
    }

    public Cursor readPowerData() {
        String query = "SELECT * FROM " + TABLE_NAME_POWER;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public long updatePowerupOwned(String powerupName, int isOwned) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_POWER_OWNED, isOwned);

        long result = db.update(TABLE_NAME_POWER, cv, COLUMN_POWER_NAME + " = ?", new String[]{powerupName});

        return result;
    }

    public long updatePowerUpActive(String powerupName, int isSelected) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_POWER_SELECTED, isSelected);

        long result = db.update(TABLE_NAME_POWER, cv, COLUMN_POWER_NAME + " = ?", new String[]{powerupName});

        return result;
    }

    public void resetPowerupsActive() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME_POWER + " SET " + COLUMN_POWER_SELECTED + " = 0";
        db.execSQL(query);
    }

    public Cursor readHistory() {
        String query = "SELECT * FROM " + TABLE_NAME_HISTORY;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public long addHistory(String date, int score, String time) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_HISTORY_DATE, date);
        cv.put(COLUMN_HISTORY_SCORE, score);
        cv.put(COLUMN_HISTORY_TIME, time);

        long result = db.insert(TABLE_NAME_HISTORY, null, cv);

        return result;
    }

    public Cursor readStats() {
        String query = "SELECT * FROM " + TABLE_NAME_STATS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public long updateBalance(String rowId, int balance) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_STATS_BALANCE, balance);

        long result = db.update(TABLE_NAME_STATS, cv, "_id = ?", new String[]{rowId});

        return result;
    }

    public long updateStats(String rowId, int highest) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_STATS_HIGHEST, highest);

        long result = db.update(TABLE_NAME_STATS, cv, "_id = ?", new String[]{rowId});

        return result;
    }

    public Cursor readAchievements() {
        String query = "SELECT * FROM " + TABLE_NAME_ACHIE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public Cursor readOneAchievement(String name) {
        String query = "SELECT * FROM " + TABLE_NAME_ACHIE + " WHERE " + COLUMN_ACHIE_NAME + " = '" + name + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public long updateAchievements(String name, int achieved) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ACHIE_NAME, name);
        cv.put(COLUMN_ACHIE_ACHIEVED, achieved);

        long result = db.update(TABLE_NAME_ACHIE, cv, COLUMN_ACHIE_NAME + " = ?", new String[]{name});

        return result;
    }

    public long updateSettings(String rowId, int music, int sfx) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SETTINGS_MUSIC, music);
        cv.put(COLUMN_SETTINGS_SFX, sfx);

        long result = db.update(TABLE_NAME_SETTINGS, cv, "_id = ?", new String[]{rowId});

        return result;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String upgrade_query = "DROP TABLE IF EXISTS " + TABLE_NAME_POWER;
        db.execSQL(upgrade_query);
    }
}
