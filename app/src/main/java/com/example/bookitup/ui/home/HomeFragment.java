package com.example.bookitup.ui.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookitup.BookActivity;
import com.example.bookitup.Books;
import com.example.bookitup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

public class HomeFragment extends Fragment {

    private EditText mSearchField;
    private ImageButton mSearchBtn;
    private Spinner mSearchOptions;

    private RecyclerView mResultList;

    private DatabaseReference mUserDatabase;

    private Context homeContext;


    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseStorage mStorage;
    private StorageReference mReference;

    FirebaseRecyclerAdapter<BookActivity,BooksViewHolder> adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        homeContext = getContext();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Booklist");


        mSearchField = view.findViewById(R.id.search_field);
        mSearchBtn = view.findViewById(R.id.search_btn);

        //drop down menu
        mSearchOptions = view.findViewById(R.id.search_options);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(homeContext,
                R.array.search_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSearchOptions.setAdapter(adapter);
        mSearchOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String option = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), option, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mResultList = view.findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);

        mResultList.setLayoutManager(new LinearLayoutManager(homeContext));

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchText = mSearchField.getText().toString();

                firebaseBooksSearch(searchText);
            }
        });
        return view;
    }

    private void firebaseBooksSearch(String searchText) {

        Toast.makeText(homeContext, "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = mUserDatabase.orderByChild("xbook")
                .startAt(searchText)
                .endAt(searchText + "\uf8ff");




            adapter = new FirebaseRecyclerAdapter<BookActivity, BooksViewHolder>(

                    BookActivity.class,
                    R.layout.list_view,
                    BooksViewHolder.class,
                    firebaseSearchQuery

            ) {
                @Override
                protected void populateViewHolder(BooksViewHolder viewHolder, BookActivity model, int position) {

                    viewHolder.setDetails(homeContext,
                            model.getXbook(),
                            model.getXauthor(),
                            model.getedition(),
                            model.getXisbn(),
                            model.getImage());
                }
            };

            mResultList.setAdapter(adapter);


    }
    public static class BooksViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public BooksViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDetails(Context ctx, String bookName, String authorName, String edition, String isbn, String coverImage){

            TextView bookNameTV = (TextView) mView.findViewById(R.id.book_name_text);
            TextView authorNameTV = (TextView) mView.findViewById(R.id.author_name);
            TextView editionTV = (TextView) mView.findViewById(R.id.edition);
            TextView isbnTV = (TextView) mView.findViewById(R.id.isbn);
            ImageView coverIM = (ImageView) mView.findViewById(R.id.cover_image);


            bookNameTV.setText(bookName);
            authorNameTV.setText(authorName);
            editionTV.setText(edition +" edition");
            isbnTV.setText(isbn);

            Glide.with(ctx).load(coverImage).error(R.drawable.ic_nocover).into(coverIM);


        }




    }

}