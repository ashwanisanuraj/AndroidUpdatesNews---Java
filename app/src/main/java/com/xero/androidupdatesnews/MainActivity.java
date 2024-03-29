package com.xero.androidupdatesnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.graphics.Color.TRANSPARENT;

public class MainActivity extends AppCompatActivity {

    // Widgets
    Button loginBtn, createAccountBtn;
    private EditText emailEt, passEt;

    // Firebase Auth
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(TRANSPARENT);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Check if user is already logged in
        currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            // If user is already logged in, redirect to JournalListActivity
            Intent intent = new Intent(MainActivity.this, JournalListActivity.class);
            startActivity(intent);
            finish(); // Finish MainActivity to prevent going back to it when pressing back button
        }

        // Initialize UI elements
        createAccountBtn = findViewById(R.id.creat_account);
        loginBtn = findViewById(R.id.email_signin);
        emailEt = findViewById(R.id.email);
        passEt = findViewById(R.id.password);

        createAccountBtn.setOnClickListener(v -> {
            // Onclick()
            Intent i = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(i);
        });

        // Login button click listener
        loginBtn.setOnClickListener(v -> {
            loginEmailPassUser(
                    emailEt.getText().toString().trim(),
                    passEt.getText().toString().trim()
            );
        });
    }


    private void loginEmailPassUser(
            String email, String pwd
    ) {
        // Checking for empty texts
        if (!TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(pwd)
        ) {
            firebaseAuth.signInWithEmailAndPassword(
                    email,
                    pwd
            ).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    Intent i = new Intent(MainActivity.this, JournalListActivity.class);
                    startActivity(i);
                }

            });

        }


    }
}

