package com.tenet.pia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tenet.pia.entity.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteDao {
    private static final String ID = "id";
    private static final String NOTE_TITLE = "noteTitle";
    private static final String NOTE_CONTENT = "noteContent";
    private static final String CREATE_TIME = "createTime";
    private DateBaseHelper dateBaseHelper;

    public NoteDao(Context context) {
        dateBaseHelper = DateBaseHelper.getInstance(context);
    }

    public void insert(Note note) {
        SQLiteDatabase db = dateBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, note.getId());
        values.put(NOTE_TITLE, note.getNoteTitle());
        values.put(NOTE_CONTENT, note.getNoteContent());
        values.put(NOTE_CONTENT, note.getNoteContent());
        long id = db.insert(DateBaseHelper.NOTE_TABLE, null, values);
        db.close();
    }

    public int update(Note note) {
        SQLiteDatabase db = dateBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOTE_TITLE, note.getNoteTitle());
        values.put(NOTE_CONTENT, note.getNoteContent());
        values.put(NOTE_CONTENT, note.getNoteContent());
        int number = db.update(DateBaseHelper.NOTE_TABLE, values, ID + "=?", new String[]{String.valueOf(note.getId())});
        db.close();
        return number;
    }

    public int delete(Note note) {
        SQLiteDatabase db = dateBaseHelper.getWritableDatabase();
        int number = db.delete(DateBaseHelper.NOTE_TABLE,  ID + "=?", new String[]{String.valueOf(note.getId())});
        db.close();
        return number;
    }

    public Note find(long id) {
        SQLiteDatabase db = dateBaseHelper.getReadableDatabase();
        Cursor cursor = db.query(DateBaseHelper.NOTE_TABLE, null,  ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        cursor.moveToFirst();
        long _id = cursor.getLong(cursor.getColumnIndex(ID));
        String noteTitle = cursor.getString(cursor.getColumnIndex(NOTE_TITLE));
        String noteContent = cursor.getString(cursor.getColumnIndex(NOTE_CONTENT));
        long createTime = cursor.getLong(cursor.getColumnIndex(CREATE_TIME));
        cursor.close();
        db.close();
        return new Note(_id, noteTitle, noteContent, createTime);
    }

    public List<Note> query() {
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase db = dateBaseHelper.getReadableDatabase();
        Cursor cursor = db.query(DateBaseHelper.NOTE_TABLE, null, null, null, null, null, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(ID));
            String noteTitle = cursor.getString(cursor.getColumnIndex(NOTE_TITLE));
            String noteContent = cursor.getString(cursor.getColumnIndex(NOTE_CONTENT));
            long createTime = cursor.getLong(cursor.getColumnIndex(CREATE_TIME));
            Note note = new Note(id,noteTitle, noteContent, createTime);
            noteList.add(note);
        }
        db.close();
        return noteList;
    }


}
