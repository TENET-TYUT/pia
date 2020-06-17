package com.tenet.pia.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tenet.pia.R;
import com.tenet.pia.dao.NoteDao;
import com.tenet.pia.entity.Note;

import java.util.Date;


public class AddNotesActivity extends AppCompatActivity {
    private NoteDao noteDao;
    private EditText titleText;
    private EditText contentText;
    private ImageButton returnBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        noteDao = new NoteDao(this);

        titleText = (EditText) findViewById(R.id.notes_title);
        contentText = (EditText) findViewById(R.id.notes_content);
        returnBtn = (ImageButton)findViewById(R.id.return_icon);


        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onBackPressed() {
        String title = titleText.getText().toString().trim();
        String content = contentText.getText().toString().trim();
        if (title.length()==0){
            Toast.makeText(this, "创建记事本失败", Toast.LENGTH_SHORT).show();
        } else {
            long createTime = new Date().getTime();
            Note note = new Note(title, content, createTime);
            noteDao.insert(note);
            Intent intent = new Intent();
            intent.putExtra("change", true);
            setResult(1, intent);
        }
        super.onBackPressed();
    }
}
