package com.example.dayflow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayflow.db.NoteEntity;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.VH> {

    public interface Listener {
        void onDelete(NoteEntity note);
    }

    private final Listener listener;
    private List<NoteEntity> list = new ArrayList<>();

    public NoteAdapter(Listener listener) {
        this.listener = listener;
    }

    public void submit(List<NoteEntity> notes) {
        list = (notes == null) ? new ArrayList<>() : notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        NoteEntity n = list.get(position);
        h.tvTitle.setText(n.title);
        h.tvContent.setText(n.content);

        h.btnDelete.setOnClickListener(v -> listener.onDelete(n));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvTitle, tvContent, btnDelete;
        VH(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvNoteTitle);
            tvContent = itemView.findViewById(R.id.tvNoteContent);
            btnDelete = itemView.findViewById(R.id.btnDeleteNote);
        }
    }
}

