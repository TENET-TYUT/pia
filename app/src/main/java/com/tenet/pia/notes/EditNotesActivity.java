package com.tenet.pia.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tenet.pia.R;
import com.tenet.pia.dao.NoteDao;

public class EditNotesActivity extends AppCompatActivity {
    private NoteDao noteDao;
    private EditText noteTitle;
    private EditText noteContent;
    private ImageButton returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes);


    }
}
