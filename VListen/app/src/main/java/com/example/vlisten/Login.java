package com.example.vlisten;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText inputEmail, inputPassword;
    Button btnLogin;
    String patternEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String name, age, gender, userId;
    Intent intent;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    // Database reference pointing to root of database
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    // Database reference pointing to demo node
    DatabaseReference usersRef = rootRef.child("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoLogin();
            }
        });
    }

    private void DoLogin() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        intent=getIntent();
        name = intent.getStringExtra("name");
        age = intent.getStringExtra("age");
        gender = intent.getStringExtra("gender");
        if(!email.matches(patternEmail)) {
            inputEmail.setError("Invalid Email");
            inputEmail.requestFocus();
        }
        else if (password.isEmpty() || password.length() < 6) {
            inputPassword.setError("Incorrect Password. Minimum length should be 6 characters");
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(Login.this, "Login Successfull!", Toast.LENGTH_SHORT).show();

                        userId=task.getResult().getUser().getUid();
                        goToUserDashboard();
                        //changes for chat feature
                    } else {
                        Toast.makeText(Login.this, "Something Went Wrong! " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void goToUserDashboard() {
        Intent intent = new Intent(Login.this, userDashboard.class);
        intent.putExtra("name", name);
        intent.putExtra("userId", userId);
        intent.putExtra("age", age);
        intent.putExtra("gender", gender);
        startActivity(intent);
    }
}
