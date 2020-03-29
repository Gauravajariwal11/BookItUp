package com.example.bookitup.ui.books;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookitup.BookActivity;
import com.example.bookitup.BookDatabaseEdit;
import com.example.bookitup.R;
import com.example.bookitup.RecyclerViewConfig;

import java.util.List;

public class BooksFragment extends Fragment {

    private BooksViewModel booksViewModel;
    private RecyclerView mRecyclerView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        booksViewModel =
                ViewModelProviders.of(this).get(BooksViewModel.class);

        View root = inflater.inflate(R.layout.fragment_books, container, false);

        final TextView textView = root.findViewById(R.id.text_gallery);

        booksViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        mRecyclerView = root.findViewById(R.id.recyclerview_mybooks);

        new BookDatabaseEdit().readMyBooks(new BookDatabaseEdit.DataStatus() {
            @Override
            public void DataIsLoaded(List<BookActivity> books, List<String> keys) {
                new RecyclerViewConfig().setConfig(mRecyclerView,getContext(),books,keys,true);
                System.out.println(mRecyclerView+"\n"+getContext()+"\n"+books+"\n"+keys+"\n");
            }

            @Override
            public void DataIsInserted() {
                Toast.makeText(getContext(),"Book has been inserted",Toast.LENGTH_LONG).show();

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
        return root;
    }
}