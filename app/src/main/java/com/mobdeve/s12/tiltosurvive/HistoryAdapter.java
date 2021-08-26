package com.mobdeve.s12.tiltosurvive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private ArrayList<HistoryModel> history;

    private Context context;
    private Activity activity;

    public HistoryAdapter(Activity activity, Context context, ArrayList<HistoryModel> history) {
        this.activity = activity;
        this.context = context;
        this.history = history;
    }

    @NonNull
    @NotNull
    @Override
    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.history_log, parent, false);
        HistoryAdapter.HistoryViewHolder historyViewHolder = new HistoryAdapter.HistoryViewHolder(itemView);
        return historyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HistoryAdapter.HistoryViewHolder holder, int position) {
        holder.tvTime.setText(String.valueOf(history.get(position).getTime()));
        holder.tvDate.setText(String.valueOf(history.get(position).getDate()));
        holder.tvScore.setText(String.valueOf(history.get(position).getScore()));
    }

    @Override
    public int getItemCount() {
        return history.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime;
        TextView tvDate;
        TextView tvScore;
        LinearLayout historyLayout;

        public HistoryViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            tvTime = itemView.findViewById(R.id.tv_history_time);
            tvDate = itemView.findViewById(R.id.tv_history_date);
            tvScore = itemView.findViewById(R.id.tv_history_score);
            historyLayout = itemView.findViewById(R.id.history_layout);
        }
    }
}
