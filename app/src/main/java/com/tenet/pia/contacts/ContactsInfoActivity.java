package com.tenet.pia.contacts;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
    private ImageButton callBtn;

    private Contact contact;
    private static long contactId;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_info);

        //设置手机应用内部状态栏字体图标为黑色
        changeStatusBarTextImgColor(true);

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
        callBtn = (ImageButton) findViewById(R.id.callBtn);

        editBtn.setOnClickListener(this);
        delButton.setOnClickListener(this);
        returnButton.setOnClickListener(this);
        callBtn.setOnClickListener(this);
    }

    public void refreshData() {
        //数据库查询后插入
        contact = contactDao.find(contactId);

        String gender = contact.getGender();

        if (contact.getGender().equals("女")) {//if (contact.getGender()=="女") {//
            mName.setText("Ms." + contact.getName() + " | " + contact.getGroupName() );
        } else {
            mName.setText("Mr." + contact.getName() + " | " + contact.getGroupName() );
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

            case R.id.callBtn:
                Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contact.getPhone()));//跳转到拨号界面，同时传递电话号码
                startActivity(dialIntent);
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

    /**
     * 界面设置状态栏字体颜色
     */
    public void changeStatusBarTextImgColor(boolean isBlack) {
        if (isBlack) {
            //设置状态栏黑色字体
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            //恢复状态栏白色字体
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

}
