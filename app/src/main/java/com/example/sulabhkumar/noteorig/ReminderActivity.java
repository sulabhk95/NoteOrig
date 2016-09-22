package com.example.sulabhkumar.noteorig;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sulabhkumar.noteorig.Reminders.AlarmDatabaseHelper;
import com.example.sulabhkumar.noteorig.Reminders.ScheduleClient;
import com.example.sulabhkumar.noteorig.Settings.SettingsDatabaseHelper;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener;
import fr.ganfra.materialspinner.BuildConfig;
import java.util.Calendar;

public class ReminderActivity extends Activity implements

        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener
{
    AlarmDatabaseHelper databaseHelper;
    private TextView dateTextView;
    AdapterView.OnItemSelectedListener listenerDate;
    AdapterView.OnItemSelectedListener listenerTime;
    Calendar mCalendar;
    private ScheduleClient scheduleClient;
    SettingsDatabaseHelper settingsDatabaseHelper;
    private TextView timeTextView;




    class C01941 implements AdapterView.OnItemSelectedListener {

        /* renamed from: com.example.sulabhkumar.testing.ReminderActivity.1.1 */
        class C01931 implements OnCancelListener {
            C01931() {
            }

            public void onCancel(DialogInterface dialogInterface) {
                Log.d("TimePicker", "Dialog was cancelled");
            }
        }

        C01941() {
        }

        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String item = parent.getItemAtPosition(position).toString();
            int num = 1;
            if (item.equals("Morning")) {
                num = 1;
            }
            if (item.equals("AfterNoon")) {
                num = 2;
            }
            if (item.equals("Evening")) {
                Toast.makeText(ReminderActivity.this.getApplicationContext(), Integer.toString(ReminderActivity.this.settingsDatabaseHelper.getHourOfDay(3)), 1).show();
                num = 3;
            }
            int hourOfDay = ReminderActivity.this.settingsDatabaseHelper.getHourOfDay(num);
            int minute = ReminderActivity.this.settingsDatabaseHelper.getMinute(num);
            int second = ReminderActivity.this.settingsDatabaseHelper.getSecond(num);
            ReminderActivity.this.mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            ReminderActivity.this.mCalendar.set(Calendar.MINUTE, minute);
            ReminderActivity.this.mCalendar.set(Calendar.SECOND, second);
            if (item.equals("Pick...")) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(ReminderActivity.this, now.get(11), now.get(12), false);
                tpd.setThemeDark(false);
                tpd.vibrate(true);
                tpd.dismissOnPause(false);
                tpd.enableSeconds(true);
                tpd.setAccentColor(Color.parseColor("#2AB769"));
                tpd.setOnCancelListener(new C01931());
                tpd.show(ReminderActivity.this.getFragmentManager(), "Timepickerdialog");
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: com.example.sulabhkumar.testing.ReminderActivity.2 */
    class C01952 implements AdapterView.OnItemSelectedListener {
        C01952() {
        }

        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String item = parent.getItemAtPosition(position).toString();
            if (item.equals("Today")) {
            }
            if (item.equals("Tomorrow")) {
                ReminderActivity.this.mCalendar.add(Calendar.DAY_OF_MONTH, 1);
                Toast.makeText(ReminderActivity.this, Integer.toString(ReminderActivity.this.mCalendar.get(5)), 1).show();
            }
            if (item.equals("Next Week")) {
                ReminderActivity.this.mCalendar.add(Calendar.DAY_OF_MONTH, 7);
            }
            if (item.equals("Pick...")) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(ReminderActivity.this, now.get(1), now.get(2), now.get(5));
                dpd.setThemeDark(false);
                dpd.vibrate(true);
                dpd.dismissOnPause(false);
                dpd.showYearPickerFirst(false);
                dpd.setAccentColor(Color.parseColor("#2AB769"));
                dpd.setMinDate(now);
                dpd.show(ReminderActivity.this.getFragmentManager(), "Datepickerdialog");
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public ReminderActivity() {
        this.listenerTime = new C01941();
        this.listenerDate = new C01952();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        requestWindowFeature(1);
        setContentView(R.layout.activity_reminder);
        this.settingsDatabaseHelper = new SettingsDatabaseHelper(this);
        this.databaseHelper = new AlarmDatabaseHelper(this);
        this.scheduleClient = new ScheduleClient(this, getIntent().getStringExtra(SettingsDatabaseHelper.SETTINGS_COLUMN_TITLE));
        this.scheduleClient.doBindService();
        Spinner dropdown = (Spinner) findViewById(R.id.spinnerDate);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new String[]{"Today", "Tomorrow", "Next Week", "Pick..."});
        dataAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(dataAdapter);
        dropdown.setOnItemSelectedListener(this.listenerDate);
        Spinner dropdown2 = (Spinner) findViewById(R.id.spinnerTime);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new String[]{"Morning", "Afternoon", "Evening", "Pick..."});
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown2.setAdapter(dataAdapter2);
        dropdown2.setOnItemSelectedListener(this.listenerTime);
        this.mCalendar = Calendar.getInstance();
    }

    public void onDateSet(DatePickerDialog datepickerdialog, int i, int j, int k)
    {
        mCalendar.set(i, j, k);
    }

    public void onResume()
    {
        super.onResume();
        TimePickerDialog timepickerdialog = (TimePickerDialog)getFragmentManager().findFragmentByTag("Timepickerdialog");
        DatePickerDialog datepickerdialog = (DatePickerDialog)getFragmentManager().findFragmentByTag("Datepickerdialog");
        if (timepickerdialog != null)
        {
            timepickerdialog.setOnTimeSetListener(this);
        }
        if (datepickerdialog != null)
        {
            datepickerdialog.setOnDateSetListener(this);
        }
    }

    public void onSetReminderPressed(View view)
    {
        scheduleClient.setAlarmForNotification(mCalendar);
        Toast.makeText(this, "Reminder Set", Toast.LENGTH_LONG).show();
        databaseHelper.saveAlarmRecord(getIntent().getStringExtra("title"));
        finish();
    }

    protected void onStop()
    {
        if (scheduleClient != null)
        {
            scheduleClient.doUnbindService();
        }
        super.onStop();
    }

    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : BuildConfig.VERSION_NAME + hourOfDay;
        String time = "You picked the following time: " + hourString + "h" + (minute < 10 ? "0" + minute : BuildConfig.VERSION_NAME + minute) + "m" + (second < 10 ? "0" + second : BuildConfig.VERSION_NAME + second) + "s";
        this.mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        this.mCalendar.set(Calendar.MINUTE, minute);
        this.mCalendar.set(Calendar.SECOND, second);
    }
}