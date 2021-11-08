package com.example.vlisten;

import android.app.Activity;
import android.os.Bundle;
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
    RelativeLayout my_relative_layout;
    int N;
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    // Database reference pointing to User node
    DatabaseReference PostsRef = rootRef.child("Posts");
    ArrayList<String> Postslist = new ArrayList<String>();
    ArrayList<String> text = new ArrayList<String>();
    ArrayList<String> user = new ArrayList<String>();
    ArrayList<POJO.Posts> Posts = new ArrayList<>();
    POJO.Posts p = new Posts();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iterative_linearlayout);
        PostsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                N = (int) snapshot.getChildrenCount();

                //iterating over the recommended group IDs through the Users node.
                for (DataSnapshot child : snapshot.getChildren()) {
                    //Postslist.add(child.getValue().toString());

                    text.add(child.child("text").getValue().toString());
                    user.add(child.child("user").getValue().toString());



                }

                createXML();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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
        for (int i = 0; i < N; i++) {
            // create a new textview
            final TextView rowTextView = new TextView(this);
            rowTextView.setText("User "+user.get(i).toString()+"\n");
            rowTextView.setId(i);
            my_linear_layout.addView(rowTextView);
            //creating another text view
            final TextView rowTextView2 = new TextView(this);
            rowTextView2.setText(text.get(i).toString()+"\n");
            rowTextView2.setId(i);
            my_linear_layout.addView(rowTextView2);
            //creating a like button
            final Button likeButton = new Button(this);
            likeButton.setText("Like");
            likeButton.setId(i);
            my_linear_layout.addView(likeButton);
            //creating a share button
            final Button shareButton = new Button(this);
            shareButton.setText("Share");
            shareButton.setId(i);
            my_linear_layout.addView(shareButton);

    }

}}
