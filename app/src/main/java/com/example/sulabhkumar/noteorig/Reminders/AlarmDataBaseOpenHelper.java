package com.example.sulabhkumar.noteorig.Reminders;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlarmDataBaseOpenHelper extends SQLiteOpenHelper {
    AlarmDataBaseOpenHelper(Context context) {
        super(context, AlarmDatabaseHelper.ALARM_DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE alarmTable( _id INTEGER PRIMARY KEY, title TEXT) ");
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS alarmTable");
        onCreate(database);
    }
}
