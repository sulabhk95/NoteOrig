package com.example.sulabhkumar.noteorig.Reminders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AlarmDatabaseHelper {
    public static final String ALARM_COLUMN_ID = "_id";
    public static final String ALARM_COLUMN_TITLE = "title";
    public static final String ALARM_DATABASE_NAME = "alarms.db";
    public static final int ALARM_DATABASE_VERSION = 1;
    public static final String ALARM_TABLE_NAME = "alarmTable";
    Context context;
    private SQLiteDatabase database;
    private AlarmDataBaseOpenHelper openHelper;

    public AlarmDatabaseHelper(Context context) {
        this.context = context;
        this.openHelper = new AlarmDataBaseOpenHelper(context);
        this.database = this.openHelper.getWritableDatabase();
    }

    public void saveAlarmRecord(String title) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ALARM_COLUMN_TITLE, title);
        this.database.insert(ALARM_TABLE_NAME, null, contentValues);
    }

    public void deleteRecord(String title) {
        this.database.execSQL("delete from alarmTable where title=\"" + title + "\"");
    }

    public Cursor getAllRecords() {
        return this.database.rawQuery("select * from alarmTable", null);
    }
}
