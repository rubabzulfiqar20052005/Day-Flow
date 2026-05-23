package com.example.dayflow.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class TaskEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String title;
    public String notes;
    public String priority; // Low/Medium/High
    public boolean done;

    public long createdAt;

    public TaskEntity(String title, String notes, String priority, boolean done, long createdAt) {
        this.title = title;
        this.notes = notes;
        this.priority = priority;
        this.done = done;
        this.createdAt = createdAt;
    }
}

