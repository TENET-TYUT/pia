package com.tenet.pia.contacts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tenet.pia.R;
import com.tenet.pia.dao.ContactDao;
import com.tenet.pia.entity.Contact;
import com.tenet.pia.group.GroupMainActivity;

public class ContactsInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private Button editBtn;
    private Button delButton;
    private ImageButton returnButton;
    private ContactDao contactDao;
    private TextView mName;
    private TextView mEmail;
    private TextView mPhone;

    private Contact contact;
    private static long contactId;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_info);
        contactDao = new ContactDao(this);
        Intent intent = getIntent();
        contactId = intent.getLongExtra("contactId", 0);
        init();
        refreshData();
    }

    public void init() {
        editBtn = (Button) findViewById(R.id.edit);
        delButton = (Button) findViewById(R.id.delete);
        returnButton = (ImageButton) findViewById(R.id.ci_return);
        mName = (TextView) findViewById(R.id.XM);
        mPhone = (TextView) findViewById(R.id.DH);
        mEmail = (TextView) findViewById(R.id.YJ);

        editBtn.setOnClickListener(this);
        delButton.setOnClickListener(this);
        returnButton.setOnClickListener(this);
    }

    public void refreshData() {
        //数据库查询后插入
        contact = contactDao.find(contactId);

        String gender = contact.getGender();

        if (contact.getGender().equals("女")) {//if (contact.getGender()=="女") {//
            mName.setText("Ms." + contact.getName() + " | " + contact.getGroupName() + "|" + contact.getGroupId());
        } else {
            mName.setText("Mr." + contact.getName() + " | " + contact.getGroupName() + "|" + contact.getGroupId());
        }
        mPhone.setText(contact.getPhone());
        mEmail.setText(contact.getEmail());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ci_return:
                Intent intent1 = new Intent(this, GroupMainActivity.class);
                startActivity(intent1);
                break;

            case R.id.edit:
                Intent intent2 = new Intent(this, EditContactsActivity.class);
                intent2.putExtra("contact", contact);
                startActivity(intent2);
                break;

            case R.id.delete:
                new AlertDialog.Builder(ContactsInfoActivity.this)
                        .setTitle("提示")
                        .setMessage("确认删除？")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                contactDao.delete(contactId);
                                Toast.makeText(ContactsInfoActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refreshData();
    }

}

