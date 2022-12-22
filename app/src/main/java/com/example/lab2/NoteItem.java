package com.example.lab2;

import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteItem {
    public static final String ITEM_SEP = System.getProperty("line.separator");

    public final static String TITLE = "title";
    public final static String DESCRIPTION = "description";
    public final static String DATE = "date";

    public final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    private String mTitle = new String();
    private String mDescription = new String();
    private Date mDate = new Date();

    NoteItem(String title, String description, Date date) {
        this.mTitle = title;
        this.mDescription = description;
        this.mDate = date;
    }

    NoteItem(Intent intent) {

        mTitle = intent.getStringExtra(NoteItem.TITLE);
        mDescription = intent.getStringExtra(NoteItem.DESCRIPTION);

        try {
            mDate = NoteItem.FORMAT.parse(intent.getStringExtra(NoteItem.DATE));
        } catch (ParseException e) {
            mDate = new Date();
        }
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public static void packageIntent(Intent intent, String title, String description,String date) {

        intent.putExtra(NoteItem.TITLE, title);
        intent.putExtra(NoteItem.DESCRIPTION, description);
        intent.putExtra(NoteItem.DATE, date);

    }

    public static void packageIntent(Intent intent, String title, String description,String date, int pos) {

        intent.putExtra(NoteItem.TITLE, title);
        intent.putExtra(NoteItem.DESCRIPTION, description);
        intent.putExtra(NoteItem.DATE, date);
        intent.putExtra("pos", pos);

    }

    public String toString() {
        return mTitle + ITEM_SEP + mDescription + ITEM_SEP + FORMAT.format(mDate);
    }

    public String toLog() {
        return "Title:" + mTitle + ITEM_SEP + "Description: " + mDescription +  ITEM_SEP + "Date:" + FORMAT.format(mDate) + "\n";
    }
}
