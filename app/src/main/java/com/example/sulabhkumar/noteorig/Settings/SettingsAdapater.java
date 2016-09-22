package com.example.sulabhkumar.noteorig.Settings;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.sulabhkumar.noteorig.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SettingsAdapater extends CursorAdapter {
    private static final int EDIT_ENTRY_REQUEST_CODE = 1;
    Context context;
    ListView listView;

    public SettingsAdapater(Activity context, Cursor cursor) {
        super(context, cursor);
        this.context = context;
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_reminder_list_item, parent, false);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        TextView timeView = (TextView) view.findViewById(R.id.settings_reminder_time);
        ((TextView) view.findViewById(R.id.settings_reminder_title)).setText(cursor.getString(cursor.getColumnIndex(SettingsDatabaseHelper.SETTINGS_COLUMN_TITLE)));
        SimpleDateFormat formatDate = new SimpleDateFormat("h:mm a");
        Calendar calendar = Calendar.getInstance();
        String hourString = cursor.getString(cursor.getColumnIndex(SettingsDatabaseHelper.SETTINGS_COLUMN_TIME_HOD));
        String minuteString = cursor.getString(cursor.getColumnIndex(SettingsDatabaseHelper.SETTINGS_COLUMN_TIME_MIN));
        String secondString = cursor.getString(cursor.getColumnIndex(SettingsDatabaseHelper.SETTINGS_COLUMN_TIME_SEC));
        calendar.set(11, Integer.parseInt(hourString));
        calendar.set(12, Integer.parseInt(minuteString));
        calendar.set(13, Integer.parseInt(secondString));
        timeView.setText(formatDate.format(calendar.getTime()));
    }
}
