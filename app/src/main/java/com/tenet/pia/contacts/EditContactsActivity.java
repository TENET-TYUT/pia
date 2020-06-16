package com.tenet.pia.contacts;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.tenet.pia.R;
import com.tenet.pia.dao.ContactDao;
import com.tenet.pia.entity.Contact;
import com.tenet.pia.entity.Group;

public class EditContactsActivity extends Activity implements View.OnClickListener {

    private Button mBtn;
    private ImageButton mReturn;
    private Button btnGroup;
    private EditText mName;
    private EditText mPhone;
    private EditText mEmail;
    private TextView mGroup;

    private RadioGroup radioGroup;
    private RadioButton maleButton;
    private RadioButton femaleButton;
    private String gender;
    private long groupID;
    private Contact contact;
    private ContactDao contactDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contacts);

        //接受ContactsInfoActivity传来的contact对象
        contact = (Contact) getIntent().getSerializableExtra("contact");
        gender = contact.getGender();
        //初始化代码
        init();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (maleButton.getId() == checkedId) {
                    gender = "男";
                } else if (femaleButton.getId() == checkedId) {
                    gender = "女";
                }
            }
        });
    }

    public void init() {
        mBtn = (Button) findViewById(R.id.ec_btn_determine);
        mReturn = (ImageButton) findViewById(R.id.ec_Cancel);
        btnGroup = (Button) findViewById(R.id.ec_btn_group);
        mName = (EditText) findViewById(R.id.ec_name);
        mPhone = (EditText) findViewById(R.id.ec_phone);
        mEmail = (EditText) findViewById(R.id.ec_email);
        mGroup = (TextView) findViewById(R.id.ec_group);
        radioGroup = (RadioGroup) findViewById(R.id.ec_gender_radio);
        maleButton = (RadioButton) findViewById(R.id.ec_gender_male);
        femaleButton = (RadioButton) findViewById(R.id.ec_gender_female);

        mName.setText(contact.getName());
        mPhone.setText(contact.getPhone());
        mEmail.setText(contact.getEmail());
        mGroup.setText(contact.getGroupName());

        if (contact.getGender().equals("女")) {
            radioGroup.check(femaleButton.getId());
        } else {
            radioGroup.check(maleButton.getId());
        }

        mBtn.setOnClickListener(this);
        mReturn.setOnClickListener(this);
        radioGroup.setOnClickListener(this);
        btnGroup.setOnClickListener(this);
        contactDao = new ContactDao(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ec_Cancel:
                onBackPressed();
                break;
            case R.id.ec_btn_determine:

                contact.setName(mName.getText().toString().trim());
                contact.setPhone(mPhone.getText().toString().trim());
                contact.setEmail(mEmail.getText().toString().trim());
                contact.setGender(gender);
                contact.setGroupName(mGroup.getText().toString().trim());
                if (checkNull()) {
                    showNormalDialog(1);
                }
                break;
            case R.id.ec_btn_group:
                Intent intent2 = new Intent(this, SelGroupActivity.class);
                startActivityForResult(intent2, 2);
                break;


        }
    }

    private boolean checkNull() {
        String mobileRegex = "^1(3|4|5|7|8)\\d{9}$";
        String emailRegex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        if (contact.getName().length() == 0) {
            Toast.makeText(this, "姓名为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!contact.getPhone().matches(mobileRegex)) {
            Toast.makeText(this, "电话号码格式错误", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!contact.getEmail().matches(emailRegex)) {
            Toast.makeText(this, "邮箱格式错误", Toast.LENGTH_SHORT).show();
            return false;
        } else if (contact.getGroupName().length() == 0) {
            contact.setGroupId(-1);
            contact.setGroupName("未分组");
        }
        return true;
    }

    public void showNormalDialog(int flag) {
        final int num = flag;
        new AlertDialog.Builder(this)
                .setTitle("确认")
                .setMessage("是否确认提交")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        contactDao.update(contact);
                        Intent intent1 = new Intent(EditContactsActivity.this, ContactsInfoActivity.class);
                        intent1.putExtra("contactId", contact.getId());
                        startActivity(intent1);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == 1) {
                Group group = (Group) data.getSerializableExtra("group");
                mGroup.setText(group.getGroupName());
                contact.setGroupId(group.getId());
                contact.setGroupName(group.getGroupName());
            }
        }
    }

}