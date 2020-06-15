package com.tenet.pia.contacts;

import androidx.annotation.Nullable;
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
import android.widget.TextView;
import android.widget.Toast;


import com.tenet.pia.R;
import com.tenet.pia.dao.ContactDao;
import com.tenet.pia.dao.DateBaseHelper;
import com.tenet.pia.entity.Contact;
import com.tenet.pia.entity.Group;
import com.tenet.pia.group.GroupMainActivity;

public class AddContactsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mbtn;
    private ImageButton mreturn;
    private Button mbtn_group;
    private EditText mname;
    private EditText mphone;
    private EditText memail;
    private TextView mgroup;

    private RadioGroup genderradio;
    private RadioButton radioMale;
    private RadioButton radioFemal;

    private DateBaseHelper dateBaseHelper;
    private ContactDao contactDao;
    private String gender = "男";
    private long groupID;
    private String contactName, contactPhone, contactEmail, contactGender, contactGroupname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);

        init();

        genderradio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioMale.getId() == checkedId) {
                    gender = "男";
                } else if (radioFemal.getId() == checkedId) {
                    gender = "女";
                }
            }
        });

    }

    public void init() {
        mbtn = (Button) findViewById(R.id.ac_btn_determine);
        mreturn = (ImageButton) findViewById(R.id.ac_return);
        mbtn_group = (Button) findViewById(R.id.ac_btn_group);
        mname = (EditText) findViewById(R.id.ac_name);
        mphone = (EditText) findViewById(R.id.ac_phone);
        memail = (EditText) findViewById(R.id.ac_email);
        mgroup = (TextView) findViewById(R.id.ac_group);
        genderradio = (RadioGroup) findViewById(R.id.ac_gender_radio);
        radioMale = (RadioButton) findViewById(R.id.ac_gender_male);
        radioFemal = (RadioButton) findViewById(R.id.ac_gender_female);

        mbtn.setOnClickListener(this);
        mreturn.setOnClickListener(this);
        mbtn_group.setOnClickListener(this);

        contactDao = new ContactDao(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ac_return:
                finish();
                break;
            case R.id.ac_btn_determine:
                contactName = mname.getText().toString().trim();
                contactPhone = mphone.getText().toString().trim();
                contactEmail = memail.getText().toString().trim();
                contactGender = gender;
                contactGroupname = mgroup.getText().toString().trim();
                if (checkNull()) {
                    showNormalDialog(1);
                }
                break;

            case R.id.ac_btn_group:
                Intent intent1 = new Intent(this, SelGroupActivity.class);
                startActivityForResult(intent1, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 1) {
                Group group = (Group) data.getSerializableExtra("group");
                mgroup.setText(group.getGroupName());
                groupID = group.getId();

            }
        }
    }


    private boolean checkNull() {
        String mobileRegex = "^1(3|4|5|7|8)\\d{9}$";
        String emailRegex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        if (contactName.length() == 0) {
            Toast.makeText(this, "姓名为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!contactPhone.matches(mobileRegex)) {
            Toast.makeText(this, "电话号码格式错误", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!contactEmail.matches(emailRegex)) {
            Toast.makeText(this, "邮箱格式错误", Toast.LENGTH_SHORT).show();
            return false;
        } else if (contactGroupname.length() == 0) {
            Toast.makeText(this, "组别为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void insertDate() {

        Contact contact = new Contact(contactName, contactPhone, contactEmail, contactGender, groupID, contactGroupname);
        contactDao.insert(contact);
    }

    public void showNormalDialog(int flag) {
        final int num = flag;
        new AlertDialog.Builder(this)
                .setTitle("确认")
                .setMessage("是否确认提交")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        insertDate();
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (num == 1) {
                            return;
                        }
                        finish();
                    }
                })
                .show();
    }

}