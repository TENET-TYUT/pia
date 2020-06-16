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
    private ArrayList<Contact> FcontactList;

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

    public List<Contact> queryById(Group group) {

        contactList = new ArrayList<>();
        FcontactList = new ArrayList<>();
        SQLiteDatabase db = dateBaseHelper.getReadableDatabase();
        //Cursor cursor1 = db.query(DateBaseHelper.CONTACT_TABLE, null, CONTACT_GROUPID + "=?", new String[]{idd + ""}, null, null, null);
        Cursor cursor1 = db.query(DateBaseHelper.CONTACT_TABLE, null, null, null, null, null, null);

        if (cursor1.getCount() != 0) {
            cursor1.moveToFirst();
        }
        while (cursor1.moveToNext()) {
            Long id = cursor1.getLong(cursor1.getColumnIndex(CONTACT_ID));
            String name = cursor1.getString(cursor1.getColumnIndex(CONTACT_NAME));
            String phone = cursor1.getString(cursor1.getColumnIndex(CONTACT_PHONE));
            String email = cursor1.getString(cursor1.getColumnIndex(CONTACT_EMAIL));
            String gender = cursor1.getString(cursor1.getColumnIndex(CONTACT_GENDER));
            long groupid = cursor1.getLong(cursor1.getColumnIndex(CONTACT_GROUPID));
            String groupname = cursor1.getString(cursor1.getColumnIndex(CONTACT_GROUPNAME));
            Contact contact = new Contact(id, name, phone, email, gender, groupid, groupname);
            contactList.add(contact);
        }
        cursor1.close();
        db.close();
        for(Contact contact: contactList) {
            if(contact.getGroupId() == group.getId()) FcontactList.add(contact);
        }
        return FcontactList;
    }

    public Contact find(long id) {
        SQLiteDatabase db = dateBaseHelper.getReadableDatabase();
        Cursor cursor = db.query(DateBaseHelper.CONTACT_TABLE, null, CONTACT_ID + "=?", new String[]{id + ""}, null, null, null);
        cursor.moveToNext();
        String name = cursor.getString(cursor.getColumnIndex(CONTACT_NAME));
        String phone = cursor.getString(cursor.getColumnIndex(CONTACT_PHONE));
        String email = cursor.getString(cursor.getColumnIndex(CONTACT_EMAIL));
        String gender = cursor.getString(cursor.getColumnIndex(CONTACT_GENDER));
        long groupid = cursor.getLong(cursor.getColumnIndex(CONTACT_GROUPID));
        String groupname = cursor.getString(cursor.getColumnIndex(CONTACT_GROUPNAME));
        Contact contact = new Contact(id, name, phone, email, gender, groupid, groupname);
        cursor.close();
        db.close();
        return contactList;
    }
}
