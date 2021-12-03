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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import POJO.Posts;

public class groupDetails extends Activity {

    LinearLayout my_linear_layout;
    int N;
    String groupName;
    String g_id;
    String groupDescription;
    String Groupmem;
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userRef = rootRef.child("Users");
    // Database reference pointing to User node

    Intent intent;

    ArrayList<String> groupMembers = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iterative_linearlayout);
        Intent intent = getIntent();
        g_id = intent.getStringExtra("g_id");
        DatabaseReference groupRef = rootRef.child("Groups").child(g_id);
        groupRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            groupName = snapshot.child("groupName").getValue().toString();
            groupDescription = snapshot.child("description").getValue().toString();


            DatabaseReference members = groupRef.child("groupMembers");
            members.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //iterating over the group members through the Users node.
                    for (DataSnapshot child : snapshot.getChildren()) {
                        groupMembers.add(child.getValue().toString());
                        Groupmem = child.getValue().toString();

                    }

                    createXML();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

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
            rowTextViewTitle.setText("Group Details");
            rowTextViewTitle.setGravity(1);
            rowTextViewTitle.setTextSize(28);
            my_linear_layout.addView(rowTextViewTitle);

            // create a new textview
            final TextView rowTextView = new TextView(this);
            rowTextView.setText("Group Name: "+groupName.toString()+"\n");
            my_linear_layout.addView(rowTextView);

            //creating another text view
            final TextView rowTextView2 = new TextView(this);
            rowTextView2.setText("Group Description: "+groupDescription.toString()+"\n");
            my_linear_layout.addView(rowTextView2);

            //create a new textview title
            final TextView rowTextView4 = new TextView(this);
            rowTextView4.setText("Group Members");
            my_linear_layout.addView(rowTextView4);
            for (int i = 0; i < groupMembers.size(); i++) {


                //creating another text view
                final TextView rowTextView3 = new TextView(this);

                rowTextView3.setText(groupMembers.get(i).toString());
                my_linear_layout.addView(rowTextView3);

            }
            final Button leaveButton = new Button(this);
            leaveButton.setText("Leave");
            leaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference groupRef = rootRef.child("Groups").child(g_id);
                    DatabaseReference  members= groupRef.child("groupMembers");
                    members.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot members: dataSnapshot.getChildren()) {
                                if(members.getValue().toString().equals("1")){
                                members.getRef().removeValue();}
                            }
                            Intent intent = new Intent(groupDetails.this, groupDetails.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            });


            my_linear_layout.addView(leaveButton);
        }

}

