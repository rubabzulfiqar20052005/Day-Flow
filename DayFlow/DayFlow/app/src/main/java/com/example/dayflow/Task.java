package com.example.dayflow;

public class Task {
    public long id;
    public String title;
    public String notes;
    public String priority; // Low/Medium/High
    public boolean done;

    public Task(long id, String title, String notes, String priority, boolean done) {
        this.id = id;
        this.title = title;
        this.notes = notes;
        this.priority = priority;
        this.done = done;
    }
}

