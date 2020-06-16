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

public class EditGroupActivity extends AppCompatActivity {
    private Group group;
    private GroupDao groupDao;
    private EditText et_eg;
    private Button btn_eg;
    private ImageButton returnBtn;
    private String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);
        et_eg = (EditText) findViewById(R.id.edit_group);
        btn_eg = (Button) findViewById(R.id.edit_btn);
        returnBtn = (ImageButton)findViewById(R.id.ag_return);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        groupDao = new GroupDao(this);
        Intent intent = getIntent();
        group = (Group) intent.getSerializableExtra("group");
        et_eg.setText(group.getGroupName());
        btn_eg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealData();
                finish();
            }
        });
    }

    public void onBackPressed() {
        if (et_eg.getText().toString().trim().equals(group.getGroupName())){
            finish();
        }else {
            AlertDialog dialog = new AlertDialog.Builder(this)
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
            groupName = et_eg.getText().toString().trim();
            if (groupName.length() > 0) {
                dialog.show();
            } else {
                finish();
            }
        }

    }

    public void dealData() {
        groupName = et_eg.getText().toString().trim();
        if (groupName.length() > 0) {
            group.setGroupName(groupName);
            groupDao.update(group);
        } else {
            Toast.makeText(EditGroupActivity.this, "请输入组名再提交", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent();
        intent.putExtra("groupName", groupName);
        setResult(1, intent);
    }
}