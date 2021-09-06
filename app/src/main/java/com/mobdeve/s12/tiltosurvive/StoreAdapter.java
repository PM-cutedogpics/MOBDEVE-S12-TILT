package com.mobdeve.s12.tiltosurvive;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {

    private ArrayList<PowerUpsModel> powerups;
    private int balance;
    private TextView tvBalance;

    private DatabaseHelper database;

    public StoreAdapter(Activity activity, Context context, DatabaseHelper database, ArrayList<PowerUpsModel> powerups, TextView tvBalance) {
        this.powerups = powerups;
        this.balance = 0;
        this.database = database;
        this.tvBalance = tvBalance;
    }

    @NonNull
    @NotNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View itemView = inflater.inflate(R.layout.store_powerup, parent, false);

        StoreViewHolder storeViewHolder = new StoreViewHolder(itemView);

        return storeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StoreViewHolder holder, int position) {
        PowerUpsModel powerup = powerups.get(position);
        holder.tvPowerupName.setText(powerup.getTitle());
        holder.tvPowerupDesc.setText(powerup.getDescription());
        if (powerup.getOwned() == 1) {
            holder.ibPowerupIcon.setImageResource(powerup.getImageId());
            holder.tvPowerupPrice.setText("SOLD");
            holder.tvPowerupPrice.setTextColor(Color.GRAY);
            holder.ibPowerupIcon.setEnabled(false);
        }
        else {
            holder.ibPowerupIcon.setImageResource(powerup.getActivatedImageId());
            holder.tvPowerupPrice.setText(String.valueOf(powerup.getPrice()));
        }

        holder.ibPowerupIcon.setOnClickListener(v -> {
            this.balance =  Integer.parseInt(tvBalance.getText().toString());
            if (this.balance >= powerup.getPrice()) {
                this.balance = Integer.parseInt(tvBalance.getText().toString()) -
                        Integer.parseInt(holder.tvPowerupPrice.getText().toString());
                this.database.updateBalance("1", this.balance);
                this.database.updatePowerupOwned(powerup.getTitle(), 1);
                this.tvBalance.setText(String.valueOf(balance));
                holder.tvPowerupPrice.setTextColor(Color.GRAY);
                holder.tvPowerupPrice.setText("SOLD");
                holder.ibPowerupIcon.setImageResource(powerup.getImageId());
                holder.ibPowerupIcon.setEnabled(false);
                this.database.close();
            }
            else {
                Toast.makeText(v.getContext(), "Not enough credits", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return powerups.size();
    }

    public class StoreViewHolder extends RecyclerView.ViewHolder {
        ImageButton ibPowerupIcon;
        TextView tvPowerupName;
        TextView tvPowerupPrice;
        TextView tvPowerupDesc;

        public StoreViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            this.ibPowerupIcon = itemView.findViewById(R.id.ib_store_powerup);
            this.tvPowerupName = itemView.findViewById(R.id.tv_store_powerup_name);
            this.tvPowerupPrice = itemView.findViewById(R.id.tv_store_powerup_price);
            this.tvPowerupDesc = itemView.findViewById(R.id.tv_store_powerup_description);
        }
    }
}
