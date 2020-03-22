package com.example.bookitup.ui.home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookitup.R;

public class BooksViewHolder extends RecyclerView.ViewHolder {

    View mView;

    public BooksViewHolder(View itemView) {
        super(itemView);

        mView = itemView;

    }

    public void setDetails(Context ctx, String bookName, String authorName, String edition, String isbn, String coverImage) {

        TextView bookNameTV = (TextView) mView.findViewById(R.id.book_name_text);
        TextView authorNameTV = (TextView) mView.findViewById(R.id.author_name);
        TextView editionTV = (TextView) mView.findViewById(R.id.edition);
        TextView isbnTV = (TextView) mView.findViewById(R.id.isbn);
        ImageView coverIM = (ImageView) mView.findViewById(R.id.cover_image);


        bookNameTV.setText(bookName);
        authorNameTV.setText(authorName);
        editionTV.setText(edition + " edition");
        isbnTV.setText(isbn);

        Glide.with(ctx).load(coverImage).error(R.drawable.ic_nocover).into(coverIM);


    }
}



