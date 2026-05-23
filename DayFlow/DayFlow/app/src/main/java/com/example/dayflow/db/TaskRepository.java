package com.example.dayflow.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository {

    private final TaskDao taskDao;
    private final LiveData<List<TaskEntity>> allTasks;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public TaskRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        taskDao = db.taskDao();
        allTasks = taskDao.getAllTasks();
    }

    public LiveData<List<TaskEntity>> getAllTasks() {
        return allTasks;
    }

    public void insert(TaskEntity task) {
        executor.execute(() -> taskDao.insert(task));
    }

    public void update(TaskEntity task) {
        executor.execute(() -> taskDao.update(task));
    }

    public void delete(TaskEntity task) {
        executor.execute(() -> taskDao.delete(task));
    }
}

