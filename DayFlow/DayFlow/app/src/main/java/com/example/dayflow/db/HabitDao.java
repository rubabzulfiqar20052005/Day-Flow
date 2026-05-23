package com.example.dayflow.db;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface HabitDao {

    @Query("SELECT * FROM habits WHERE isActive = 1 ORDER BY id DESC")
    LiveData<List<HabitEntity>> getActiveHabits();

    @Query("SELECT * FROM habits WHERE id = :id LIMIT 1")
    HabitEntity getById(int id);

    @Insert
    long insert(HabitEntity habit);

    @Update
    void update(HabitEntity habit);

    @Delete
    void delete(HabitEntity habit);

    @Query("DELETE FROM habits WHERE id = :id")
    void deleteById(int id);
}
