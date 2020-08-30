package com.example.bookitup;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class RegistrationActivity extends AppCompatActivity {

    private EditText emailTV, passwordTV;
    private Button regBtn;
    private TextView login;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        initializeUI();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                 startActivity(intent);
             }
        });
    }

    private void registerNewUser() {
        progressBar.setVisibility(View.VISIBLE);

        String email, password, emailRegex;
        email = emailTV.getText().toString();
        password = passwordTV.getText().toString();
        emailRegex = "^[-a-z0-9~!$%^&*_=+}{\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+" +
                ")*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+" +
                ")*\\.(edu|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$";


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter .edu email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        //register, email has not verified
        if(email.matches(emailRegex)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                emailVerification(mAuth.getCurrentUser());
                                Intent intent = new Intent(RegistrationActivity.this, AddProfileActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }
        else{
            Toast.makeText(getApplicationContext(), "You must have an .edu email to register.", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            //progressBar.setVisibility(View.GONE);
        }
    }

    private void emailVerification(FirebaseUser user){
        user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),
                            "Please check your email for verification before login.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Failed to send verification email." + task.getException(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initializeUI() {
        emailTV = findViewById(R.id.email);
        passwordTV = findViewById(R.id.password);
        regBtn = findViewById(R.id.register);
        login = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar);
    }
}
