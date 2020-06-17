package com.tenet.pia;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.tenet.pia.group.GroupMainActivity;
import com.tenet.pia.notes.ShowNotesActivity;
import com.tenet.pia.schedule.ShowScheduleActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton mbtn_contacts;
    private ImageButton mbtn_schedule;
    private ImageButton mbtn_notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置手机应用内部状态栏字体图标为黑色
        changeStatusBarTextImgColor(true);

        mbtn_contacts=(ImageButton) findViewById(R.id.btn_contacts);
        mbtn_notes=(ImageButton) findViewById(R.id.btn_notes);
        mbtn_schedule =(ImageButton)findViewById(R.id.btn_schedule);


        mbtn_contacts.setOnClickListener(this);
        mbtn_notes.setOnClickListener(this);
        mbtn_schedule.setOnClickListener(this);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR}, 1);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_contacts:
                Intent intent1 = new Intent(this,GroupMainActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_schedule:
                Intent intent2 = new Intent(this, ShowScheduleActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_notes:
                Intent intent3 = new Intent(this, ShowNotesActivity.class);
                startActivity(intent3);
                break;
        }

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
