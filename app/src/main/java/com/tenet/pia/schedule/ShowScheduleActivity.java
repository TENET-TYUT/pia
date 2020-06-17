package com.tenet.pia.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tenet.pia.R;
import com.tenet.pia.calendarProvider.CalendarEvent;
import com.tenet.pia.calendarProvider.CalendarProviderManager;
import com.tenet.pia.entity.Schedule;

import java.util.Date;
import java.util.List;

public class ShowScheduleActivity extends AppCompatActivity implements View.OnClickListener {



    private ImageButton returnBtn;
    private ImageButton addBtn;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_schedule);
        initView();
        renderListView();
    }

    public void initView() {
        returnBtn = (ImageButton) findViewById(R.id.btn_return);
        addBtn = (ImageButton) findViewById(R.id.add_btn);
        listView = (ListView) findViewById(R.id.listView);
        addBtn.setOnClickListener(this);
        returnBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_btn:
                onClickAdd();
                break;
            case R.id.btn_return:
                onBackPressed();
                break;
        }
    }

    public void onClickAdd() {
        Intent intent = new Intent(this, AddScheduleActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 1) {
                String success = data.getStringExtra("success");
                if (success.equals("true")) {
                    renderListView();
                }
            }
        }
    }

    public void renderListView() {
        long calID = CalendarProviderManager.obtainCalendarAccountID(this);
        final List<CalendarEvent> calendarEventList = CalendarProviderManager.queryAccountEvent(this, calID);

        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return calendarEventList.size();
            }

            @Override
            public Object getItem(int position) {
                return calendarEventList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                if (convertView == null) {
                    LayoutInflater inflater = ShowScheduleActivity.this.getLayoutInflater();
                    view = inflater.inflate(R.layout.item_schedule, null);
                } else {
                    view = convertView;
                }

                final CalendarEvent calendarEvent = calendarEventList.get(position);
                TextView month = (TextView) view.findViewById(R.id.month);
                TextView day = (TextView) view.findViewById(R.id.day);
                TextView time = (TextView) view.findViewById(R.id.time);
                TextView title = (TextView) view.findViewById(R.id.title);
                Date date = new Date(calendarEvent.getStart());
                Date endDate = new Date(calendarEvent.getEnd());
                String[] monArray = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"};
                String[] dayArray = new String[]{"Mon", "Tue", "Wed", "Thur", "Fri", "Sat", "Sun"};
                String dayStr = date.getDate() + "";
                String titleStr = calendarEvent.getTitle();
                month.setText(monArray[date.getMonth()]);
                time.setText(dayArray[date.getDay()] + ", " + date.getHours() + ": " + date.getMinutes() + " to " + endDate.getHours() + ": " + endDate.getMinutes());
                day.setText(dayStr);
                title.setText(titleStr);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Schedule schedule = new Schedule(calendarEvent.getId(), calendarEvent.getStart(), calendarEvent.getEnd(), calendarEvent.getTitle(), calendarEvent.getDescription(), calendarEvent.getEventLocation(), calendarEvent.getRRule(), calendarEvent.getAdvanceTime());
                        Intent intent = new Intent(ShowScheduleActivity.this, EditScheduleActivity.class);
                        intent.putExtra("schedule", schedule);
                        startActivityForResult(intent, 1);
                    }
                });
                return view;
            }

        });
    }
}
