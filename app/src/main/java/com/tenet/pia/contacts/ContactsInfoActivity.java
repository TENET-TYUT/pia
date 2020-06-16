package com.tenet.pia.contacts;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.tenet.pia.R;

public class ContactsInfoActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_info);
        contactDao = new ContactDao(this);
        Intent intent = getIntent();
        contactId = intent.getLongExtra("contactId",-1);
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
}
