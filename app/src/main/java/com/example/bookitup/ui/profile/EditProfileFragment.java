package com.example.bookitup.ui.profile;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bookitup.UserInformation;
import com.google.android.gms.tasks.OnSuccessListener;

import com.example.bookitup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class EditProfileFragment extends Fragment {
    private ImageView profileIV;
    private EditText fNameET, lNameET, schoolET, majorET;
    private Button saveBtn;
    private TextView emailTV;
    private ProgressBar progressBar;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseStorage mStorage;
    private StorageReference mReference;

    //vars
    private UserInformation user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        profileIV = view.findViewById(R.id.update_imageView);
        fNameET = view.findViewById(R.id.fnameTV);
        lNameET = view.findViewById(R.id.lnameTV);
        emailTV = view.findViewById(R.id.textViewEmail);
        schoolET = view.findViewById(R.id.uniTV);
        majorET = view.findViewById(R.id.majorTV);
        progressBar = view.findViewById(R.id.progressBar);

        setupFirebaseAuth();


        saveBtn = view.findViewById(R.id.btnSave);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileSetting();
            }
        });
    return view;
    }

     /*
    ------------------------------------ Firebase ---------------------------------------------
     */

    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("Users").child(mAuth.getUid());
        mStorage = FirebaseStorage.getInstance();
        mReference = mStorage.getReference();
        mReference.child(mAuth.getUid()).child("Images").child("Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Using "Picasso" (http://square.github.io/picasso/) after adding the dependency in the Gradle.
                // ".fit().centerInside()" fits the entire image into the specified area.
                // Finally, add "READ" and "WRITE" external storage permissions in the Manifest.
                Picasso.get().load(uri).fit().centerInside().into(profileIV);
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation userProfile = dataSnapshot.getValue(UserInformation.class);
                FirebaseUser user = mAuth.getCurrentUser();
                fNameET.setText(userProfile.getfname());
                lNameET.setText(userProfile.getlname());
                emailTV.setText(user.getEmail());
                schoolET.setText(userProfile.getSchool());
                majorET.setText(userProfile.getMajor());

                //retrieve images for the user in question

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void saveProfileSetting(){
        final String fname = fNameET.getText().toString().trim();
        final String lname = lNameET.getText().toString().trim();
        final String university = schoolET.getText().toString().trim();
        final String major = majorET.getText().toString().trim();

        myRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInformation user = dataSnapshot.getValue(UserInformation.class);

                //case 1: change first name
                if(!user.getfname().equals(fname)){
                    updateUserAccountSettings(fname,null,null,null);
                }
                if(!user.getlname().equals(lname)){
                    updateUserAccountSettings(null,lname,null,null);
                }
                if(!user.getSchool().equals(university)){
                    updateUserAccountSettings(null,null,university,null);
                }
                if(!user.getMajor().equals(major)){
                    updateUserAccountSettings(null,null,null,major);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Update 'user_account_settings' node for the current user
     * @param fname
     * @param lname
     * @param school
     * @param major
     */
    public void updateUserAccountSettings(String fname, String lname, String school, String major){
        String userID = mAuth.getCurrentUser().getUid();
        Log.d(TAG, "updateUserAccountSettings: updating user account settings.");
        if(fname != null){
            myRef.child("fname")
                    .setValue(fname);
        }


        if(lname != null) {
            myRef.child("lname")
                    .setValue(lname);
        }

        if(school != null) {
            myRef.child("uni")
                    .setValue(school);
        }

        if(major != null) {
            myRef.child("major")
                    .setValue(major);
        }
        Toast.makeText(getContext(), "Successed.", Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.GONE);
    }

}