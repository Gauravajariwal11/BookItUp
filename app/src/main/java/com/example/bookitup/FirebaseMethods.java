package com.example.bookitup;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseMethods {
    //firebase
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String userName;
    private FirebaseDatabase database;

    private Context mContext;

    public FirebaseMethods(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        database = FirebaseDatabase.getInstance();
        mContext = context;

//        if(mAuth.getCurrentUser() != null){
//            userID = mAuth.getCurrentUser().getUid();
//        }
    }

    public String getUserName(final String userID){
        final DatabaseReference databaseUserRef = database.getReference("Users");
        databaseUserRef.orderByChild(userID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            String fName = data.getValue(UserInformation.class).getfname() + " ";
                            String lName = data.getValue(UserInformation.class).getlname();
                            userName = fName + lName;
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        return userName;

    }
}
