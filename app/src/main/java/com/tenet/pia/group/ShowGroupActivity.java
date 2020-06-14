package com.tenet.pia.group;

import androidx.annotation.Nullable;
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
import com.tenet.pia.dao.GroupDao;
import com.tenet.pia.entity.Group;

import java.util.ArrayList;

public class ShowGroupActivity extends AppCompatActivity {

    private ImageButton button;
    private ListView lv;
    private GroupDao groupDao;
    private ArrayList<Group> groupList;
    private String addGroupName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_group);



        button = (ImageButton) findViewById(R.id.add_sg);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to_add();
            }
        });

        refreshData();


    }

    public void refreshData() {
        groupDao = new GroupDao(this);
        groupList = (ArrayList<Group>) groupDao.query();

        lv = (ListView) findViewById(R.id.Lv_mg);

        lv.setAdapter(new BaseAdapter() {

            @Override
            public int getCount() {
                return groupList.size();
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
                if (convertView == null) {
                    LayoutInflater inflater = ShowGroupActivity.this.getLayoutInflater();
                    view = inflater.inflate(R.layout.item_show_group, null);
                    //view = View.inflate(getBaseContext(),R.layout.item,null);
                } else {
                    view = convertView;
                }
                Group gp = groupList.get(position);
                TextView name = (TextView) view.findViewById(R.id.item_show_group_tv);
                name.setText(gp.getGroupName());
                return view;
            }
        });
    }

    public void to_add() {
        Intent intent = new Intent(this, AddGroupActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(addGroupName.length() > 0) {
            refreshData();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(addGroupName.length() > 0) {
            refreshData();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(resultCode == 1) {
                addGroupName = data.getStringExtra("groupName");
            }
        }
    }
}
