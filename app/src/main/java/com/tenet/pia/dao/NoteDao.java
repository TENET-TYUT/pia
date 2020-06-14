package com.tenet.pia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tenet.pia.entity.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteDao {
    private static final String NOTE_TITLE = "noteTitle";
    private static final String NOTE_CONTENT = "noteContent";
    private static final String CREATE_TIME = "createTime";
    private DateBaseHelper dateBaseHelper;
    private ArrayList<Note> noteList;

    public NoteDao(Context context) {
        dateBaseHelper = DateBaseHelper.getInstance(context);
    }

    public void insert(Note note) {
        SQLiteDatabase db = dateBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOTE_TITLE, note.getNoteTitle());
        values.put(NOTE_CONTENT, note.getNoteContent());
        values.put(NOTE_CONTENT, note.getNoteContent());
        long id = db.insert(DateBaseHelper.NOTE_TABLE, null, values);
        db.close();
    }

    public void update(Note note){
        SQLiteDatabase db = dateBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOTE_TITLE,note.getNoteTitle());
        values.put(NOTE_CONTENT, note.getNoteContent());
        values.put(NOTE_CONTENT, note.getNoteContent());
        long id=db.update(DateBaseHelper.NOTE_TABLE,values,"name=?",new String[]{})
    }

    public void delete(Note note){

    }

    public List<Note> query() {
        noteList = new ArrayList<>();
        SQLiteDatabase db = dateBaseHelper.getReadableDatabase();
        Cursor cursor = db.query("NOTE_TABLE", null, null, null, null, null, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }
        while (cursor.moveToNext()) {
            String notetitle = cursor.getString(cursor.getColumnIndex(NOTE_TITLE));
            String notecontent = cursor.getString(cursor.getColumnIndex(NOTE_CONTENT));
            String createtime = cursor.getString(cursor.getColumnIndex(CREATE_TIME));
            Note note = new Note(notetitle, notecontent,createtime);
            noteList.add(note);
        }

        db.close();
        return noteList;
    }


}
