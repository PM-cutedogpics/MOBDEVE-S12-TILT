package com.mobdeve.s12.tiltosurvive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AchievementsAdapter extends RecyclerView.Adapter<AchievementsAdapter.AchievementsViewHolder> {

    private ArrayList<AchievementModel> achievements;

    private Context context;
    private Activity activity;

    public AchievementsAdapter(Activity activity, Context context, ArrayList<AchievementModel> achievements) {
        this.activity = activity;
        this.context = context;
        this.achievements = achievements;
    }

    @NonNull
    @NotNull
    @Override
    public AchievementsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_achievement, parent, false);
        AchievementsViewHolder achievementsViewHolder = new AchievementsViewHolder(itemView);
        return achievementsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AchievementsViewHolder holder, int position) {
        holder.tvAchievement.setText(String.valueOf(achievements.get(position).getTitle()));
        if (achievements.get(position).getAchieved() == 1) {
            holder.tvAchievement.setTextColor(Color.GREEN);
        }
        holder.tvAchievement.setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewAchievementActivity.class);
            intent.putExtra("title", String.valueOf(achievements.get(position).getTitle()));
            intent.putExtra("description", String.valueOf(achievements.get(position).getDescription()));
            activity.startActivityForResult(intent, 1);
        });
    }

    @Override
    public int getItemCount() {
        return achievements.size();
    }

    public class AchievementsViewHolder extends RecyclerView.ViewHolder {
        TextView tvAchievement;
        LinearLayout achievementLayout;

        public AchievementsViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            tvAchievement = itemView.findViewById(R.id.tv_item_achievement);
            achievementLayout = itemView.findViewById(R.id.ll_item_achievement);
        }
    }
}
