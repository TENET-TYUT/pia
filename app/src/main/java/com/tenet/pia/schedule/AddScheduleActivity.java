package com.tenet.pia.schedule;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tenet.pia.R;
import com.tenet.pia.calendarProvider.CalendarEvent;
import com.tenet.pia.calendarProvider.CalendarProviderManager;
import com.tenet.pia.calendarProvider.RRuleConstant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddScheduleActivity extends AppCompatActivity implements View.OnClickListener {

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

    private List<Integer> times = new ArrayList<>();

    private Date startDate;
    private Date endDate;

    private int advanceInt = 5;
    private String ruleStr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
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
        date.setOnClickListener(this);
        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
        advanceTime.setOnClickListener(this);
        rule.setOnClickListener(this);
        returnBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.return_icon:
                onBackPressed();
                break;
            case R.id.confirm_btn:
                onClickConfirm();
                break;
            case R.id.choose_date:
                onClickDate();
                break;
            case R.id.start_time:
                onClickStartTime();
                break;
            case R.id.end_time:
                onClickEndTime();
                break;
            case R.id.advance_time:
                onClickAdvanceTime();
                break;
            case R.id.rule:
                onClickRule();
                break;
        }
    }


    /**
     * 点击确认按钮
     */
    public void onClickConfirm() {
        if(checkNull()) {
            addToCalendar();
        }
    }

    /**
     * 判空
     */
    public boolean checkNull() {
        String eventTitle = titleEdit.getText().toString().trim();
        String eventDes = desEdit.getText().toString().trim();
        String eventLocation = locationEdit.getText().toString().trim();
        String eventDate = (String) date.getText();
        String start = (String) startTime.getText();
        String end = (String) endTime.getText();
        if (eventTitle.length() == 0) {
            Toast.makeText(this, "标题不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (eventDes.length() == 0) {
            Toast.makeText(this, "描述不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (eventLocation.length() == 0) {
            Toast.makeText(this, "地点不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (eventDate.length() == 0) {
            Toast.makeText(this, "日期不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (start.length() == 0) {
            Toast.makeText(this, "开始时间不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (ruleStr == null && end.length() == 0) {
            Toast.makeText(this, "不重复时结束时间不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 添加到系统日历
     */
    public void addToCalendar() {
        String eventTitle = titleEdit.getText().toString().trim();
        String eventDes = desEdit.getText().toString().trim();
        String eventLocation = locationEdit.getText().toString().trim();
        CalendarEvent calendarEvent = new CalendarEvent(eventTitle, eventDes, eventLocation, startDate.getTime(), endDate.getTime(), advanceInt, ruleStr);
        int result = CalendarProviderManager.addCalendarEvent(this, calendarEvent);
        if (result == 0) {
            Toast.makeText(this, "插入成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("success", "true");
            setResult(1, intent);
        } else if (result == -1) {
            Toast.makeText(this, "插入失败", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("success", "false");
            setResult(1, intent);
        } else if (result == -2) {
            Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("success", "false");
            setResult(1, intent);
        }
    }

    /**
     * 点击日期
     */
    public void onClickDate() {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date.setText(year + "年" + (month + 1) + "月" + dayOfMonth + "日");
                times.add(0, year);
                times.add(1, month);
                times.add(2, dayOfMonth);

            }
        }, year, month, day);
        DatePicker datePicker = datePickerDialog.getDatePicker();
        Date date = new Date();
        datePicker.setMinDate(date.getTime());
        datePickerDialog.show();
    }

    /**
     * 点击开始时间
     */
    public void onClickStartTime() {
        if (date.getText().length() > 0) {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    startTime.setText(hourOfDay + "时" + minute + "分");
                    startDate = new Date(times.get(0), times.get(1), times.get(2), hourOfDay, minute);
                }
            }, hour, minute, true);
            timePickerDialog.show();
        } else {
            Toast.makeText(this, "请先选择日期", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 点击结束时间
     */
    public void onClickEndTime() {
        if (date.getText().length() > 0) {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    endTime.setText(hourOfDay + "时" + minute + "分");
                    endDate = new Date(times.get(0), times.get(1), times.get(2), hourOfDay, minute);
                }
            }, hour, minute, true);
            timePickerDialog.show();
        } else {
            Toast.makeText(this, "请先选择日期", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 点击提醒时间
     */
    public void onClickAdvanceTime() {
        final int[] whichID = {0};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("选择提醒时间");
        final String items[] = {"5分钟前", "15分钟前", "30分钟前", "1小时前", "2小时前", "一天前", "两天前", "一周前"};
        dialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                whichID[0] = which;
            }
        });
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                advanceTime.setText(items[whichID[0]]);
                int[] arr = {5, 15, 30, 60, 2 * 60, 24 * 60, 2 * 24 * 60, 7 * 24 * 60};
                advanceInt = arr[whichID[0]];
            }
        });
        dialog.setNegativeButton("取消", null);
        dialog.show();

    }

    /**
     * 点击规则
     */
    public void onClickRule() {
        final int[] whichID = {3};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("选择选择重复规则");
        final String items[] = {"每天重复", "每周重复", "每月重复", "不重复"};
        dialog.setSingleChoiceItems(items, 3, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                whichID[0] = which;
            }
        });
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                rule.setText(items[whichID[0]]);
            }
        });
        switch (whichID[0]) {
            case 0:
                ruleStr = RRuleConstant.REPEAT_CYCLE_DAILY_FOREVER;
                break;
            case 1:
                ruleStr = RRuleConstant.REPEAT_CYCLE_WEEKLY;
                break;
            case 2:
                ruleStr = RRuleConstant.REPEAT_CYCLE_MONTHLY;
                break;
            default:
                ruleStr = null;
        }
        dialog.setNegativeButton("取消", null);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        String eventTitle = titleEdit.getText().toString().trim();
        String eventDes = desEdit.getText().toString().trim();
        String eventLocation = locationEdit.getText().toString().trim();
        String eventDate = (String) date.getText();
        String start = (String) startTime.getText();
        String end = (String) endTime.getText();
        if(eventTitle.length() == 0 && eventDes.length() == 0 && eventLocation.length() == 0 && eventDate.length() == 0 && start.length() == 0 && end.length() == 0) {
            new AlertDialog.Builder(this)
                    .setTitle("是否保存事件")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AddScheduleActivity.this.addToCalendar();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        } else {

            super.onBackPressed();
        }
    }
}
