package com.example.vlisten;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import POJO.cheerPosts;

public class cheerBoard extends Activity {
    LinearLayout my_linear_layout;
    int N;
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    // Database reference pointing to User node
    DatabaseReference CheerRef = rootRef.child("Cheers");
    ArrayList<cheerPosts> cheers = new ArrayList<>();
    String activeUserId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String activeGroup = intent.getStringExtra("g_id");
        activeUserId = intent.getStringExtra("user_id");
        if(activeUserId != null)
        {
            setContentView(R.layout.iterative_linearlayout);

        CheerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                N = (int) snapshot.getChildrenCount();

                //iterating over the recommended group IDs through the Users node.
                for (DataSnapshot child : snapshot.getChildren()) {

                    cheerPosts cheer = new cheerPosts();
                    cheer.setText(child.child("text").getValue().toString());
                    cheer.setUserName(child.child("userName").getValue().toString());
                    cheer.setCheerPostId(child.child("cheerPostId").getValue().toString());
                    cheer.setCheers(Integer.valueOf(child.child("cheers").getValue().toString()));
                    cheers.add(cheer);

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
        rowTextViewTitle.setText("Cheer Board");
        rowTextViewTitle.setGravity(1);
        rowTextViewTitle.setTextSize(28);
        my_linear_layout.addView(rowTextViewTitle);
        for (int i = 0; i < N; i++) {
            // create a new textview
            final TextView rowTextView = new TextView(this);
            rowTextView.setText(cheers.get(i).getText());
            rowTextView.setId(i);
            my_linear_layout.addView(rowTextView);
            //creating another text view
            final TextView rowTextView2 = new TextView(this);
            rowTextView2.setText(cheers.get(i).getUserName());
            rowTextView2.setId(i);
            my_linear_layout.addView(rowTextView2);
            //creating a like button
            final Button cheerButton = new Button(this);
            cheerButton.setText(cheers.get(i).getCheers() + " Cheers");
            cheerButton.setId(i);
            String id = cheers.get(i).getCheerPostId();
            int cheersCount = cheers.get(i).getCheers()+1;
            cheerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(cheerButton.isEnabled())
                    {
                        cheerButton.setEnabled(false);

                        CheerRef.child(id).child("cheers").setValue(cheersCount);
                        cheerButton.setText(String.valueOf(cheersCount) + " Cheers");


                    }

                }
            });

            my_linear_layout.addView(cheerButton);
            //creating a share button
            final Button shareButton = new Button(this);
            shareButton.setText("Share");
            shareButton.setId(i);
            my_linear_layout.addView(shareButton);

        }

    }
}
