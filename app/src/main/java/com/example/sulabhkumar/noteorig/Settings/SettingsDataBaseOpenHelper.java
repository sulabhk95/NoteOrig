package com.example.sulabhkumar.noteorig.Settings;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SettingsDataBaseOpenHelper extends SQLiteOpenHelper {
    SettingsDataBaseOpenHelper(Context context) {
        super(context, SettingsDatabaseHelper.SETTINGS_DATABASE_NAME, null, 2);
    }

    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE TimeDefaultsTable( _id INTEGER PRIMARY KEY, title TEXT, time_hod TEXT, time_min TEXT, time_sec TEXT) ");
        database.execSQL("INSERT INTO TimeDefaultsTable VALUES (1,\"Morning\",\"8\",\"0\",\"0\"   ) ");
        database.execSQL("INSERT INTO TimeDefaultsTable VALUES (2,\"Afternoon\",\"13\",\"0\",\"0\"   ) ");
        database.execSQL("INSERT INTO TimeDefaultsTable VALUES (3,\"Evening\",\"18\",\"0\",\"0\"   ) ");
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS TimeDefaultsTable");
        onCreate(database);
    }
}
