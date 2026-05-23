package com.example.dayflow;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dayflow.db.HabitEntity;
import com.example.dayflow.db.HabitRepository;

public class AddEditHabitActivity extends AppCompatActivity {

    public static final String EXTRA_HABIT_ID = "habit_id";
    public static final String EXTRA_HABIT_TITLE = "habit_title";

    private EditText etTitle;
    private HabitRepository repo;
    private int habitId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_habit);

        etTitle = findViewById(R.id.etHabitTitle);
        Button btnSave = findViewById(R.id.btnSaveHabit);

        repo = new HabitRepository(this);

        // (optional) edit mode
        habitId = getIntent().getIntExtra(EXTRA_HABIT_ID, -1);
        String title = getIntent().getStringExtra(EXTRA_HABIT_TITLE);
        if (title != null) etTitle.setText(title);

        btnSave.setOnClickListener(v -> {
            String t = etTitle.getText().toString().trim();
            if (t.isEmpty()) {
                Toast.makeText(this, "Habit name required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (habitId == -1) {
                repo.insert(new HabitEntity(t));
            } else {
                HabitEntity h = new HabitEntity(t);
                h.id = habitId;

                repo.update(h);
            }
            finish();
        });
    }
}

