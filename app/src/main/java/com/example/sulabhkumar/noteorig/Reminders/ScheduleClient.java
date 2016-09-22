package com.example.sulabhkumar.noteorig.Reminders;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.example.sulabhkumar.noteorig.Reminders.ScheduleService.ServiceBinder;
import com.example.sulabhkumar.noteorig.Settings.SettingsDatabaseHelper;
import java.util.Calendar;

public class ScheduleClient {
    private ScheduleService mBoundService;
    private ServiceConnection mConnection;
    private Context mContext;
    private boolean mIsBound;
    private String mtitle;

    /* renamed from: com.example.sulabhkumar.testing.Reminders.ScheduleClient.1 */
    class C01961 implements ServiceConnection {
        C01961() {
        }

        public void onServiceConnected(ComponentName className, IBinder service) {
            ScheduleClient.this.mBoundService = ((ServiceBinder) service).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            ScheduleClient.this.mBoundService = null;
        }
    }

    public ScheduleClient(Context context, String title) {
        this.mConnection = new C01961();
        this.mContext = context;
        this.mtitle = title;
    }

    public void doBindService() {
        Intent intent = new Intent(this.mContext, ScheduleService.class);
        intent.putExtra(SettingsDatabaseHelper.SETTINGS_COLUMN_TITLE, this.mtitle);
        this.mContext.bindService(intent, this.mConnection, 1);
        this.mIsBound = true;
    }

    public void setAlarmForNotification(Calendar c) {
        this.mBoundService.setAlarm(c);
    }

    public void doUnbindService() {
        if (this.mIsBound) {
            this.mContext.unbindService(this.mConnection);
            this.mIsBound = false;
        }
    }
}
