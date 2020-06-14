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
    private ArrayList<Note> noteList;

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

    public int update(Note note){
        SQLiteDatabase db = dateBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, note.getId());
        values.put(NOTE_TITLE,note.getNoteTitle());
        values.put(NOTE_CONTENT, note.getNoteContent());
        values.put(NOTE_CONTENT, note.getNoteContent());
        int number=db.update(DateBaseHelper.NOTE_TABLE,values,"id=?",new Integer[]{note.getId()});
        db.close();
        return number;
    }

    public int delete(Note note) {
        SQLiteDatabase db = dateBaseHelper.getWritableDatabase();
        int number = db.delete(DateBaseHelper.NOTE_TABLE, "_id=?", new Int[]{note.getId()});
        db.close();
        return number;
    }

    public boolean find(long id){
        SQLiteDatabase db = dateBaseHelper.getReadableDatabase();
        Cursor cursor = db.query(DateBaseHelper.NOTE_TABLE,null,"_id=?",new String[]{},null,null,null);
        boolean result=cursor.moveToNext();
        cursor.close();
        db.close();
        return result;
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
