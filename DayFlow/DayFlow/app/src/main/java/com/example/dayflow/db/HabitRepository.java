package com.example.dayflow.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HabitRepository {

    private final HabitDao habitDao;
    private final LiveData<List<HabitEntity>> habits;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public HabitRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        habitDao = db.habitDao();
        habits = habitDao.getActiveHabits();
    }

    public LiveData<List<HabitEntity>> getHabits() {
        return habits;
    }

    public void insert(HabitEntity habit) {
        executor.execute(() -> habitDao.insert(habit));
    }

    public void update(HabitEntity habit) {
        executor.execute(() -> habitDao.update(habit));
    }

    public void delete(HabitEntity habit) {
        executor.execute(() -> habitDao.delete(habit));
    }

    public void deleteById(int id) {
        executor.execute(() -> habitDao.deleteById(id));
    }

    // ✅ Daily checkbox toggle logic (streak + lastDoneDate)
    public void toggleDoneToday(HabitEntity habit, boolean done) {
        executor.execute(() -> {
            String today = todayStr();
            String yesterday = yesterdayStr();

            if (done) {
                // already done today -> no change
                if (today.equals(habit.lastDoneDate)) return;

                // if done yesterday, streak++ else streak=1
                if (yesterday.equals(habit.lastDoneDate)) {
                    habit.streak = habit.streak + 1;
                } else {
                    habit.streak = 1;
                }
                habit.lastDoneDate = today;
                habitDao.update(habit);

            } else {
                // uncheck today -> simplest: reset
                if (today.equals(habit.lastDoneDate)) {
                    habit.lastDoneDate = null;
                    habit.streak = 0;
                    habitDao.update(habit);
                }
            }
        });
    }

    private String todayStr() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
    }

    private String yesterdayStr() {
        long oneDay = 24L * 60L * 60L * 1000L;
        return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date(System.currentTimeMillis() - oneDay));
    }
}
