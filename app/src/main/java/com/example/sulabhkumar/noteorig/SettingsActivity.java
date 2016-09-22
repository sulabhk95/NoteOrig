package com.example.sulabhkumar.noteorig;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import com.example.sulabhkumar.noteorig.Settings.SettingsAdapater;
import com.example.sulabhkumar.noteorig.Settings.SettingsDatabaseHelper;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener;
import fr.ganfra.materialspinner.BuildConfig;
import java.util.Calendar;

public class SettingsActivity extends ListActivity implements OnTimeSetListener {
    SettingsAdapater adapater;
    SettingsDatabaseHelper databaseHelper;
    Calendar mCalendar;
    int posSelected;

    /* renamed from: com.example.sulabhkumar.testing.SettingsActivity.1 */
    class C01971 implements OnCancelListener {
        C01971() {
        }

        public void onCancel(DialogInterface dialogInterface) {
            Log.d("TimePicker", "Dialog was cancelled");
        }
    }

    public SettingsActivity() {
        this.mCalendar = Calendar.getInstance();
        this.posSelected = 1;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.databaseHelper = new SettingsDatabaseHelper(this);
        registerForContextMenu(getListView());
        this.adapater = new SettingsAdapater(this, this.databaseHelper.getAllRecords());
        setListAdapter(this.adapater);
    }

    public void refreshDisplay() {
        this.adapater.changeCursor(this.databaseHelper.getAllRecords());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(this, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false);
        tpd.setThemeDark(false);
        tpd.vibrate(true);
        tpd.dismissOnPause(false);
        tpd.enableSeconds(true);
        tpd.setAccentColor(Color.parseColor("#2AB769"));
        tpd.setOnCancelListener(new C01971());
        tpd.show(getFragmentManager(), "Timepickerdialog");
        this.posSelected = position + 1;
    }

    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : BuildConfig.VERSION_NAME + hourOfDay;
        String time = "You picked the following time: " + hourString + "h" + (minute < 10 ? "0" + minute : BuildConfig.VERSION_NAME + minute) + "m" + (second < 10 ? "0" + second : BuildConfig.VERSION_NAME + second) + "s";
        this.mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        this.mCalendar.set(Calendar.MINUTE, minute);
        this.mCalendar.set(Calendar.SECOND , second);
        this.mCalendar.get(Calendar.HOUR_OF_DAY);
        this.databaseHelper.updateRecord(this.mCalendar.get(Calendar.HOUR_OF_DAY), this.mCalendar.get(Calendar.MINUTE), this.mCalendar.get(Calendar.SECOND ), this.posSelected);
        refreshDisplay();
    }
}
