package com.example.dayflow;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayflow.db.TaskEntity;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.VH> {

    public interface Listener {
        void onToggleDone(TaskEntity task);
        void onDelete(TaskEntity task);
    }

    private final Listener listener;
    private final List<TaskEntity> list = new ArrayList<>();

    public TaskAdapter(Listener listener) {
        this.listener = listener;
    }

    public void submitList(List<TaskEntity> newList) {
        list.clear();
        if (newList != null) list.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        TaskEntity t = list.get(position);

        h.tvTitle.setText(t.title);
        h.tvMeta.setText(t.priority + " • Today");

        if (t.done) {
            h.ivDone.setImageResource(R.drawable.ic_check_circle);
            h.tvTitle.setPaintFlags(h.tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            h.tvTitle.setAlpha(0.6f);
        } else {
            h.ivDone.setImageResource(R.drawable.ic_circle);
            h.tvTitle.setPaintFlags(h.tvTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            h.tvTitle.setAlpha(1f);
        }

        h.ivDone.setOnClickListener(v -> listener.onToggleDone(t));
        h.ivDelete.setOnClickListener(v -> listener.onDelete(t));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView ivDone, ivDelete;
        TextView tvTitle, tvMeta;

        VH(@NonNull View itemView) {
            super(itemView);
            ivDone = itemView.findViewById(R.id.ivDone);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvMeta = itemView.findViewById(R.id.tvMeta);
        }
    }
}
