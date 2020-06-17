package com.tenet.pia.group;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.tenet.pia.R;
import com.tenet.pia.dao.GroupDao;
import com.tenet.pia.entity.Group;

import java.util.ArrayList;

public class ShowGroupActivity extends AppCompatActivity {

    private ImageButton button;
    private ListView lv;
    private GroupDao groupDao;
    private GroupDao delgroupDao;
    private ArrayList<Group> groupList;
    private String addGroupName = "";
    private ImageButton returnBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_group);

        returnBtn = (ImageButton)findViewById(R.id.sg_return);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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
        delgroupDao = new GroupDao(this);

        groupList = (ArrayList<Group>) groupDao.query();

        lv = (ListView) findViewById(R.id.Lv_mg);
        final ShowGroupActivity _this = this;
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
            public View getView(final int position, View convertView, ViewGroup parent) {
                View view;
                if (convertView == null) {
                    LayoutInflater inflater = ShowGroupActivity.this.getLayoutInflater();
                    view = inflater.inflate(R.layout.item_show_group, null);
                    //view = View.inflate(getBaseContext(),R.layout.item,null);
                } else {
                    view = convertView;
                }
                final Group gp = groupList.get(position);
                TextView name = (TextView) view.findViewById(R.id.item_show_group_tv);
                Button delButten = (Button) view.findViewById(R.id.delBtn);
                final BaseAdapter _that = this;
                name.setText(gp.getGroupName());

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(_this, GroupContactsActivity.class);
                        intent.putExtra("group",gp);
                        startActivity(intent);
                    }
                });

                delButten.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog dialog = new AlertDialog.Builder(_this)
                                .setTitle("删除确认").setMessage("是否确定删除？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        delgroupDao.delete(gp);
                                        groupList.remove(gp);
                                        _that.notifyDataSetChanged();
                                    }
                                }).setNegativeButton("取消", null).create();
                        dialog.show();
                    }
                });
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

        refreshData();


    }

    @Override
    protected void onRestart() {
        super.onRestart();

        refreshData();

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
