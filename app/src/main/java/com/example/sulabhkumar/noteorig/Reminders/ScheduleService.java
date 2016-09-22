package com.example.sulabhkumar.noteorig.Reminders;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import com.example.sulabhkumar.noteorig.Settings.SettingsDatabaseHelper;
import java.util.Calendar;

public class ScheduleService extends Service {
    private final IBinder mBinder;
    private String mTitle;

    public class ServiceBinder extends Binder {
        ScheduleService getService() {
            return ScheduleService.this;
        }
    }

    public ScheduleService() {
        this.mBinder = new ServiceBinder();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("ScheduleService", "Received start id " + startId + ": " + intent);
        return 1;
    }

    public IBinder onBind(Intent intent) {
        this.mTitle = intent.getStringExtra(SettingsDatabaseHelper.SETTINGS_COLUMN_TITLE);
        return this.mBinder;
    }

    public void setAlarm(Calendar c) {
        new AlarmTask(this, c, this.mTitle).run();
    }
}
