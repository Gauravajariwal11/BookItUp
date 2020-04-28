package com.example.bookitup;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Member;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class AddBookActivity extends AppCompatActivity {
    private EditText entry_name;
    EditText Nbook,Nisbn,Ndate,Nauthor,Nprice,Ncondition,Ndescription;
    Button Nsave;
    DatabaseReference newrecord;
    FirebaseDatabase database;
    int maxid=0;
    BookActivity detail = new BookActivity();
    private FirebaseAuth firebaseAuth;
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_add_book);
        initializeUI();
        Nbook = findViewById(R.id.bookname);
        Nisbn=findViewById(R.id.isbn);
        Nauthor=findViewById(R.id.author);
        Nprice=findViewById(R.id.price);
        Ncondition=findViewById(R.id.condition);
        Ndescription=findViewById(R.id.description);
        Nsave=findViewById(R.id.save);
        //FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        newrecord= database.getInstance().getReference().child("Booklist");

        newrecord.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /*if (dataSnapshot.exists())
                {
                    maxid=(int) dataSnapshot.getChildrenCount();
                }
                else {}*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Nsave.setOnClickListener(new View.OnClickListener()
        {

            @Override

            public void onClick(View view)
            {
                detail.setXbook(Nbook.getText().toString().trim());
                detail.setXisbn(Nisbn.getText().toString().trim());
                detail.setXdate(getDateTime().trim());
                detail.setXauthor(Nauthor.getText().toString().trim());
                detail.setXprice(Float.parseFloat(Nprice.getText().toString()));
                detail.setXdescription(Ndescription.getText().toString().trim());
                detail.setXcondition(Ncondition.getText().toString().trim());
                detail.setXuid(firebaseAuth.getInstance().getCurrentUser().getUid().trim());
                newrecord.push().setValue(detail);
                Toast.makeText(AddBookActivity.this,"Book added sucessfully",Toast.LENGTH_LONG).show();
            }

        });

    }
    private void initializeUI() {
        entry_name = findViewById(R.id.bookname);

    }
}
