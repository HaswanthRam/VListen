package com.example.vlisten;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class userDashboard extends Activity {
    ArrayList<String> recommendedGroupIds = new ArrayList<>();
    ArrayList<String> joinedGroups = new ArrayList<>();
    LinearLayout my_linear_layout;
    String groupName,activeGroupFinal;
    Intent intent;
    String userId, name, age, gender;
    // Database reference pointing to root of database
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    // Database reference pointing to User node
    DatabaseReference usersRef = rootRef.child("Users");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iterative_linearlayout);
        intent=getIntent();
        userId = intent.getStringExtra("userId");
        name = intent.getStringExtra("name");
        age = intent.getStringExtra("age");
        gender = intent.getStringExtra("gender");
        // Database reference pointing to root of database
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("Users").child(userId);
        DatabaseReference groups = usersRef.child(userId).child("groups");
        DatabaseReference groupCollection = rootRef.child("Groups");
        // Database reference pointing to User node


        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                age = snapshot.child("age").getValue().toString();
                gender = snapshot.child("gender").getValue().toString();
                name = snapshot.child("name").getValue().toString();

                for(DataSnapshot child:snapshot.child("recommendedGroups").getChildren())
                {
                    recommendedGroupIds.add(child.getValue().toString());
                }
                for(DataSnapshot child:snapshot.child("groups").getChildren())
                {
                    joinedGroups.add(child.getValue().toString());
                }
                createXML();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void createXML()
    {
        my_linear_layout = findViewById(R.id.my_linear_layout);
        //create a new textview title
        final TextView rowTextViewTitle = new TextView(this);
        rowTextViewTitle.setText("User Dashboard");
        rowTextViewTitle.setGravity(1);
        rowTextViewTitle.setTextSize(28);
        my_linear_layout.addView(rowTextViewTitle);

        final TextView rowTextView = new TextView(this);
        rowTextView.setText(name+"\n");
        my_linear_layout.addView(rowTextView);

        final TextView rowTextView2 = new TextView(this);
        rowTextView2.setText(gender+"\n");
        my_linear_layout.addView(rowTextView2);

        final TextView rowTextView3 = new TextView(this);
        rowTextView3.setText(age+"\n");
        my_linear_layout.addView(rowTextView3);

        final TextView rowTextView6 = new TextView(this);
        rowTextView6.setText("Recommended Groups");
        my_linear_layout.addView(rowTextView6);

        for(int j=0; j<recommendedGroupIds.size();j++)
        {
            final TextView rowTextView7 = new TextView(this);
            String groupId = recommendedGroupIds.get(j);
            DatabaseReference groups = usersRef.child(userId).child("groups");
            DatabaseReference groupCollection = rootRef.child("Groups");
            groupCollection.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    groupName = snapshot.child(groupId).child("groupName").getValue().toString();
                    rowTextView7.setText(groupName);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            Button joinGroup= new Button(this);
            if(joinedGroups.contains(recommendedGroupIds.get(j)))
            {
                joinGroup.setText("Open");
                String activeGroup = recommendedGroupIds.get(j);
                joinGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(userDashboard.this, groupFeed.class);
                        activeGroupFinal = activeGroup;
                        intent.putExtra("name", name);
                        intent.putExtra("userId", userId);
                        intent.putExtra("age", age);
                        intent.putExtra("gender", gender);
                        intent.putExtra("g_id", activeGroupFinal);
                        startActivity(intent);
                    }
                });


            }
            else
            {
                joinGroup.setText("Join");
                String s = recommendedGroupIds.get(j);
                joinGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        groups.push().setValue(s);
                        groupCollection.child(s).child("groupMembers").push().setValue(name);
                        joinGroup.setText("Open");
                        joinGroup.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(userDashboard.this, groupFeed.class);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
            my_linear_layout.addView(rowTextView7);
            my_linear_layout.addView(joinGroup);
        }
        Button createCheer = new Button(this);
        createCheer.setText("Create Cheer");
        my_linear_layout.addView(createCheer);
        createCheer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(userDashboard.this, createCheer.class);
                intent.putExtra("name", name);
                intent.putExtra("userId", userId);
                intent.putExtra("age", age);
                intent.putExtra("gender", gender);
                startActivity(intent);
            }
        });
    }
}
