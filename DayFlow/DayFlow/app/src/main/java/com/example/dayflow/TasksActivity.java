package com.example.dayflow;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayflow.db.TaskEntity;
import com.example.dayflow.db.TaskRepository;

public class TasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        TaskRepository repo = new TaskRepository(this);

        RecyclerView rv = findViewById(R.id.rvTasksList);
        rv.setLayoutManager(new LinearLayoutManager(this));

        TaskAdapter adapter = new TaskAdapter(new TaskAdapter.Listener() {
            @Override
            public void onToggleDone(TaskEntity task) {
                task.done = !task.done;
                repo.update(task);

                if (task.done) {
                    SparkleCongrats.show(TasksActivity.this,
                            "Congratulations, task is completed ✨🎉✨🎉✨");
                }
            }

            @Override
            public void onDelete(TaskEntity task) {
                repo.delete(task);
            }
        });

        rv.setAdapter(adapter);

        repo.getAllTasks().observe(this, adapter::submitList);
    }
}
