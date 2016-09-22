package com.example.sulabhkumar.noteorig;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Sulabh Kumar on 3/24/2016.
 */
public class NoteItemDatabaseHelper {
    public static final String DATABASE_NAME = "notes.db";
    public static final int DATABASE_VERSION = 5;
    public static final String NOTEITEM_COLUMN_COLOR = "color";
    public static final String NOTEITEM_COLUMN_DATE = "date";
    public static final String NOTEITEM_COLUMN_ID = "_id";
    public static final String NOTEITEM_COLUMN_TEXT = "text";
    public static final String NOTEITEM_COLUMN_TIME = "time";
    public static final String NOTEITEM_COLUMN_TITLE = "title";
    public static final String NOTEITEM_COLUMN_TYPE = "type";
    public static final String TABLE_NAME = "noteItems";
    Context context;
    private SQLiteDatabase database;
    private NoteItemOpenHelper openHelper;

    public NoteItemDatabaseHelper(Context context) {
        this.context = context;
        this.openHelper = new NoteItemOpenHelper(context);
        this.database = this.openHelper.getWritableDatabase();
    }

    public void saveTimeRecord(String title, String text, String type, String color) {
        Locale.setDefault(new Locale("en_US"));
        SimpleDateFormat formatter = new SimpleDateFormat();
        Date date = new Date();
        formatter.applyPattern("EEE dd MMM");
        String d = formatter.format(date);
        formatter.applyPattern("hh:mm a");
        String time = formatter.format(date);
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTEITEM_COLUMN_TITLE, title);
        contentValues.put(NOTEITEM_COLUMN_TEXT, text);
        contentValues.put(NOTEITEM_COLUMN_TYPE, type);
        contentValues.put(NOTEITEM_COLUMN_DATE, d);
        contentValues.put(NOTEITEM_COLUMN_TIME, time);
        contentValues.put(NOTEITEM_COLUMN_COLOR, color);
        this.database.insert(TABLE_NAME, null, contentValues);
    }

    public void deleteRecord(int key) {
        this.database.execSQL("delete from noteItems where _id=" + key);
    }

    public void updateRecord(String title, String text, int id, String type, String color) {
        Locale.setDefault(new Locale("en_US"));
        SimpleDateFormat formatter = new SimpleDateFormat();
        Date date = new Date();
        formatter.applyPattern("EEE dd MMM");
        String d = formatter.format(date);
        formatter.applyPattern("hh:mm a");
        String time = formatter.format(date);
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTEITEM_COLUMN_TITLE, title);
        contentValues.put(NOTEITEM_COLUMN_TEXT, text);
        contentValues.put(NOTEITEM_COLUMN_TYPE, type);
        contentValues.put(NOTEITEM_COLUMN_DATE, d);
        contentValues.put(NOTEITEM_COLUMN_TIME, time);
        contentValues.put(NOTEITEM_COLUMN_COLOR, color);
        this.database.update(TABLE_NAME, contentValues, "_id=?", new String[]{Integer.toString(id)});
    }

    public Cursor getAllRecords() {
        return this.database.rawQuery("select * from noteItems", null);
    }

    public Cursor getRecordsFromType(String type) {
        return this.database.rawQuery("select * from noteItems where type=\"" + type + "\"", null);
    }
}
