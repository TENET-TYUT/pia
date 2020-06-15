package com.tenet.pia.group;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tenet.pia.R;
import com.tenet.pia.entity.Group;

public class GroupContactsActivity extends AppCompatActivity {
    private Group group;
    private TextView group_name;
    private Button edit_btn;
    private ImageButton returnBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_contacts);
        Intent intent = getIntent();

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
}
