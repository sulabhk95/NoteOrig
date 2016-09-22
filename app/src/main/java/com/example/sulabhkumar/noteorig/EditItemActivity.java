package com.example.sulabhkumar.noteorig;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sulabhkumar.noteorig.ColorPicker.ColorPickerDialog;
import com.example.sulabhkumar.noteorig.ColorPicker.ColorPickerSwatch;
import com.example.sulabhkumar.noteorig.Settings.SettingsDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Sulabh Kumar on 3/24/2016.
 */
public class EditItemActivity extends Activity {
    String dropDownItem;
    int mSelectedColorCal0;
    int[] mColor;
    AdapterView.OnItemSelectedListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_list_item);
        dropDownItem = "Note";
        mSelectedColorCal0 = Color.parseColor("#99cc00");
        mColor = colorChoice(EditItemActivity.this);
        Locale locale = new Locale("en_US");
        Locale.setDefault(locale);
        String pattern3 = "hh:mm a";
        SimpleDateFormat formatter = new SimpleDateFormat();
        Date date = new Date();
        formatter.applyPattern(pattern3);
        ((TextView)findViewById(R.id.add_item_time)).setText(formatter.format(date));
        Spinner dropdown = (Spinner) findViewById(R.id.spinner);
        String[] items = new String[]{"Note", "Reminder", "To Do","List"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(dataAdapter);
        dropdown.setOnItemSelectedListener(listener);
        EditText titleView = (EditText) findViewById(R.id.add_item_et_title);
        titleView.getBackground().mutate().setColorFilter(getResources().getColor(R.color.base), PorterDuff.Mode.SRC_ATOP);
        EditText textView = (EditText)findViewById(R.id.add_item_et_text);
        textView.getBackground().mutate().setColorFilter(getResources().getColor(R.color.base), PorterDuff.Mode.SRC_ATOP);
        Intent intent = getIntent();
        titleView.setText(intent.getStringExtra(SettingsDatabaseHelper.SETTINGS_COLUMN_TITLE));
        textView.setText(intent.getStringExtra(NoteItemDatabaseHelper.NOTEITEM_COLUMN_TEXT));
    }
    public void onSave(View view) {
        Intent intent = getIntent();
        EditText textView = (EditText) findViewById(R.id.add_item_et_text);
        intent.putExtra(SettingsDatabaseHelper.SETTINGS_COLUMN_TITLE, ((EditText) findViewById(R.id.add_item_et_title)).getText().toString());
        intent.putExtra(NoteItemDatabaseHelper.NOTEITEM_COLUMN_TEXT, textView.getText().toString());
        intent.putExtra(NoteItemDatabaseHelper.NOTEITEM_COLUMN_TYPE, this.dropDownItem);
        intent.putExtra(NoteItemDatabaseHelper.NOTEITEM_COLUMN_COLOR, Integer.toString(this.mSelectedColorCal0));
        setResult(-1, intent);
        finish();
    }

    public void onCancel(View v) {
        finish();
    }

    @TargetApi(11)
    public void onPickColor(View v) {
        ColorPickerDialog colorcalendar = ColorPickerDialog.newInstance(R.string.color_picker_default_title, this.mColor, this.mSelectedColorCal0, 4, 2);
        colorcalendar.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {
                mSelectedColorCal0 = color;
            }

        });

        colorcalendar.show(getFragmentManager(), "cal");
    }

    public static int[] colorChoice(Context context) {
        int[] mColorChoices = null;
        String[] color_array = context.getResources().getStringArray(R.array.default_color_choice_values);
        if (color_array != null && color_array.length > 0) {
            mColorChoices = new int[color_array.length];
            for (int i = 0; i < color_array.length; i++) {
                mColorChoices[i] = Color.parseColor(color_array[i]);
            }
        }
        return mColorChoices;
    }

}