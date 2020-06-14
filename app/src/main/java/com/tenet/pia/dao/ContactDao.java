package com.tenet.pia.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tenet.pia.entity.Contact;

import java.util.ArrayList;
import java.util.List;



public class ContactDao {
    private static final String CONTACT_NAME = "name";
    private static final String CONTACT_PHONE = "phone";
    private static final String CONTACT_EMAIL = "email";
    private static final String CONTACT_GENDER = "gender";
    private static final String CONTACT_GROUPID = "groupId";
    private static final String CONTACT_GROUPNAME = "groupName";
    private DateBaseHelper dateBaseHelper;

    private ArrayList<Contact> contactList;

    public ContactDao(Context context){
        dateBaseHelper = DateBaseHelper.getInstance(context);
    }
    public void insert(Contact contact){
        SQLiteDatabase db =dateBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();



        values.put(CONTACT_NAME,contact.getGroupName());
        values.put(CONTACT_PHONE,contact.getPhone());
        values.put(CONTACT_EMAIL,contact.getEmail());
        values.put(CONTACT_GENDER,contact.getGender());
        values.put(CONTACT_GROUPID,contact.getGroupId());
        values.put(CONTACT_GROUPNAME,contact.getGroupName());
        long id = db.insert(DateBaseHelper.CONTACT_TABLE,null,values);
        db.close();
    }

    public List<Contact> query(){
        contactList = new ArrayList<>();
        SQLiteDatabase db = dateBaseHelper.getReadableDatabase();
        Cursor cursor = db.query("CONTACT_TABLE",null,null,null,null,null,null);
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
        }
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String gender = cursor.getString(cursor.getColumnIndex("gender"));
            long groupid = cursor.getLong(cursor.getColumnIndex("groupid"));
            String groupname = cursor.getString(cursor.getColumnIndex("groupname"));
            Contact contact = new Contact(name,phone,email,gender,groupid,groupname);
            contactList.add(contact);
        }

        db.close();
        return contactList;
    }
}
