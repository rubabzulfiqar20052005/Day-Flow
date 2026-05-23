package com.example.dayflow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.dayflow.db.HabitEntity;
import com.example.dayflow.db.HabitRepository;
import com.example.dayflow.db.TaskEntity;
import com.example.dayflow.db.TaskRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView tvProgressPercent, tvTasksCount, tvHabitsCount, tvNotesCount;

    private TaskRepository taskRepo;
    private HabitRepository habitRepo;

    private List<TaskEntity> currentTasks;
    private List<HabitEntity> currentHabits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView btnSettings = findViewById(R.id.btnSettings);
        if (btnSettings != null) {
            btnSettings.setOnClickListener(v ->
                    startActivity(new Intent(HomeActivity.this, SettingsActivity.class)));
        }

        progressBar = findViewById(R.id.progressBar);
        tvProgressPercent = findViewById(R.id.tvProgressPercent);
        tvTasksCount = findViewById(R.id.tvTasksCount);
        tvHabitsCount = findViewById(R.id.tvHabitsCount);
        tvNotesCount = findViewById(R.id.tvNotesCount);

        // ✅ Buttons
        Button btnAddTask = findViewById(R.id.btnAddTask);
        View btnAddHabit = findViewById(R.id.btnAddHabit);

        btnAddTask.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, AddEditTaskActivity.class)));

        if (btnAddHabit != null) {
            btnAddHabit.setOnClickListener(v ->
                    startActivity(new Intent(HomeActivity.this, AddEditHabitActivity.class)));
        }

        // ✅ Repos
        taskRepo = new TaskRepository(this);
        habitRepo = new HabitRepository(this);

        // ✅ Observe counts + progress
        taskRepo.getAllTasks().observe(this, tasks -> {
            currentTasks = tasks;
            int total = (tasks == null) ? 0 : tasks.size();
            tvTasksCount.setText(String.valueOf(total));
            updateProgress();
        });

        habitRepo.getHabits().observe(this, habits -> {
            currentHabits = habits;
            int total = (habits == null) ? 0 : habits.size();
            tvHabitsCount.setText(String.valueOf(total));
            updateProgress();
        });

        // Notes count abhi 0 (jab Notes DB banayengi to yahan observe karwa denge)
        tvNotesCount.setText("0");

        // ✅ CARD CLICKS (THIS IS WHAT YOU WANT)
        View cardTasks = findViewById(R.id.cardTasks);
        View cardHabits = findViewById(R.id.cardHabits);
        View cardNotes = findViewById(R.id.cardNotes);

        if (cardTasks != null) {
            cardTasks.setOnClickListener(v ->
                    startActivity(new Intent(HomeActivity.this, TasksActivity.class)));
        }

        if (cardHabits != null) {
            cardHabits.setOnClickListener(v ->
                    startActivity(new Intent(HomeActivity.this, HabitsActivity.class)));
        }

        if (cardNotes != null) {
            cardNotes.setOnClickListener(v ->
                    startActivity(new Intent(HomeActivity.this, NotesActivity.class)));
        }
    }

    private void updateProgress() {
        int taskTotal = (currentTasks == null) ? 0 : currentTasks.size();
        int taskDone = 0;
        if (currentTasks != null) {
            for (TaskEntity t : currentTasks) if (t.done) taskDone++;
        }

        int habitTotal = (currentHabits == null) ? 0 : currentHabits.size();
        int habitDoneToday = 0;
        String today = todayStr();
        if (currentHabits != null) {
            for (HabitEntity h : currentHabits) {
                if (today.equals(h.lastDoneDate)) habitDoneToday++;
            }
        }

        int total = taskTotal + habitTotal;
        int done = taskDone + habitDoneToday;

        int progress = (total == 0) ? 0 : (int) ((done * 100f) / total);
        progressBar.setProgress(progress);
        tvProgressPercent.setText(progress + "% completed");
    }

    private String todayStr() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
    }
}
