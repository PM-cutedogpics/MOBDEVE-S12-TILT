package com.mobdeve.s12.tiltosurvive;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PreGameAdapter extends RecyclerView.Adapter<PreGameAdapter.PreGameViewHolder> {

    private ArrayList<PowerUpsModel> powerups;

    public PreGameAdapter(Activity activity, Context context, ArrayList<PowerUpsModel> powerups) {
        this.powerups = powerups;
    }

    @NonNull
    @NotNull
    @Override
    public PreGameViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View itemView = inflater.inflate(R.layout.item_powerup, parent, false);

        PreGameViewHolder preGameViewHolder = new PreGameViewHolder(itemView);

        return preGameViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PreGameViewHolder holder, int position) {
        holder.ivPowerupIcon.setImageResource(powerups.get(position).getImageId());
        holder.tvPowerupName.setText(String.valueOf(powerups.get(position).getTitle()));
    }

    @Override
    public int getItemCount() {
        return powerups.size();
    }

    public class PreGameViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPowerupIcon;
        TextView tvPowerupName;

        public PreGameViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            this.ivPowerupIcon = itemView.findViewById(R.id.iv_powerup);
            this.tvPowerupName = itemView.findViewById(R.id.tv_powerup_name);
        }
    }
}
