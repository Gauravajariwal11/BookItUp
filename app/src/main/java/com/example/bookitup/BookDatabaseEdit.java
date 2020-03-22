package com.example.bookitup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookDatabaseEdit {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceBooks;
    private List<BookActivity> books = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<BookActivity> books, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }
    public BookDatabaseEdit() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceBooks = mDatabase.getReference("Booklist");
    }
    public void readBooks(final DataStatus dataStatus){
        mReferenceBooks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                books.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    BookActivity book = keyNode.getValue(BookActivity.class);
                    books.add(book);
                }
                dataStatus.DataIsLoaded(books,keys);
                System.out.println(keys);
                System.out.println(books);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}