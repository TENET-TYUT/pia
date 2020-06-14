package com.tenet.pia.notes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.tenet.pia.R;
import com.tenet.pia.dao.NoteDao;
import com.tenet.pia.entity.Note;
import com.tenet.pia.notes.AddNotesActivity;
import com.tenet.pia.notes.ShowNotesActivity;

import java.util.ArrayList;


public class ShowNotesActivity extends AppCompatActivity {
    private ImageButton button;
    private ListView lv;
    private NoteDao noteDao;
    private ArrayList<Note> noteList;
    private String addNoteName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notes);

        button = (ImageButton) findViewById(R.id.add_nt);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to_add();
            }
        });

        refreshData();


    }

    public void refreshData() {
        noteDao = new NoteDao(this);
        noteList = (ArrayList<Note>) noteDao.query();

        lv = (ListView) findViewById(R.id.Lv_sn);

        lv.setAdapter(new BaseAdapter() {

            @Override
            public int getCount() {
                return noteList.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                if (convertView == null) {
                    LayoutInflater inflater = ShowNotesActivity.this.getLayoutInflater();
                    view = inflater.inflate(R.layout.item_show_note, null);
                    //view = View.inflate(getBaseContext(),R.layout.item,null);
                } else {
                    view = convertView;
                }
                Note nt = noteList.get(position);
                TextView name = (TextView) view.findViewById(R.id.item_show_note_tv);
                name.setText(nt.getGroupName());
                return view;
            }
        });
    }

    public void to_add() {
        Intent intent = new Intent(this, AddGroupActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(addGroupName.length() > 0) {
            refreshData();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(addGroupName.length() > 0) {
            refreshData();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(resultCode == 1) {
                addGroupName = data.getStringExtra("groupName");
            }
        }
    }
}
