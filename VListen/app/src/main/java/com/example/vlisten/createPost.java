package com.example.vlisten;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import POJO.Posts;
import POJO.cheerPosts;

public class createPost extends Activity {

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    // Database reference pointing to User node
    DatabaseReference PostsRef = rootRef.child("Posts");
    DatabaseReference CheersRef = rootRef.child("Cheers");
    ArrayList<Posts> Posts = new ArrayList<>();
    ArrayList<cheerPosts> cheerPosts = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dummy_create_post);
        Random r = new Random();

        String generatedString = r.ints(97, 122 + 1)
                .limit(15)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        TextView postText = findViewById(R.id.postText);
        Button send = findViewById(R.id.sendButton);
        //Button createCheerPost = findViewById(R.id.postCheerButton);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Posts newPost = new Posts();
                    newPost.setText(postText.getText().toString());
                    newPost.setUser("1");
                    newPost.setLikes(0);
                    newPost.setUserName("User One");
                    newPost.setPostId(generatedString);
                    PostsRef.child(generatedString).setValue(newPost);
                    gotoGroupFeed();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

            }

        });

    }
    private void gotoGroupFeed(){
        Intent intent = new Intent(createPost.this, groupFeed.class);
        startActivity(intent);
    }
}
