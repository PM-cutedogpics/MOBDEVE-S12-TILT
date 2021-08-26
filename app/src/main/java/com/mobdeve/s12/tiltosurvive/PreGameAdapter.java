package com.mobdeve.s12.tiltosurvive;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PreGameAdapter extends RecyclerView.Adapter<PreGameAdapter.PreGameViewHolder> {

    private ArrayList<PowerUpsModel> powerups;
    private int counter;

    public PreGameAdapter(Activity activity, Context context, ArrayList<PowerUpsModel> powerups) {
        this.powerups = powerups;
        this.counter = 0;
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
        holder.ibPowerupIcon.setImageResource(powerups.get(position).getImageId());
        holder.tvPowerupName.setText(String.valueOf(powerups.get(position).getTitle()));
        holder.ibPowerupIcon.setOnClickListener(v -> {
            System.out.println(counter);
            if (counter <= 3 && powerups.get(position).isSelected()) {
                powerups.get(position).setSelected(false);
                counter--;
                System.out.println(counter);
                holder.ibPowerupIcon.setImageResource(powerups.get(position).getImageId());
            }
            else if (counter < 3 && !powerups.get(position).isSelected()) {
                powerups.get(position).setSelected(true);
                counter++;
                System.out.println(counter);
                holder.ibPowerupIcon.setImageResource(powerups.get(position).getActivatedImageId());

            }
            else {
                Toast.makeText(v.getContext(), "A maximum of only 3 power-ups can be selected.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return powerups.size();
    }

    public class PreGameViewHolder extends RecyclerView.ViewHolder {
        ImageButton ibPowerupIcon;
        TextView tvPowerupName;

        public PreGameViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            this.ibPowerupIcon = itemView.findViewById(R.id.ib_powerup);
            this.tvPowerupName = itemView.findViewById(R.id.tv_powerup_name);
        }
    }
}
