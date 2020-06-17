package com.tenet.pia.group;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.tenet.pia.R;
import com.tenet.pia.contacts.ContactsInfoActivity;
import com.tenet.pia.dao.ContactDao;
import com.tenet.pia.entity.Contact;
import com.tenet.pia.entity.Group;

import java.util.ArrayList;

public class GroupContactsActivity extends AppCompatActivity {
    private Group group;
    private TextView group_name;
    private Button edit_btn;
    private ImageButton returnBtn;
    private ContactDao contactDao;
    private ArrayList<Contact> contactArrayList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_contacts);

        //设置手机应用内部状态栏字体图标为黑色
        changeStatusBarTextImgColor(true);

        Intent intent = getIntent();

        contactDao = new ContactDao(this);
        contactArrayList = new ArrayList<Contact>();

        group = (Group) intent.getSerializableExtra("group");

        group_name = (TextView) findViewById(R.id.group_name);
        group_name.setText(group.getGroupName());
        returnBtn = (ImageButton)findViewById(R.id.return_icon);
        edit_btn = (Button) findViewById(R.id.edit_btn);

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to_edit();
            }
        });
        refreshData();
    }


    public void to_edit() {
        Intent GotoEditIntent = new Intent(this, EditGroupActivity.class);
        GotoEditIntent.putExtra("group",group);
        startActivityForResult(GotoEditIntent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(resultCode == 1) {
                String editGroupName = data.getStringExtra("groupName");
                group_name.setText(editGroupName);
            }
        }
    }

    public void refreshData() {

        contactArrayList = (ArrayList<Contact>) contactDao.queryById(group);
        listView = (ListView) findViewById(R.id.Lv_gc);
        final GroupContactsActivity _this = this;
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return contactArrayList.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                if (convertView == null) {
                    LayoutInflater inflater = GroupContactsActivity.this.getLayoutInflater();
                    view = inflater.inflate(R.layout.item_contacts, null);
                } else {
                    view = convertView;
                }
                final Contact contact = contactArrayList.get(position);
                TextView name = (TextView) view.findViewById((R.id.ic_txt));
                name.setText(contact.getName());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(_this, ContactsInfoActivity.class);
                        intent.putExtra("contactId", contact.getId());
                        startActivity(intent);
                    }
                });
                Log.i("ss", contact.getName());
                return view;
            }
        });
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
