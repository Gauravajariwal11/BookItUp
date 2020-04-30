package com.example.bookitup;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.model.Model;
import com.example.bookitup.ui.home.BookDetailsView;

import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class SellerAdapter extends RecyclerView.Adapter<SellerHolder> {

    Context c;
    ArrayList<SellerModel> models; //this array list create a list of array with pare define in deller model class

    //parameterize constructor


    public SellerAdapter(Context c, ArrayList<SellerModel> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public SellerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_info, null);

        return new SellerHolder(view); //return view to holder class
    }

    @Override
    public void onBindViewHolder(@NonNull SellerHolder holder, final int position) {
        holder.name.setText(models.get(position).getName());
        holder.condition.setText(models.get(position).getCondition());
        holder.date.setText(models.get(position).getDate());
        holder.description.setText(models.get(position).getDescription());
        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{models.get(position).getEmail()});
                i.putExtra(Intent.EXTRA_SUBJECT, "BookItUp - Inquiry for: " + models.get(position).getBookName().trim());
                i.putExtra(Intent.EXTRA_TEXT   , "Is it still available?");
                try {
                    c.startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
