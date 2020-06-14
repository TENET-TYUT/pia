package com.tenet.pia.group;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.tenet.pia.R;

import com.tenet.pia.dao.GroupDao;
import com.tenet.pia.entity.Group;

public class AddGroupActivity extends AppCompatActivity {
    private GroupDao groupDao;
    private EditText et_ag;
    private Button btn_ag;
    private ImageButton returnBtn;
    private String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        et_ag = (EditText) findViewById(R.id.new_group);
        btn_ag = (Button) findViewById(R.id.btn);
        returnBtn = (ImageButton)findViewById(R.id.ag_return);
        groupDao = new GroupDao(this);

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        btn_ag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealData();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog dialog;
        dialog = new AlertDialog.Builder(this)
                .setTitle("保存提醒").setMessage("是否确定保存？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dealData();
                        finish();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create();
        groupName = et_ag.getText().toString().trim();
        if (groupName.length() > 0) {
            dialog.show();
        } else {
            finish();
        }
    }

    public void dealData() {
        groupName = et_ag.getText().toString().trim();
        if (groupName.length() > 0) {
            Group group = new Group(groupName);
            groupDao.insert(group);
        } else {
            Toast.makeText(AddGroupActivity.this, "请输入组名再提交", Toast.LENGTH_SHORT).show();
        }
        final Intent intent = new Intent();
        intent.putExtra("groupName", groupName);
        setResult(1, intent);
    }
}
