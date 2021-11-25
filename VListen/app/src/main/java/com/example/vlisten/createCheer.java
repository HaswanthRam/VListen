package com.example.vlisten;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

import POJO.Posts;
import POJO.cheerPosts;

public class createCheer extends Activity {

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    // Database reference pointing to User node
    DatabaseReference PostsRef = rootRef.child("Posts");
    DatabaseReference CheersRef = rootRef.child("Cheers");
    ArrayList<POJO.Posts> Posts = new ArrayList<>();
    ArrayList<POJO.cheerPosts> cheerPosts = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dummy_create_cheer);
        Random r = new Random();

        String generatedString = r.ints(97, 122 + 1)
                .limit(15)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        TextView postText = findViewById(R.id.postText);
        Button send = findViewById(R.id.sendButton);
        Button createCheerPost = findViewById(R.id.postCheerButton);
        Button goToCheerBoard = findViewById(R.id.goToCheerBoard);
        goToCheerBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoCheerBoard();
            }
        });

        createCheerPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cheerPosts cheer = new cheerPosts();
                cheer.setText(postText.getText().toString());
                cheer.setCheers(0);
                cheer.setCheerPostId(generatedString);
                cheer.setUserName("User 1");
                cheer.setUserId("1");
                CheersRef.child(generatedString).setValue(cheer);
                gotoCheerBoard();
            }
        });


    }
    private void gotoCheerBoard(){
        Intent intent = new Intent(createCheer.this, cheerBoard.class);
        startActivity(intent);
    }
}
