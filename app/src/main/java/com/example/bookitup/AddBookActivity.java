package com.example.bookitup;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.Date;


public class AddBookActivity extends AppCompatActivity {
    private EditText entry_name;
    private Spinner bookCondition;
    private DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;



    EditText Nbook,Nisbn,Nauthor,Nprice,Nedition,Ndescription;
    Button Nsave;
    DatabaseReference newrecord;
    FirebaseDatabase database;
    String condition;
    String sellerName;


    BookActivity detail = new BookActivity();
    private FirebaseAuth firebaseAuth;
//    private String getDateTime() {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        Date date = new Date();
//        return dateFormat.format(date);
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_add_book);
        Nbook = findViewById(R.id.bookname);
        Nisbn=findViewById(R.id.isbn);
        Nauthor=findViewById(R.id.author);
        Nprice=findViewById(R.id.price);
        Nedition=findViewById(R.id.bookEdition);
        //drop down menu
        bookCondition = findViewById(R.id.book_condition);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.book_condition, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bookCondition.setAdapter(adapter);
        bookCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                condition = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Ndescription=findViewById(R.id.description);
        Nsave=findViewById(R.id.save);
        detail=new BookActivity();
        Timestamp ts=new Timestamp(System.currentTimeMillis());
        final Date date=ts;

        newrecord= database.getInstance().getReference().child("Booklist");

        newrecord.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //retrieve user info

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("Users").child(mAuth.getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation userProfile = dataSnapshot.getValue(UserInformation.class);
                FirebaseUser user = mAuth.getCurrentUser();
                sellerName =userProfile.getfname();
                sellerName = sellerName + " "+ userProfile.getlname();

                //retrieve images for the user in question
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        Nsave.setOnClickListener(new View.OnClickListener()
        {

            @Override

            public void onClick(View view)
            {
                try{
                detail.setXbook(Nbook.getText().toString().trim());
                detail.setEdition(Nedition.getText().toString().trim());
                detail.setIsbn(Nisbn.getText().toString().trim());
                detail.setXauthor(Nauthor.getText().toString().trim());
                detail.setXprice(Nprice.getText().toString());
                detail.setXdescription(Ndescription.getText().toString().trim());
                detail.setXcondition(condition);
                detail.setXuid(firebaseAuth.getInstance().getCurrentUser().getUid().trim());
                detail.setDate(date.toString());
                detail.setSellerName(sellerName);
                detail.setEmail(firebaseAuth.getInstance().getCurrentUser().getEmail().trim());
                newrecord.push().setValue(detail);
                }
                catch(Exception e){
                    System.out.println(e);
                }
                if (TextUtils.isEmpty(detail.getXbook())) {
                    Nbook.setError("Book name cannot be empty");
                }
                Toast.makeText(AddBookActivity.this,"Book added successfully",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddBookActivity.this, MainActivity.class);
                startActivity(intent);

            }

        });

    }
    private void initializeUI() {
        entry_name = findViewById(R.id.bookname);

    }
}
