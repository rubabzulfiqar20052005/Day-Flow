package com.example.dayflow;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayflow.db.NoteEntity;
import com.example.dayflow.db.NoteRepository;

public class NotesActivity extends AppCompatActivity {

    private NoteRepository repo;
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        repo = new NoteRepository(this);

        RecyclerView rvNotes = findViewById(R.id.rvNotes);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NoteAdapter(note -> repo.delete(note));
        rvNotes.setAdapter(adapter);

        repo.getNotes().observe(this, notes -> adapter.submit(notes));

        findViewById(R.id.btnAddNote).setOnClickListener(v ->
                startActivity(new Intent(this, AddEditNotesActivity.class)));
    }
}
