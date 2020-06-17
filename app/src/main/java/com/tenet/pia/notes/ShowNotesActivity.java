package com.tenet.pia.notes;

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
import com.tenet.pia.dao.NoteDao;
import com.tenet.pia.entity.Note;

import java.util.ArrayList;
import java.util.Date;


public class ShowNotesActivity extends AppCompatActivity {
    private ImageButton buttonAdd;
    private ImageButton buttonReturn;
    private ListView lv;
    private NoteDao noteDao;
    private ArrayList<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notes);
        noteDao = new NoteDao(this);

        buttonAdd = (ImageButton) findViewById(R.id.add_nt);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to_add();
            }
        });

        buttonReturn = (ImageButton) findViewById(R.id.btn_return);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        refreshData();


    }

    public void refreshData() {

        noteList = (ArrayList<Note>) noteDao.query();

        lv = (ListView) findViewById(R.id.Lv_sn);

        lv.setAdapter(new BaseAdapter() {

            @Override
            public int getCount() {
                return noteList.size();
            }

            @Override
            public Object getItem(int position) {
                return noteList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                if (convertView == null) {
                    LayoutInflater inflater = ShowNotesActivity.this.getLayoutInflater();
                    view = inflater.inflate(R.layout.item_show_note, null);
                } else {
                    view = convertView;
                }
                final Note nt = noteList.get(position);
                TextView noteTitle = (TextView) view.findViewById(R.id.tv_noteTitle);
                noteTitle.setText(nt.getNoteTitle());
                TextView noteContent = (TextView) view.findViewById(R.id.tv_noteContent);
                noteContent.setText(nt.getNoteContent());
                TextView noteCreateTime = (TextView) view.findViewById(R.id.tv_createTime);
                Date createDate = new Date(nt.getCreateTime());
                noteCreateTime.setText(createDate.getMonth() + 1 + "月" + createDate.getDate() + "日");
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(ShowNotesActivity.this,EditNotesActivity.class);
                        intent.putExtra("note",nt);
                        startActivityForResult(intent,1);
                    }
                });
                return view;
            }
        });
    }

    public void to_add() {
        Intent intent = new Intent(this, AddNotesActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            if(requestCode==1){
                if(resultCode==1){
                    boolean isChange = data.getBooleanExtra("change", true);
                    if(isChange) {
                        refreshData();
                    }
                }
            }
        }
    }
}