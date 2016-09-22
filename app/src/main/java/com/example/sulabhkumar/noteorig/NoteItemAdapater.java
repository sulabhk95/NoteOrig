package com.example.sulabhkumar.noteorig;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.sulabhkumar.noteorig.NoteItemDatabaseHelper.*;
import static com.example.sulabhkumar.noteorig.NoteItemDatabaseHelper.NOTEITEM_COLUMN_DATE;
import static com.example.sulabhkumar.noteorig.NoteItemDatabaseHelper.NOTEITEM_COLUMN_TEXT;
import static com.example.sulabhkumar.noteorig.NoteItemDatabaseHelper.NOTEITEM_COLUMN_TIME;
import static com.example.sulabhkumar.noteorig.NoteItemDatabaseHelper.NOTEITEM_COLUMN_TITLE;
import static com.example.sulabhkumar.noteorig.NoteItemDatabaseHelper.NOTEITEM_COLUMN_TYPE;

/**
 * Created by Sulabh Kumar on 3/24/2016.
 */
public class NoteItemAdapater extends CursorAdapter{
    NoteItemDatabaseHelper databaseHelper;
    ListView listView;
    Context context;
    private static final int EDIT_ENTRY_REQUEST_CODE = 1;
    ArrayList<Integer> colorList;
    int index;

    public NoteItemAdapater(Activity context, Cursor cursor) {
        super(context,cursor);
        this.context=context;
        databaseHelper = new NoteItemDatabaseHelper(context);
         listView  = (ListView)context.findViewById(android.R.id.list);
        colorList = new ArrayList<Integer>();
        colorList.add(Color.parseColor("#5CAAEC"));
        colorList.add(Color.parseColor("#F15D5D"));
        colorList.add(Color.parseColor("#ECE864"));
        colorList.add(Color.parseColor("#DE8BF6"));
        colorList.add(Color.parseColor("#64cdcf"));
        index = 0;
    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        TextView titleView = (TextView)view.findViewById(R.id.layout_item_title);
        TextView textView = (TextView)view.findViewById(R.id.layout_item_text);
        TextView dateView = (TextView)view.findViewById(R.id.layout_item_date);
        TextView timeView = (TextView)view.findViewById(R.id.layout_item_time);
        TextView typeView = (TextView)view.findViewById(R.id.layout_item_type);
        titleView.setText(cursor.getString(cursor.getColumnIndex(NOTEITEM_COLUMN_TITLE)));
        textView.setText(cursor.getString(cursor.getColumnIndex(NOTEITEM_COLUMN_TEXT)));
        dateView.setText(cursor.getString(cursor.getColumnIndex(NOTEITEM_COLUMN_DATE)));
        timeView.setText(cursor.getString(cursor.getColumnIndex(NOTEITEM_COLUMN_TIME)));
        String type = cursor.getString(cursor.getColumnIndex(NOTEITEM_COLUMN_TYPE));
        typeView.setText(type);

        ((GradientDrawable)((LayerDrawable)((ViewGroup)view).getChildAt(1).getBackground()).findDrawableByLayerId(R.id.list_item_topline_shape)).setStroke(40, Integer.parseInt(cursor.getString(cursor.getColumnIndex("color"))));


        int key = Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id")));
        ImageButton remButton = (ImageButton) view.findViewById(R.id.layout_item_remove_button);
        ImageButton editButton = (ImageButton) view.findViewById(R.id.layout_item_edit_button);
        ImageButton reminderButton = (ImageButton) view.findViewById(R.id.layout_item_reminder_button);
        reminderButton.setOnClickListener(new ReminderButtonOnClickListener(cursor.getString(cursor.getColumnIndex(NOTEITEM_COLUMN_TITLE))));
        remButton.setOnClickListener(new RemoveButtonOnClickListener(key,type));
        editButton.setOnClickListener(new EditButtonOnClickListener(key,cursor));
        ((View) textView.getParent()).setOnClickListener(new EditButtonOnClickListener(key, cursor));
    }

     class RemoveButtonOnClickListener implements View.OnClickListener
    {

        int key;
        String type;
        public RemoveButtonOnClickListener(int key, String type) {
            this.key = key;
            this.type = type;
        }

        @Override
        public void onClick(View v)
        {
            Animation anim = AnimationUtils.loadAnimation(
                    context, android.R.anim.slide_out_right
            );
            View viewtoAnimate = (View)v.getParent().getParent().getParent().getParent();
            anim.setDuration(500);
            viewtoAnimate.startAnimation(anim);
            databaseHelper.deleteRecord(key);
            changeCursor(databaseHelper.getRecordsFromType(type));
        }

    };

    class EditButtonOnClickListener implements View.OnClickListener
    {

        int key;
        Cursor cursor;
        public EditButtonOnClickListener(int key,Cursor cursor) {
            this.key= key;
            this.cursor = cursor;
        }

        @Override
        public void onClick(View v)
        {
            MainActivity mainActivity = (MainActivity) context;
            mainActivity.editButtonPressed(key, cursor);
        }

    };

    class ReminderButtonOnClickListener implements View.OnClickListener
    {

        int key;
        String title;
        public ReminderButtonOnClickListener(String c) {
            title = c;
        }

        @Override
        public void onClick(View v) {
            MainActivity mainActivity = (MainActivity) context;
            mainActivity.reminderButtonPressed(title);
        }

    };
}
