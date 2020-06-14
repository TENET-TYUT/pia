package com.tenet.pia.group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.tenet.pia.R;
import com.tenet.pia.contacts.AddContactsActivity;
import com.tenet.pia.dao.ContactDao;
import com.tenet.pia.dao.DateBaseHelper;
import com.tenet.pia.entity.Contact;

import java.util.ArrayList;

public class GroupMainActivity extends AppCompatActivity implements View.OnClickListener{

    private ContactDao contactDao;
    private ArrayList<Contact> contactArrayList;
    private ListView listView;

    private ImageButton addBtn;
    private ImageButton returnBtn;
    private TextView myGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_main);

        contactDao = new ContactDao(this);
        contactArrayList = new ArrayList<Contact>();

        returnBtn=(ImageButton)findViewById(R.id.return_icon);
        addBtn=(ImageButton)findViewById(R.id.gm_add_btn);
        myGroup=(TextView)findViewById(R.id.gm_group);

        returnBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        myGroup.setOnClickListener(this);

        contactArrayList = (ArrayList<Contact>) contactDao.query();

        listView =(ListView)findViewById(R.id.gm_lv);
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
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                if (convertView == null){
                    LayoutInflater inflater = GroupMainActivity.this.getLayoutInflater();
                    view = inflater.inflate(R.layout.item_contacts,null);
                }else{
                    view = convertView;
                }
                Contact contact = contactArrayList.get(position);
                TextView name =(TextView)view.findViewById((R.id.ic_txt));
                name.setText(contact.getName());
                return view;
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.return_icon:
                onBackPressed();
                break;
            case R.id.gm_add_btn:
                Intent intent1 = new Intent(this, AddContactsActivity.class);
                startActivity(intent1);
                break;
            case R.id.gm_group:
                Intent intent2 = new Intent(this, ShowGroupActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
