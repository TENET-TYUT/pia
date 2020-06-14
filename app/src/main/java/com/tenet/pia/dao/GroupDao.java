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
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }
        while(cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(GROUP_NAME));
            int id = cursor.getInt(cursor.getColumnIndex(ID));
            Group group = new Group(id,name);
            groupList.add(group);
        }
        db.close();
        return groupList;

    }

}
