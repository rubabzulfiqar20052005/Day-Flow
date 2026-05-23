package com.example.dayflow.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM tasks ORDER BY createdAt DESC")
    LiveData<List<TaskEntity>> getAllTasks();

    @Insert
    long insert(TaskEntity task);

    @Update
    int update(TaskEntity task);

    @Delete
    int delete(TaskEntity task);

    @Query("DELETE FROM tasks")
    void deleteAll();
}

