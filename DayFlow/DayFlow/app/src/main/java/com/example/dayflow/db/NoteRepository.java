package com.example.dayflow.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {

    private final NoteDao noteDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public NoteRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        noteDao = db.noteDao();
    }

    public LiveData<List<NoteEntity>> getNotes() {
        return noteDao.getAllNotes();
    }

    public void insert(NoteEntity note) {
        executor.execute(() -> noteDao.insert(note));
    }

    public void delete(NoteEntity note) {
        executor.execute(() -> noteDao.delete(note));
    }
}

