package com.tenet.pia.notes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewNote;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.tenet.pia.R;
import com.tenet.pia.dao.GroupDao;
import com.tenet.pia.dao.NoteDao;
import com.tenet.pia.entity.Group;
import com.tenet.pia.entity.Note;

import java.util.ArrayList;


public class ShowNotesActivity extends AppCompatActivity {
    private ImageButton button;
    private ListView lv;
    private NoteDao NoteDao;
    private ArrayList<Note> noteList;
    private String addNoteName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notes);

        button = (ImageButton) findViewById(R.id.add_sg);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to_add();
            }
        });

        refreshData();


    }
}
