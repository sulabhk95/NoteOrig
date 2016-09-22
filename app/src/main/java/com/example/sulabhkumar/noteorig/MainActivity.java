package com.example.sulabhkumar.noteorig;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sulabhkumar.noteorig.Settings.SettingsDatabaseHelper;

import static com.example.sulabhkumar.noteorig.NoteItemDatabaseHelper.*;
import static com.example.sulabhkumar.noteorig.NoteItemDatabaseHelper.NOTEITEM_COLUMN_DATE;
import static com.example.sulabhkumar.noteorig.NoteItemDatabaseHelper.NOTEITEM_COLUMN_ID;
import static com.example.sulabhkumar.noteorig.NoteItemDatabaseHelper.NOTEITEM_COLUMN_TEXT;
import static com.example.sulabhkumar.noteorig.NoteItemDatabaseHelper.NOTEITEM_COLUMN_TIME;
import static com.example.sulabhkumar.noteorig.NoteItemDatabaseHelper.NOTEITEM_COLUMN_TITLE;

public class MainActivity extends ListActivity {
    public static final int ADD_ENTRY_REQUEST_CODE = 1;
    private static final int EDIT_ENTRY_REQUEST_CODE = 2;
    NoteItemDatabaseHelper databaseHelper;
    private ArrayAdapter<String> mAdapter;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    NoteItemAdapater noteItemAdapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.databaseHelper = new NoteItemDatabaseHelper(this);
        this.noteItemAdapater = new NoteItemAdapater(this, this.databaseHelper.getAllRecords());
        setListAdapter(this.noteItemAdapater);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_ENTRY_REQUEST_CODE && resultCode == -1) {
            this.databaseHelper.saveTimeRecord(data.getStringExtra(SettingsDatabaseHelper.SETTINGS_COLUMN_TITLE), data.getStringExtra(NoteItemDatabaseHelper.NOTEITEM_COLUMN_TEXT), data.getStringExtra(NoteItemDatabaseHelper.NOTEITEM_COLUMN_TYPE), data.getStringExtra(NoteItemDatabaseHelper.NOTEITEM_COLUMN_COLOR));
            this.noteItemAdapater.changeCursor(this.databaseHelper.getAllRecords());
        }
        if (requestCode == EDIT_ENTRY_REQUEST_CODE && resultCode == -1) {
            String time = data.getStringExtra(SettingsDatabaseHelper.SETTINGS_COLUMN_TITLE);
            String notes = data.getStringExtra(NoteItemDatabaseHelper.NOTEITEM_COLUMN_TEXT);
            String type = data.getStringExtra(NoteItemDatabaseHelper.NOTEITEM_COLUMN_TYPE);
            String color = data.getStringExtra(NoteItemDatabaseHelper.NOTEITEM_COLUMN_COLOR);
            this.databaseHelper.updateRecord(time, notes, Integer.parseInt(data.getStringExtra("id")), type, color);
            this.noteItemAdapater.changeCursor(this.databaseHelper.getAllRecords());
        }
    }

    public void editButtonPressed(int key, Cursor cursor) {
        Intent intent = new Intent(this, EditItemActivity.class);
        intent.putExtra(SettingsDatabaseHelper.SETTINGS_COLUMN_TITLE, cursor.getString(cursor.getColumnIndex(SettingsDatabaseHelper.SETTINGS_COLUMN_TITLE)));
        intent.putExtra(NoteItemDatabaseHelper.NOTEITEM_COLUMN_TEXT, cursor.getString(cursor.getColumnIndex(NoteItemDatabaseHelper.NOTEITEM_COLUMN_TEXT)));
        intent.putExtra(NoteItemDatabaseHelper.NOTEITEM_COLUMN_DATE, cursor.getString(cursor.getColumnIndex(NoteItemDatabaseHelper.NOTEITEM_COLUMN_DATE)));
        intent.putExtra(NoteItemDatabaseHelper.NOTEITEM_COLUMN_TIME, cursor.getString(cursor.getColumnIndex(NoteItemDatabaseHelper.NOTEITEM_COLUMN_TIME)));
        intent.putExtra("id", cursor.getString(cursor.getColumnIndex(SettingsDatabaseHelper.SETTINGS_COLUMN_ID)));
        startActivityForResult(intent, EDIT_ENTRY_REQUEST_CODE);
    }

    public void reminderButtonPressed(String title) {
        Intent intent = new Intent(this, ReminderActivity.class);
        intent.putExtra(SettingsDatabaseHelper.SETTINGS_COLUMN_TITLE, title);
        startActivity(intent);
    }

    public void addButtonPressed(View v) {
        startActivityForResult(new Intent(this, AddItemActivity.class), ADD_ENTRY_REQUEST_CODE);
    }

    public void toDoButtonPressed(View v) {
        this.noteItemAdapater.changeCursor(this.databaseHelper.getRecordsFromType("To Do"));
        getListView().startLayoutAnimation();
    }

    public void reminderButtonPressed(View v) {
        this.noteItemAdapater.changeCursor(this.databaseHelper.getRecordsFromType("Reminder"));
        getListView().startLayoutAnimation();
    }

    public void notesButtonPressed(View v) {
        this.noteItemAdapater.changeCursor(this.databaseHelper.getRecordsFromType("Note"));
        getListView().startLayoutAnimation();
    }

    public void listButtonPressed(View v) {
        this.noteItemAdapater.changeCursor(this.databaseHelper.getRecordsFromType("List"));
        getListView().startLayoutAnimation();
    }

    public void allButtonPressed(View v) {
        this.noteItemAdapater.changeCursor(this.databaseHelper.getAllRecords());
        getListView().startLayoutAnimation();
    }

    public void navButtonListener(View v) {
        this.mDrawerLayout.openDrawer(3);
    }

    public void editRemindersPressed(View v) {
        startActivity(new Intent(this, EditRemindersActivity.class));
    }

    public void searchButtonPressed(View v) {
        Toast.makeText(this, "Functionality coming in next update...", Toast.LENGTH_LONG).show();
    }

    public void aboutPressed(View v) {
        startActivity(new Intent(this, AboutScreenActivity.class));
    }

    public void settingsPressed(View v) {startActivity(new Intent(this, SettingsActivity.class));
    }
}
