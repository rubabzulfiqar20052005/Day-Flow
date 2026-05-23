package com.example.dayflow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayflow.db.HabitEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.VH> {

    public interface Listener {
        void onToggle(HabitEntity habit, boolean done);
        void onDelete(HabitEntity habit);
    }

    private final Listener listener;
    private final List<HabitEntity> data = new ArrayList<>();

    public HabitAdapter(Listener listener) {
        this.listener = listener;
    }

    public void submit(List<HabitEntity> list) {
        data.clear();
        if (list != null) data.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_habit, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        HabitEntity habit = data.get(position);

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
        boolean doneToday = today.equals(habit.lastDoneDate);

        h.tvTitle.setText(habit.title);
        h.tvStreak.setText("Streak: " + habit.streak);

        h.cbDone.setOnCheckedChangeListener(null);
        h.cbDone.setChecked(doneToday);

        h.cbDone.setOnCheckedChangeListener((buttonView, isChecked) ->
                listener.onToggle(habit, isChecked)
        );

        h.btnDelete.setOnClickListener(v -> listener.onDelete(habit));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        CheckBox cbDone;
        TextView tvTitle, tvStreak;
        ImageView btnDelete;

        VH(@NonNull View itemView) {
            super(itemView);
            cbDone = itemView.findViewById(R.id.cbDone);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvStreak = itemView.findViewById(R.id.tvStreak);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}

