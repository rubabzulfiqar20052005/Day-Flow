package com.example.dayflow;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dayflow.db.NoteEntity;
import com.example.dayflow.db.NoteRepository;

public class AddEditNotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_notes);

        EditText etTitle = findViewById(R.id.etNoteTitle);
        EditText etContent = findViewById(R.id.etNoteContent);
        Button btnSave = findViewById(R.id.btnSaveNote);

        NoteRepository repo = new NoteRepository(this);

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String content = etContent.getText().toString().trim();

            if (title.isEmpty()) {
                etTitle.setError("Title required");
                return;
            }
            if (content.isEmpty()) {
                etContent.setError("Content required");
                return;
            }

            repo.insert(new NoteEntity(title, content, System.currentTimeMillis()));
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
