package com.example.sulabhkumar.noteorig.Reminders;

import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import com.example.sulabhkumar.noteorig.R;
import com.example.sulabhkumar.noteorig.MainActivity;
import com.example.sulabhkumar.noteorig.Settings.SettingsDatabaseHelper;

public class NotifyService extends Service {
    public static final String INTENT_NOTIFY = "com.example.sulabhkumar.testing.service.INTENT_NOTIFY";
    private static final int NOTIFICATION = 123;
    AlarmDatabaseHelper databaseHelper;
    private final IBinder mBinder;
    private NotificationManager mNM;
    private String mTitle;

    public class ServiceBinder extends Binder {
        NotifyService getService() {
            return NotifyService.this;
        }
    }

    public NotifyService() {
        this.mBinder = new ServiceBinder();
    }

    public void onCreate() {
        Log.i("NotifyService", "onCreate()");
        this.mNM = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        this.mTitle = intent.getStringExtra(SettingsDatabaseHelper.SETTINGS_COLUMN_TITLE);
        if (intent.getBooleanExtra(INTENT_NOTIFY, false)) {
            this.databaseHelper = new AlarmDatabaseHelper(getApplicationContext());
            this.databaseHelper.deleteRecord(this.mTitle);
            showNotification();
        }
        return 2;
    }

    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    private void showNotification() {
        CharSequence title = this.mTitle;
        CharSequence text = "Reminder";
        long time = System.currentTimeMillis();
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        Builder builder = new Builder(this);
        builder.setAutoCancel(false);
        builder.setContentTitle(title);
        builder.setSmallIcon(R.drawable.btn_stat_notify_icon2);
        builder.setContentIntent(contentIntent);
        builder.setOngoing(false);
        builder.setSound(RingtoneManager.getDefaultUri(2));
        this.mNM.notify(NOTIFICATION, builder.build());
        stopSelf();
    }
}
