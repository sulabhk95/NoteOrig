package com.example.sulabhkumar.noteorig;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
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
import com.wdullaer.materialdatetimepicker.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Sulabh Kumar on 3/24/2016.
 */
public class AddItemActivity extends Activity {
    String dropDownItem;
    int mSelectedColorCal0;
    int[] mColor;
    AdapterView.OnItemSelectedListener listener;

    /* renamed from: com.example.sulabhkumar.testing.AddItemActivity.2 */
    class C01902 implements AdapterView.OnItemSelectedListener {
        C01902() {
        }

        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            AddItemActivity.this.dropDownItem = parent.getItemAtPosition(position).toString();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: com.example.sulabhkumar.testing.AddItemActivity.1 */
    class C02821 implements ColorPickerSwatch.OnColorSelectedListener {
        C02821() {
        }

        public void onColorSelected(int color) {
            mSelectedColorCal0 = color;
        }
    }

    public AddItemActivity() {
        this.listener = new C01902();
        mSelectedColorCal0 = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_list_item);
        dropDownItem = "Note";
        mSelectedColorCal0 = Color.parseColor("#99cc00");
        mColor = colorChoice(AddItemActivity.this);
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

    public void onPickColor(View v) {
                ColorPickerDialog colorcalendar = ColorPickerDialog.newInstance(
                R.string.color_picker_default_title,
                mColor,
                0,
                5,
                ColorPickerDialog.SIZE_SMALL);
        colorcalendar.setOnColorSelectedListener(new C02821());
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

