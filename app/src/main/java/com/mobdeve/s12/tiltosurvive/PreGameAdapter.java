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

    private DatabaseHelper database;

    public PreGameAdapter(Activity activity, Context context, DatabaseHelper database, ArrayList<PowerUpsModel> powerups) {
        this.powerups = powerups;
        this.counter = 0;
        this.database = database;
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
        PowerUpsModel powerup = powerups.get(position);
        if (powerup.isSelected() == 0) {
            holder.ibPowerupIcon.setImageResource(powerup.getImageId());
        }
        else {
            holder.ibPowerupIcon.setImageResource(powerup.getActivatedImageId());
        }
        holder.tvPowerupName.setText(String.valueOf(powerup.getTitle()));
        holder.ibPowerupIcon.setOnClickListener(v -> {
            System.out.println(counter);
            if (counter <= 3 && powerup.isSelected() == 1) {
                powerup.setSelected(0);
                database.updatePowerUpActive(powerup.getTitle(), powerup.isSelected());
                counter--;
                System.out.println(counter);
                holder.ibPowerupIcon.setImageResource(powerup.getImageId());
            }
            else if (counter < 3 && powerup.isSelected() == 0) {
                powerup.setSelected(1);
                System.out.println();
                database.updatePowerUpActive(powerup.getTitle(), powerup.isSelected());
                counter++;
                System.out.println(counter);
                holder.ibPowerupIcon.setImageResource(powerup.getActivatedImageId());

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
