package com.tenet.pia.notes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.tenet.pia.R;
import com.tenet.pia.dao.NoteDao;
import com.tenet.pia.entity.Note;

public class EditNotesActivity extends AppCompatActivity {
    private NoteDao noteDao;
    private EditText titleText;
    private EditText contentText;
    private ImageButton returnBtn;
    private Button deleteButton;
    private Note note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes);

        //设置手机应用内部状态栏字体图标为黑色
        changeStatusBarTextImgColor(true);

        titleText = (EditText) findViewById(R.id.notes_title);
        contentText = (EditText) findViewById(R.id.notes_content);
        returnBtn = (ImageButton)findViewById(R.id.return_icon);
        deleteButton=(Button)findViewById(R.id.delete_btn);
        noteDao = new NoteDao(this);
        note = (Note) getIntent().getSerializableExtra("note");

        titleText.setText(note.getNoteTitle());
        contentText.setText(note.getNoteContent());

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(EditNotesActivity.this)
                        .setTitle("确认删除")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int result =  noteDao.delete(note);
                                if(result > 0) {
                                    Toast.makeText(EditNotesActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent();
                                    intent.putExtra("change", true);
                                    setResult(1, intent);
                                } else {
                                    Toast.makeText(EditNotesActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                                }
                                finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        String title = titleText.getText().toString().trim();
        String content = contentText.getText().toString().trim();
        if(title.equals(note.getNoteTitle()) && content.equals(note.getNoteContent())) {
            super.onBackPressed();
            return;
        } else {
            if (title.length() == 0) {
                Toast.makeText(this, "标题不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            note.setNoteTitle(title);
            note.setNoteContent(content);
            int result = noteDao.update(note);
            if(result > 0) {
                Intent intent = new Intent();
                intent.putExtra("change", true);
                setResult(1, intent);
                Toast.makeText(this, "编辑成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "编辑失败", Toast.LENGTH_SHORT).show();
            }
            super.onBackPressed();
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
