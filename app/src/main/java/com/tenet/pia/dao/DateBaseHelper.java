package com.tenet.pia.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DateBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "pia.db";
    public static final int DATABASE_VERSION = 1;
    public static final String GROUP_TABLE = "group_table";
    public static final String CONTACT_TABLE = "contact_table";
    public static final String NOTE_TABLE = "note_table";
    public static final String SCHEDULE_TABLE = "schedule_table";

    private static DateBaseHelper instance;

    public static DateBaseHelper getInstance(Context context) {
        if(instance == null) {
            instance = new DateBaseHelper(context);
        }

        return instance;
    }

    private DateBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + GROUP_TABLE + " (id long primary key autoincrement, groupName text)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CONTACT_TABLE + " (id long primary key autoincrement, name text, phone text, email text, gender text,  groupName text, FOREIGN KEY(groupId) REFERENCES " + GROUP_TABLE + " (id))" );
        db.execSQL("CREATE TABLE IF NOT EXISTS " + NOTE_TABLE + " (id long primary key autoincrement, noteTitle text, noteContent text, long createTime)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + SCHEDULE_TABLE + " (id long primary key autoincrement, startTime long, endTime long, scheduleTitle text, scheduleDes text, scheduleLocation text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}