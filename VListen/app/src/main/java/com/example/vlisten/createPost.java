package com.example.vlisten;

import android.app.Activity;
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

public class createPost extends Activity {

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    // Database reference pointing to User node
    DatabaseReference PostsRef = rootRef.child("Posts");
    ArrayList<Posts> Posts = new ArrayList<>();

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
//                    PostsRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                          //  int entry = Integer.valueOf(snapshot.child("entries").getValue().toString());
//                            newPost.setPostId(String.valueOf(entry+1));
//                            PostsRef.child(String.valueOf(entry+1)).setValue(newPost);
//                            PostsRef.child("entries").setValue(String.valueOf(entry+1));
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
                   // PostsRef.child("100").setValue(newPost);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

//                PostsRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                        for (DataSnapshot child : snapshot.getChildren()) {
//
//
//                           String text =  child.child("text").getValue().toString();
//                           String user =  child.child("user").getValue().toString();
//                           Posts p = new Posts();
//                           p.setText(text);
//                           p.setUser(user);
//                           p.setLikes(0);
//                           Posts.add(p);
//
//                        }
//
//                    }

//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//
//                });


            }
        });
    }
}
