package com.tenet.pia.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.tenet.pia.entity.Contact;
import com.tenet.pia.entity.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ContactDao {
    private static final String CONTACT_ID = "contact_id";
    private static final String CONTACT_NAME = "name";
    private static final String CONTACT_PHONE = "phone";
    private static final String CONTACT_EMAIL = "email";
    private static final String CONTACT_GENDER = "gender";
    private static final String CONTACT_GROUPID = "groupId";
    private static final String CONTACT_GROUPNAME = "groupName";
    private DateBaseHelper dateBaseHelper;

    private ArrayList<Contact> contactList;
    private ArrayList<Contact> FcontactList;

    public ContactDao(Context context) {
        dateBaseHelper = DateBaseHelper.getInstance(context);
    }

    public void insert(Contact contact) {
        SQLiteDatabase db = dateBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CONTACT_NAME, contact.getName());
        values.put(CONTACT_PHONE, contact.getPhone());
        values.put(CONTACT_EMAIL, contact.getEmail());
        values.put(CONTACT_GENDER, contact.getGender());
        values.put(CONTACT_GROUPID, contact.getGroupId());
        values.put(CONTACT_GROUPNAME, contact.getGroupName());
        long id = db.insert(DateBaseHelper.CONTACT_TABLE, null, values);
        db.close();
    }

    public int delete(long id) {
        SQLiteDatabase db = dateBaseHelper.getReadableDatabase();
        int number = db.delete(DateBaseHelper.CONTACT_TABLE, CONTACT_ID + "=?", new String[]{id + ""});
        db.close();
        return number;
    }

    public int update(Contact contact) {
        SQLiteDatabase db = dateBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CONTACT_NAME, contact.getName());
        values.put(CONTACT_PHONE, contact.getPhone());
        values.put(CONTACT_EMAIL, contact.getEmail());
        values.put(CONTACT_GENDER, contact.getGender());
        values.put(CONTACT_GROUPID, contact.getGroupId());
        values.put(CONTACT_GROUPNAME, contact.getGroupName());
        int number = db.update(DateBaseHelper.CONTACT_TABLE, values, CONTACT_ID + "=?", new String[]{String.valueOf(contact.getId())});
        db.close();
        return number;
    }


    public List<Contact> query() {
        contactList = new ArrayList<>();
        SQLiteDatabase db = dateBaseHelper.getReadableDatabase();
        Cursor cursor = db.query(DateBaseHelper.CONTACT_TABLE, null, null, null, null, null, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }
        while (cursor.moveToNext()) {
            Long id = cursor.getLong(cursor.getColumnIndex(CONTACT_ID));
            String name = cursor.getString(cursor.getColumnIndex(CONTACT_NAME));
            String phone = cursor.getString(cursor.getColumnIndex(CONTACT_PHONE));
            String email = cursor.getString(cursor.getColumnIndex(CONTACT_EMAIL));
            String gender = cursor.getString(cursor.getColumnIndex(CONTACT_GENDER));
            long groupid = cursor.getLong(cursor.getColumnIndex(CONTACT_GROUPID));
            String groupname = cursor.getString(cursor.getColumnIndex(CONTACT_GROUPNAME));
            Contact contact = new Contact(id, name, phone, email, gender, groupid, groupname);
            contactList.add(contact);
        }
        cursor.close();
        db.close();
        return contactList;
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
        return contact;
    }


    public static boolean isMobileNO(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，"[5,7,9]"表示可以是5,7,9中的任意一位,[^4]表示除4以外的任何一个,\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }
}
