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
import com.tenet.pia.entity.Schedule;

import java.util.ArrayList;
import java.util.Calendar;
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

    private Calendar startDate;
    private Calendar endDate;

    private int advanceInt = 0;
    private String ruleStr = null;

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
        returnBtn = (ImageButton) findViewById(R.id.return_icon);
        confirmBtn = (Button) findViewById(R.id.confirm_btn);
        deleteBtn = (Button) findViewById(R.id.delete_btn);

        date.setOnClickListener(this);
        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
        returnBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);

        Intent intent = getIntent();
        schedule = (Schedule) intent.getSerializableExtra("schedule");
        titleEdit.setText(schedule.getScheduleTitle());
        desEdit.setText(schedule.getScheduleDes());
        locationEdit.setText(schedule.getScheduleLocation());
        startDate = Calendar.getInstance();
        startDate.setTimeInMillis(schedule.getStartTime());
        endDate = Calendar.getInstance();
        endDate.setTimeInMillis(schedule.getEndTime());
        int year = startDate.get(Calendar.YEAR);
        int month = startDate.get(Calendar.MONTH);
        int day = startDate.get(Calendar.DAY_OF_MONTH);
        times.add(0, startDate.get(Calendar.YEAR));
        times.add(1, startDate.get(Calendar.MONTH));
        times.add(2, startDate.get(Calendar.DAY_OF_MONTH));
        date.setText((year - 1900) + "年" + (month  + 1) + "月" + day + "日");
        startTime.setText(startDate.get(Calendar.HOUR_OF_DAY) + "时" + startDate.get(Calendar.MINUTE) + "分");
        endTime.setText(endDate.get(Calendar.HOUR_OF_DAY) + "时" + endDate.get(Calendar.MINUTE) + "分");
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
            case R.id.delete_btn:
                onClickDelete();
                break;
        }
    }

    /**
     * 点击删除按钮
     */
    public void onClickDelete() {
        new AlertDialog.Builder(this)
                .setTitle("确认删除")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long calID = CalendarProviderManager.obtainCalendarAccountID(EditScheduleActivity.this);
                        long eventID = schedule.getId();
                        int result = CalendarProviderManager.deleteCalendarEvent(EditScheduleActivity.this, eventID);
                        if(result == -2) {
                            Toast.makeText(EditScheduleActivity.this, "没有权限", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditScheduleActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("success", "true");
                            setResult(1, intent);
                            finish();
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();

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
        Date start = new Date(times.get(0), times.get(1), times.get(2), startDate.get(Calendar.HOUR_OF_DAY), startDate.get(Calendar.MINUTE));
        Date end =  new Date(times.get(0), times.get(1), times.get(2), endDate.get(Calendar.HOUR_OF_DAY), endDate.get(Calendar.MINUTE));
        CalendarEvent calendarEvent = new CalendarEvent(eventTitle, eventDes, eventLocation, start.getTime(), end.getTime(), advanceInt, ruleStr);
        int result =  CalendarProviderManager.updateCalendarEvent(this, schedule.getId(), calendarEvent);
        System.out.println(calendarEvent.toString());
        if (result == -2) {
            Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("success", "false");
            setResult(1, intent);
            finish();
        }
        Toast.makeText(this, "编辑成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("success", "true");
        setResult(1, intent);
        finish();
    }

    /**
     * 点击日期
     */
    public void onClickDate() {
        Date _date = new Date(schedule.getStartTime());
        final int year = _date.getYear();
        final int month = _date.getMonth() + 1;
        final int day = _date.getDate();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date.setText(year + "年" + month + "月" + dayOfMonth + "日");
                times.set(0, year);
                times.set(1, month);
                times.set(2, dayOfMonth);
            }
        }, year, month, day);
        DatePicker datePicker = datePickerDialog.getDatePicker();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        datePicker.setMinDate(calendar.getTime().getTime());
        datePickerDialog.show();
    }

    /**
     * 点击开始时间
     */
    public void onClickStartTime() {
        if (date.getText().length() > 0) {
            final TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    startTime.setText(hourOfDay + "时" + minute + "分");
                    Date date1 = new Date(times.get(0), times.get(1), times.get(2), hourOfDay, minute);
                    startDate.setTimeInMillis(date1.getTime());
                }
            }, startDate.get(Calendar.HOUR_OF_DAY), startDate.get(Calendar.MINUTE), true);
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
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    endTime.setText(hourOfDay + "时" + minute + "分");
                    Date date1 = new Date(times.get(0), times.get(1), times.get(2), hourOfDay, minute);
                    endDate.setTimeInMillis(date1.getTime());
                }
            }, endDate.get(Calendar.HOUR_OF_DAY), endDate.get(Calendar.MINUTE), true);
            timePickerDialog.show();
        } else {
            Toast.makeText(this, "请先选择日期", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        String eventTitle = titleEdit.getText().toString().trim();
        String eventDes = desEdit.getText().toString().trim();
        String eventLocation = locationEdit.getText().toString().trim();
        String eventDate = (String) date.getText();
        String start = (String) startTime.getText();
        String end = (String) endTime.getText();
        if(eventTitle.equals(schedule.getScheduleTitle()) && eventDes.equals(schedule.getScheduleDes()) && eventLocation.equals(schedule.getScheduleLocation()) && startDate.getTime().getTime() == schedule.getStartTime() && endDate.getTime().getTime() == schedule.getEndTime()) {
            super.onBackPressed();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("是否保存修改")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditScheduleActivity.this.addToCalendar();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }
    }
}
