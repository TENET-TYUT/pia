package com.tenet.pia.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tenet.pia.R;
import com.tenet.pia.dao.GroupDao;
import com.tenet.pia.entity.Group;


import java.util.ArrayList;

public class SelGroupActivity extends AppCompatActivity {
    private ListView lv;
    private GroupDao groupDao;
    private ArrayList<Group> groupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_group);

        refreshData();
    }

    public void refreshData() {
        groupDao = new GroupDao(this);
        groupList = (ArrayList<Group>) groupDao.query();

        lv = (ListView) findViewById(R.id.Lv_sg);

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
                    LayoutInflater inflater = SelGroupActivity.this.getLayoutInflater();
                    view = inflater.inflate(R.layout.item_sel_group, null);
                    //view = View.inflate(getBaseContext(),R.layout.item,null);
                } else {
                    view = convertView;
                }
                Group gp = groupList.get(position);
                TextView name = (TextView) view.findViewById(R.id.sg_tv);
                name.setText(gp.getGroupName());
                return view;
            }
        });
    }


}
