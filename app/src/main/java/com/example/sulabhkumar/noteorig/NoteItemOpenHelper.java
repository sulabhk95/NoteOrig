package com.example.sulabhkumar.noteorig;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.sulabhkumar.noteorig.NoteItemDatabaseHelper.*;
import static com.example.sulabhkumar.noteorig.NoteItemDatabaseHelper.DATABASE_NAME;
import static com.example.sulabhkumar.noteorig.NoteItemDatabaseHelper.DATABASE_VERSION;
import static com.example.sulabhkumar.noteorig.NoteItemDatabaseHelper.NOTEITEM_COLUMN_DATE;
import static com.example.sulabhkumar.noteorig.NoteItemDatabaseHelper.NOTEITEM_COLUMN_ID;
import static com.example.sulabhkumar.noteorig.NoteItemDatabaseHelper.NOTEITEM_COLUMN_TEXT;
import static com.example.sulabhkumar.noteorig.NoteItemDatabaseHelper.NOTEITEM_COLUMN_TIME;
import static com.example.sulabhkumar.noteorig.NoteItemDatabaseHelper.NOTEITEM_COLUMN_TITLE;
import static com.example.sulabhkumar.noteorig.NoteItemDatabaseHelper.NOTEITEM_COLUMN_TYPE;
import static com.example.sulabhkumar.noteorig.NoteItemDatabaseHelper.TABLE_NAME;

/**
 * Created by Sulabh Kumar on 3/24/2016.
 */
public class NoteItemOpenHelper extends SQLiteOpenHelper {
    NoteItemOpenHelper(Context context) {
        super(context, NoteItemDatabaseHelper.DATABASE_NAME, null, 5);
    }

    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE noteItems( _id INTEGER PRIMARY KEY, title TEXT, text TEXT, date TEXT, time TEXT, color TEXT,type TEXT) ");
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS noteItems");
        onCreate(database);
    }
}
