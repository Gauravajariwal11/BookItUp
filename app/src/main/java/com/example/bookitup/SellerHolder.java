package com.example.bookitup;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SellerHolder extends RecyclerView.ViewHolder {
    TextView name, condition, date, description;
    ImageButton send;

    public SellerHolder(@NonNull View itemView) {
        super(itemView);

        this.name = itemView.findViewById(R.id.sellerTV);
        this.condition = itemView.findViewById(R.id.conditionTV);
        this.date = itemView.findViewById(R.id.dateTV);
        this.description = itemView.findViewById(R.id.descriptionTV);
        this.send = itemView.findViewById(R.id.sendEmail);
    }
}
