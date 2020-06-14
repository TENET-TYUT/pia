package com.tenet.pia.contacts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.tenet.pia.R;
import com.tenet.pia.dao.ContactDao;
import com.tenet.pia.dao.DateBaseHelper;
import com.tenet.pia.entity.Contact;
import com.tenet.pia.entity.Group;
import com.tenet.pia.group.GroupMainActivity;

public class AddContactsActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mbtn;
    private ImageButton mreturn;
    private Button mbtn_group;
    private EditText mname;
    private EditText mphone;
    private EditText memail;
    private EditText msex;
    private EditText mgroup;

    private RadioGroup genderradio;
    private RadioButton radioMale;
    private RadioButton radioFemal;

    private DateBaseHelper dateBaseHelper;
    private ContactDao contactDao;
    private String gender = "男";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);

        init();
        genderradio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioMale.getId() == checkedId){
                    gender = "男";
                } else if (radioFemal.getId() == checkedId) {
                    gender = "女";
                }
            }
        });

    }

    public void init(){
        mbtn=(Button)findViewById(R.id.ac_btn_determine);
        mreturn=(ImageButton)findViewById(R.id.ac_return);
        mbtn_group=(Button)findViewById(R.id.ac_btn_group);
        mname=(EditText) findViewById(R.id.ac_name);
        mphone=(EditText) findViewById(R.id.ac_phone);
        memail=(EditText) findViewById(R.id.ac_email);
        mgroup=(EditText) findViewById(R.id.ac_group);
        genderradio=(RadioGroup)findViewById(R.id.ac_gender_radio);
        radioMale=(RadioButton)findViewById(R.id.ac_gender_male);
        radioFemal=(RadioButton)findViewById(R.id.ac_gender_female);

        mbtn.setOnClickListener(this);
        mreturn.setOnClickListener(this);
        mbtn_group.setOnClickListener(this);

        contactDao = new ContactDao(this);
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.ac_return:
            onBackPressed();
            break;
        case R.id.ac_btn_determine:
            insertDate();
            break;
        case R.id.ac_btn_group:
            Intent intent1 = new Intent(this, SelGroupActivity.class);
            startActivity(intent1);
            break;
        }
    }


    private void insertDate() {

        String contactName = mname.getText().toString().trim();
        String contactPhone = mphone.getText().toString().trim();
        String contactEmail = memail.getText().toString().trim();
        String contactGender = gender.trim();
//      long contactGroupid =  ;
        String contactGroupname = mgroup.getText().toString().trim();

        if (contactName.length() > 0){

        }else {
        }
//        Contact contact = new Contact(contactName,contactPhone,contactEmail,contactGender,contactGroupid,contactGroupname);
//        contactDao.insert(contact);
    }

}