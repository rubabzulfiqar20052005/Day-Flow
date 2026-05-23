package com.example.dayflow.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY createdAt DESC")
    LiveData<List<NoteEntity>> getAllNotes();

    @Insert
    long insert(NoteEntity note);

    @Delete
    void delete(NoteEntity note);
}

