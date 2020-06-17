package com.tenet.pia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tenet.pia.entity.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupDao {

    private static final String GROUP_NAME = "groupName";
    private static final String ID = "id";
    private static final String GROUP_ID = "groupId";
    private DateBaseHelper dateBaseHelper;
    private ArrayList<Group> groupList;

    public GroupDao(Context context) {
        dateBaseHelper = DateBaseHelper.getInstance(context);
    }

    public void insert(Group group) {

        SQLiteDatabase db = dateBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GROUP_NAME, group.getGroupName());
        long id = db.insert(DateBaseHelper.GROUP_TABLE, null, values);
        db.close();

    }

    public List<Group> query() {
        groupList = new ArrayList<>();
        SQLiteDatabase db = dateBaseHelper.getReadableDatabase();
        Cursor cursor = db.query(DateBaseHelper.GROUP_TABLE,null,null,null,null,null,null);
        while(cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(GROUP_NAME));
            long id = cursor.getLong(cursor.getColumnIndex(ID));
            Group group = new Group(id,name);
            groupList.add(group);
        }
        db.close();
        return groupList;

    }

    public int delete(Group group){
        SQLiteDatabase db = dateBaseHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(GROUP_ID, -1);
        values.put(GROUP_NAME, "未分组");
        int number = db.delete(DateBaseHelper.GROUP_TABLE,ID + "=?",new String[]{group.getId() + ""});
        db.update(DateBaseHelper.CONTACT_TABLE, values, GROUP_ID + "=?", new String[]{group.getId() + ""});
        db.close();
        return number;
    }

    public int update(Group group){
        SQLiteDatabase db = dateBaseHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(GROUP_NAME, group.getGroupName());
        int number = db.update(DateBaseHelper.GROUP_TABLE, values, ID + "=?", new String[]{group.getId() + ""});
        db.update(DateBaseHelper.CONTACT_TABLE, values, GROUP_ID + "=?", new String[]{group.getId() + ""});
        db.close();
        return number;
    }



}