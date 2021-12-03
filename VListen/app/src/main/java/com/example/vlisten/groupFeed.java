package com.example.vlisten;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import POJO.Posts;

public class groupFeed extends Activity {
    LinearLayout my_linear_layout;
    int N;
    String activeUserId, activeGroup;
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    // Database reference pointing to User node
    DatabaseReference PostsRef = rootRef.child("Posts");
    ArrayList<Posts> Posts = new ArrayList<>();
    String name, age, gender, userId,activeGroup;
    Intent intent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        activeGroup = intent.getStringExtra("g_id");

        userId = intent.getStringExtra("userId");
        name = intent.getStringExtra("name");
        age = intent.getStringExtra("age");
        gender = intent.getStringExtra("gender");

        PostsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                N = (int) snapshot.getChildrenCount();

                //iterating over the recommended group IDs through the Users node.
                for (DataSnapshot child : snapshot.getChildren()) {

                    if (child.child("groupId").getValue().toString().equals(activeGroup)) {
                        Posts p = new Posts();
                        p.setText(child.child("text").getValue().toString());
                        p.setUserName(child.child("userName").getValue().toString());
                        p.setPostId(child.child("postId").getValue().toString());

                        p.setLikes(Integer.valueOf(child.child("likes").getValue().toString()));
                        Posts.add(p);

                    }
                }


                createXML();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }
        else
        {
            setContentView(R.layout.dummy_sign_in);
        }

    }
    //method to create the iterative XML
    public void createXML()
    {
        my_linear_layout = findViewById(R.id.my_linear_layout);
        //create a new textview title
        final TextView rowTextViewTitle = new TextView(this);
        rowTextViewTitle.setText("Group Feed");
        rowTextViewTitle.setGravity(1);
        rowTextViewTitle.setTextSize(28);
        my_linear_layout.addView(rowTextViewTitle);
        for (int i = 0; i <Posts.size() ; i++) {
            // create a new textview
            final TextView rowTextView = new TextView(this);
            rowTextView.setText(Posts.get(i).getText());
            rowTextView.setId(i);
            my_linear_layout.addView(rowTextView);

            //creating another text view
            final TextView rowTextView2 = new TextView(this);
            rowTextView2.setText(Posts.get(i).getUserName());
            rowTextView2.setId(i);
            my_linear_layout.addView(rowTextView2);
            //creating a like button
            final Button likeButton = new Button(this);
            likeButton.setText(Posts.get(i).getLikes() + " Likes");
            likeButton.setId(i);
            String id = Posts.get(i).getPostId();
            int likes = Posts.get(i).getLikes()+1;
            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(likeButton.isEnabled())
                    {
                        likeButton.setEnabled(false);

                        PostsRef.child(id).child("likes").setValue(likes);
                        likeButton.setText(String.valueOf(likes) + " Likes");


                    }

                }
            });

            my_linear_layout.addView(likeButton);
            //creating a share button
            final Button shareButton = new Button(this);
            shareButton.setText("Share");
            shareButton.setId(i);
            my_linear_layout.addView(shareButton);

        }

        final Button createPost = new Button(this);
        createPost.setText("Create Post");

        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(groupFeed.this, createPost.class);

                intent.putExtra("name", name);
                intent.putExtra("userId", userId);
                intent.putExtra("age", age);
                intent.putExtra("gender", gender);
                intent.putExtra("g_id", activeGroup);

                startActivity(intent);
            }
        });
        my_linear_layout.addView(createPost);
        final Button groupDetails = new Button(this);
        groupDetails.setText("Group Details");

        groupDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(groupFeed.this, groupDetails.class);
                intent1.putExtra("name", name);
                intent1.putExtra("userId", userId);
                intent1.putExtra("age", age);
                intent1.putExtra("gender", gender);
                intent1.putExtra("g_id", activeGroup);
                startActivity(intent1);
            }
        });
        my_linear_layout.addView(groupDetails);

    }}