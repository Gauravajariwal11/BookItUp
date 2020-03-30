package com.example.bookitup;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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
    private FirebaseAuth firebaseAuth;

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
    public void readMyBooks(final DataStatus dataStatus){
        mReferenceBooks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                books.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    BookActivity book = keyNode.getValue(BookActivity.class);
                    System.out.println(book);
                    System.out.println(keyNode.getKey());
                    try {
                        if (book.getXuid().equals(firebaseAuth.getInstance().getCurrentUser().getUid())) {
                            keys.add(keyNode.getKey());

                            books.add(book);
                        }
                    }
                    catch (NullPointerException e){
                        Log.getStackTraceString(e);
                    }

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
    public void updateBook(String key, BookActivity book, final DataStatus dataStatus)
    {
        mReferenceBooks.child(key).setValue(book).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsUpdated();
            }
        });
    }
    public  void deleteBook(String key, final DataStatus dataStatus)
    {
        mReferenceBooks.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsDeleted();
            }
        });

    }

    public void readBooksFiltered(String searchString, String searchOption, final DataStatus dataStatus){
        mReferenceBooks.orderByChild(searchOption)
                .startAt(searchString)
                .endAt(searchString + "\uf8ff")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                        books.clear();
                        List<String> keys = new ArrayList<>();
                        for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                            BookActivity book = keyNode.getValue(BookActivity.class);
                            keys.add(keyNode.getKey());
                            books.add(book);
                        }
                        dataStatus.DataIsLoaded(books,keys);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
        });
    }
}