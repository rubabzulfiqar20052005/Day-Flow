package com.example.dayflow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.VH> {

    private final List<OnboardingItem> items;

    public OnboardingAdapter(List<OnboardingItem> items) {
        this.items = items;
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView title, desc;
        VH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.onTitle);
            desc = itemView.findViewById(R.id.onDesc);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_onboarding, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.title.setText(items.get(position).title);
        holder.desc.setText(items.get(position).desc);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
