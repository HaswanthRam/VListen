package com.example.vlisten;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    EditText inputName, inputEmail, inputPassword, inputConfirmPassword, inputAge, inputGender;
    TextView alreadyUser;
    Button btnRegister;
    String patternEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String name, userID, age, gender;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        inputName = findViewById(R.id.name);
        inputAge = findViewById(R.id.age);
        inputGender = findViewById(R.id.gender);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        inputConfirmPassword = findViewById(R.id.confirmpass);
        btnRegister = findViewById(R.id.btnRegister);

        alreadyUser = findViewById(R.id.alreadyuser_textview);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoAuthentication();
            }
        });

        alreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });
    }

    private void DoAuthentication() {
        name = inputName.getText().toString().trim();
        age = inputAge.getText().toString().trim();
        gender = inputGender.getText().toString().trim();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = inputConfirmPassword.getText().toString();

        if(!email.matches(patternEmail)) {
            inputEmail.setError("Invalid Email");
            inputEmail.requestFocus();
        }
        else if (password.isEmpty() || password.length() < 6) {
            inputPassword.setError("Incorrect Password. Minimum length should be 6 characters");
        } else if (!password.equals(confirmPassword)) {
            inputConfirmPassword.setError("Passwords Don't Match");
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(Register.this, "Kindly Complete the next page to Complete Registration", Toast.LENGTH_SHORT).show();
                        userID = mUser.getUid();
                        goToMainActivity();
                    } else {
                        Toast.makeText(Register.this, "Something Went Wrong! " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void goToLogin() {
        Intent intent = new Intent(Register.this, Login.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("name", name);
        intent.putExtra("userId", userID);
        intent.putExtra("age", age);
        intent.putExtra("gender", gender);
        startActivity(intent);
    }

    private void goToMainActivity() {
        Intent intent1 = new Intent(Register.this, MainActivity.class);
       // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent1.putExtra("name", name);
        intent1.putExtra("userId", userID);
        intent1.putExtra("age", age);
        intent1.putExtra("gender", gender);
        startActivity(intent1);
    }
}
