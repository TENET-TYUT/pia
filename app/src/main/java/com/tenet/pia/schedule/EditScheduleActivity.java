package com.tenet.pia.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tenet.pia.R;
import com.tenet.pia.calendarProvider.RRuleConstant;
import com.tenet.pia.entity.Schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditScheduleActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String advancesTexts[] = {"5分钟前", "15分钟前", "30分钟前", "1小时前", "2小时前", "一天前", "两天前", "一周前"};
    private final static int advances[] = {0, 5, 15, 30, 60, 24 * 60, 2 * 24 * 60, 7 * 24 * 60};

    private final static String ruleText[] = {"每天重复", "每周重复", "每月重复", "不重复"};
    private final static String rules[] = {RRuleConstant.REPEAT_CYCLE_DAILY_FOREVER, RRuleConstant.REPEAT_CYCLE_WEEKLY,RRuleConstant.REPEAT_CYCLE_MONTHLY, null};

    /**
     * 日程标题
     */
    private EditText titleEdit;

    /**
     * 日程描述
     */
    private EditText desEdit;

    /**
     * 日程地点
     */
    private EditText locationEdit;

    /**
     * 日程日期
     */
    private TextView date;

    /**
     * 日程开始时间
     */
    private TextView startTime;

    /**
     * 日程结束时间
     */
    private TextView endTime;

    /**
     * 日程提醒时间
     */
    private TextView advanceTime;

    /**
     * 日程重复规则
     */
    private TextView rule;

    /**
     * 返回按钮
     */
    private ImageButton returnBtn;

    /**
     * 确认按钮
     */
    private Button confirmBtn;

    /**
     * 删除按钮
     */
    private Button deleteBtn;

    private List<Integer> times = new ArrayList<>();

    private Schedule schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);
        initView();
    }

    public void initView() {
        titleEdit = (EditText) findViewById(R.id.title);
        desEdit = (EditText) findViewById(R.id.des);
        locationEdit = (EditText) findViewById(R.id.location);
        date = (TextView) findViewById(R.id.choose_date);
        startTime = (TextView) findViewById(R.id.start_time);
        endTime = (TextView) findViewById(R.id.end_time);
        advanceTime = (TextView) findViewById(R.id.advance_time);
        rule = (TextView) findViewById(R.id.rule);
        returnBtn = (ImageButton) findViewById(R.id.return_icon);
        confirmBtn = (Button) findViewById(R.id.confirm_btn);
        deleteBtn = (Button) findViewById(R.id.delete_btn);

        date.setOnClickListener(this);
        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
        advanceTime.setOnClickListener(this);
        rule.setOnClickListener(this);
        returnBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);

        Intent intent = getIntent();
        schedule = (Schedule) intent.getSerializableExtra("schedule");
        titleEdit.setText(schedule.getScheduleTitle());
        desEdit.setText(schedule.getScheduleDes());
        locationEdit.setText(schedule.getScheduleLocation());
        Date startDate = new Date(schedule.getStartTime());
        Date endDate = new Date(schedule.getEndTime());
        date.setText(startDate.getYear() + "年" + startDate.getMonth() + "月" + startDate.getDate() + "日");
        startTime.setText(startDate.getHours() + "时" + startDate.getMinutes() + "分");
        endTime.setText(endDate.getHours() + "时" + endDate.getMinutes() + "分");
        Log.i("advance", schedule.getAdvance() + "");
        advanceTime.setText(advancesTexts[schedule.getAdvance()]);
        for (int i = 0; i < rules.length; i++) {
            if(rules[i].equals(schedule.getRule())) {
                rule.setText(ruleText[i]);
            }
        }
    }

    @Override
    public void onClick(View v) {

    }
}
