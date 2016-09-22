package com.example.sulabhkumar.noteorig.Settings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SettingsDatabaseHelper {
    public static final String SETTINGS_COLUMN_ID = "_id";
    public static final String SETTINGS_COLUMN_TIME_HOD = "time_hod";
    public static final String SETTINGS_COLUMN_TIME_MIN = "time_min";
    public static final String SETTINGS_COLUMN_TIME_SEC = "time_sec";
    public static final String SETTINGS_COLUMN_TITLE = "title";
    public static final String SETTINGS_DATABASE_NAME = "settings.db";
    public static final int SETTINGS_DATABASE_VERSION = 2;
    public static final String SETTINGS_TABLE_NAME = "TimeDefaultsTable";
    Context context;
    private SQLiteDatabase database;
    private SettingsDataBaseOpenHelper openHelper;

    public SettingsDatabaseHelper(Context context) {
        this.context = context;
        this.openHelper = new SettingsDataBaseOpenHelper(context);
        this.database = this.openHelper.getWritableDatabase();
    }

    public void updateRecord(int hourOfDay, int minute, int second, int id) {
        String hod = Integer.toString(hourOfDay);
        String min = Integer.toString(minute);
        String sec = Integer.toString(second);
        ContentValues contentValues = new ContentValues();
        contentValues.put(SETTINGS_COLUMN_TIME_HOD, hod);
        contentValues.put(SETTINGS_COLUMN_TIME_MIN, min);
        contentValues.put(SETTINGS_COLUMN_TIME_SEC, sec);
        this.database.update(SETTINGS_TABLE_NAME, contentValues, "_id=?", new String[]{Integer.toString(id)});
    }

    public int getHourOfDay(int id) {
        String s = "8";
        Cursor cursor = this.database.rawQuery("SELECT  * from TimeDefaultsTable where _id = " + id, null);
        if (cursor != null && cursor.moveToFirst()) {
            s = cursor.getString(cursor.getColumnIndex(SETTINGS_COLUMN_TIME_HOD));
        }
        return Integer.parseInt(s);
    }

    public int getMinute(int id) {
        String s = "8";
        Cursor cursor = this.database.rawQuery("SELECT  * from TimeDefaultsTable where _id=" + id, null);
        if (cursor != null && cursor.moveToFirst()) {
            s = cursor.getString(cursor.getColumnIndex(SETTINGS_COLUMN_TIME_MIN));
        }
        return Integer.parseInt(s);
    }

    public int getSecond(int id) {
        String s = "8";
        Cursor cursor = this.database.rawQuery("SELECT  * from TimeDefaultsTable where _id = " + id, null);
        if (cursor != null && cursor.moveToFirst()) {
            s = cursor.getString(cursor.getColumnIndex(SETTINGS_COLUMN_TIME_SEC));
        }
        return Integer.parseInt(s);
    }

    public Cursor getAllRecords() {
        return this.database.rawQuery("select * from TimeDefaultsTable", null);
    }
}
