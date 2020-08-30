package com.example.bookitup.ui.profile;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import com.example.bookitup.ResetPasswordActivity;
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

public class ProfileFragment extends Fragment {
    private ProfileViewModel profileViewModel;
    private ImageView profileIV;
    private TextView fNameTV, lNameTV, emailTV, schoolTV, majorTV;
    private Button editProfileBtn, forgotPasswordBtn;
    private Context profileContext;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseStorage mStorage;
    private  StorageReference mReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profileIV = view.findViewById(R.id.update_imageView);
        fNameTV = view.findViewById(R.id.fnameTV);
        lNameTV = view.findViewById(R.id.lnameTV);
        emailTV = view.findViewById(R.id.textViewEmail);
        schoolTV = view.findViewById(R.id.schoolTV);
        majorTV = view.findViewById(R.id.majorTV);


        profileContext = getContext();

        setupFirebaseAuth();


        Button editProfile = view.findViewById(R.id.btn_editProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.nav_host_fragment,editProfileFragment,editProfileFragment.getTag())
                        .addToBackStack(null)
                        .commit();
//                transaction.replace(R.id.nav_profile,editProfileFragment);
            }
        });

        TextView changePassword = view.findViewById(R.id.btn_changePassword);
        changePassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), ResetPasswordActivity.class);
                startActivity(intent);
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

                //retrieve user information from the database
//                setProfileWidgets(mFirebaseMethods.getUserSettings(dataSnapshot));
                UserInformation userProfile = dataSnapshot.getValue(UserInformation.class);
                FirebaseUser user = mAuth.getCurrentUser();
                fNameTV.setText(userProfile.getfname());
                lNameTV.setText(userProfile.getlname());
                emailTV.setText(user.getEmail());
                schoolTV.setText(userProfile.getSchool());
                majorTV.setText(userProfile.getMajor());

                //retrieve images for the user in question

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}