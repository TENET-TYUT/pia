package com.tenet.pia.group;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tenet.pia.R;
import com.tenet.pia.contacts.AddContactsActivity;

import java.io.IOException;

public class GroupMainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton mbtn_add;
    private ImageButton mbtn_return;
    private TextView mgroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_main);

        mbtn_return=(ImageButton)findViewById(R.id.return_icon);
        mbtn_add=(ImageButton)findViewById(R.id.gm_add_btn);
        mgroup=(TextView)findViewById(R.id.gm_group);


        mbtn_return.setOnClickListener(this);
        mbtn_add.setOnClickListener(this);
        mgroup.setOnClickListener(this);



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
