package com.example.sulabhkumar.noteorig.Reminders;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.sulabhkumar.noteorig.Settings.SettingsDatabaseHelper;

public class AlarmAdapater extends CursorAdapter {
    private static final int EDIT_ENTRY_REQUEST_CODE = 1;
    Context context;
    ListView listView;

    public AlarmAdapater(Activity context, Cursor cursor) {
        super(context, cursor);
        this.context = context;
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(17367043, parent, false);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView) view.findViewById(16908308)).setText(cursor.getString(cursor.getColumnIndex(SettingsDatabaseHelper.SETTINGS_COLUMN_TITLE)));
    }
}
