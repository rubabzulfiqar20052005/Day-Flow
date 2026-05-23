package com.example.dayflow.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "habits")
public class HabitEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;

    // daily habit tracking
    public int streak;            // 0,1,2...
    public String lastDoneDate;   // "yyyy-MM-dd" (null allowed)

    public int isActive;          // 1 = active, 0 = archived (optional)

    public HabitEntity(String title) {
        this.title = title;
        this.streak = 0;
        this.lastDoneDate = null;
        this.isActive = 1;
    }
}

