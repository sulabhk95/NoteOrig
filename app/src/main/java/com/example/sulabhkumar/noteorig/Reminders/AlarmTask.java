package com.example.sulabhkumar.noteorig.Reminders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.example.sulabhkumar.noteorig.Settings.SettingsDatabaseHelper;
import java.util.Calendar;

public class AlarmTask implements Runnable {
    private final AlarmManager am;
    private final Context context;
    private final Calendar date;
    private String title;

    public AlarmTask(Context context, Calendar date, String title) {
        this.context = context;
        this.am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        this.date = date;
        this.title = title;
    }

    public void run() {
        Intent intent = new Intent(this.context, NotifyService.class);
        intent.putExtra(NotifyService.INTENT_NOTIFY, true);
        intent.putExtra(SettingsDatabaseHelper.SETTINGS_COLUMN_TITLE, this.title);
        intent.setData(Uri.parse("custom://" + this.title));
        this.am.set(0, this.date.getTimeInMillis(), PendingIntent.getService(this.context, 0, intent, 0));
    }
}
