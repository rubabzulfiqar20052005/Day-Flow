package com.example.dayflow;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayflow.db.HabitEntity;
import com.example.dayflow.db.HabitRepository;

public class HabitsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits);

        HabitRepository repo = new HabitRepository(this);

        RecyclerView rv = findViewById(R.id.rvHabitsList);
        rv.setLayoutManager(new LinearLayoutManager(this));

        HabitAdapter adapter = new HabitAdapter(new HabitAdapter.Listener() {
            @Override
            public void onToggle(HabitEntity habit, boolean done) {
                repo.toggleDoneToday(habit, done);

                if (done) {
                    SparkleCongrats.show(HabitsActivity.this,
                            "Congratulations, habit is completed ✨🎉✨🎉✨");
                }
            }

            @Override
            public void onDelete(HabitEntity habit) {
                repo.delete(habit);
            }
        });

        rv.setAdapter(adapter);

        repo.getHabits().observe(this, adapter::submit);
    }
}
