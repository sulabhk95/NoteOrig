package com.example.sulabhkumar.noteorig;

import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import com.example.sulabhkumar.noteorig.Reminders.AlarmAdapater;
import com.example.sulabhkumar.noteorig.Reminders.AlarmDatabaseHelper;
import com.example.sulabhkumar.noteorig.Reminders.NotifyService;
import com.example.sulabhkumar.noteorig.Settings.SettingsDatabaseHelper;

public class EditRemindersActivity extends ListActivity {
    private static final int MENU_DELETE_ID = 1002;
    AlarmAdapater adapater;
    private int currNoteId;
    AlarmDatabaseHelper databaseHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reminders);
        this.databaseHelper = new AlarmDatabaseHelper(this);
        registerForContextMenu(getListView());
        this.adapater = new AlarmAdapater(this, this.databaseHelper.getAllRecords());
        setListAdapter(this.adapater);
    }

    public void refreshDisplay() {
        this.adapater.changeCursor(this.databaseHelper.getAllRecords());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_reminders, menu);
        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        this.currNoteId = ((AdapterContextMenuInfo) menuInfo).position;
        menu.add(0, MENU_DELETE_ID, 0, "Delete");
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == MENU_DELETE_ID) {
            Cursor c = this.adapater.getCursor();
            c.moveToPosition(this.currNoteId);
            delete(c.getString(c.getColumnIndex(SettingsDatabaseHelper.SETTINGS_COLUMN_TITLE)));
        }
        return super.onContextItemSelected(item);
    }

    public void delete(String title) {
        this.databaseHelper.deleteRecord(title);
        AlarmManager am = (AlarmManager) getSystemService(this.ALARM_SERVICE);
        Intent intent = new Intent(this, NotifyService.class);
        intent.putExtra(NotifyService.INTENT_NOTIFY, true);
        intent.putExtra(SettingsDatabaseHelper.SETTINGS_COLUMN_TITLE, title);
        intent.setData(Uri.parse("custom://" + title));
        am.cancel(PendingIntent.getService(this, 0, intent, 0));
        refreshDisplay();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
