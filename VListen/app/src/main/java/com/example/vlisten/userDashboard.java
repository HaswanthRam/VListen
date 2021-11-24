package com.example.vlisten;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
    String age, gender, userName, userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iterative_linearlayout);

        // Database reference pointing to root of database
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        // Database reference pointing to User node
        DatabaseReference usersRef = rootRef.child("Users").child("1");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                age = snapshot.child("age").getValue().toString();
                Log.d("age= ", age);
                gender = snapshot.child("gender").getValue().toString();
                Log.d("gender= ", gender);
                userName = snapshot.child("user_Name").getValue().toString();
                Log.d("userName= ", userName);

                for(DataSnapshot child:snapshot.child("recommendedGroups").getChildren())
                {
                    recommendedGroupIds.add(child.getValue().toString());
                }
                Log.d("groupIds", recommendedGroupIds.toString());
                for(DataSnapshot child:snapshot.child("groups").getChildren())
                {
                    joinedGroups.add(child.getValue().toString());
                }
                Log.d("joined Groups", joinedGroups.toString());
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
        rowTextView.setText(userName+"\n");
        my_linear_layout.addView(rowTextView);

        final TextView rowTextView2 = new TextView(this);
        rowTextView2.setText(gender+"\n");
        my_linear_layout.addView(rowTextView2);

        final TextView rowTextView3 = new TextView(this);
        rowTextView3.setText(age+"\n");
        my_linear_layout.addView(rowTextView3);

        final TextView rowTextView4 = new TextView(this);
        rowTextView4.setText("Joined Groups");
        my_linear_layout.addView(rowTextView4);

        for(int i=0; i<joinedGroups.size();i++)
        {
            final TextView rowTextView5 = new TextView(this);
            rowTextView5.setText(joinedGroups.get(i));
            my_linear_layout.addView(rowTextView5);
        }

        final TextView rowTextView6 = new TextView(this);
        rowTextView6.setText("Recommended Groups");
        my_linear_layout.addView(rowTextView6);

        for(int j=0; j<recommendedGroupIds.size();j++)
        {
            final TextView rowTextView7 = new TextView(this);
            rowTextView7.setText(recommendedGroupIds.get(j));
            my_linear_layout.addView(rowTextView7);
        }
    }
}
