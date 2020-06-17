package com.tenet.pia.contacts;

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
import com.tenet.pia.group.AddGroupActivity;


import java.util.ArrayList;

public class SelGroupActivity extends AppCompatActivity {
    private ListView lv;
    private GroupDao groupDao;
    private ArrayList<Group> groupList;
    private ImageButton add_sg;
    private ImageButton returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_group);

        //设置手机应用内部状态栏字体图标为黑色
        changeStatusBarTextImgColor(true);

        final Intent intent = new Intent(this, AddGroupActivity.class);
        add_sg = (ImageButton) findViewById(R.id.add_sg);
        returnButton = (ImageButton) findViewById(R.id.sg_return);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        refreshData();
    }

    public void refreshData() {
        final Intent intent = new Intent(this, AddGroupActivity.class);
        add_sg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
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
                final Group group = groupList.get(position);
                TextView name = (TextView) view.findViewById(R.id.sg_tv);
                name.setText(group.getGroupName());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("group", group);
                        setResult(1,intent);
                        finish();
                    }
                });
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
