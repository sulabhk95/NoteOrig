package com.example.sulabhkumar.noteorig;

/**
 * Created by Sulabh Kumar on 3/24/2016.
 */

public class NoteItem {
    private String notes;
    private String time;

    public NoteItem(String time, String notes) {
        this.time = time;
        this.notes = notes;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
