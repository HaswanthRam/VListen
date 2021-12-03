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

import org.json.JSONArray;

import java.util.ArrayList;
public class GroupRecommendation extends Activity{
        LinearLayout my_linear_layout;
        int N;
        ArrayList<String> recommendedGroupIds = new ArrayList<>();
        ArrayList<String> recommendedGroupDescriptions = new ArrayList<>();
    ArrayList<String> recommendedGroupNames = new ArrayList<>();


    Intent intent;
    String userId, name, age, gender;
    // Database reference pointing to root of database
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    // Database reference pointing to User node
    DatabaseReference usersRef = rootRef.child("Users");




        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.iterative_linearlayout);
            intent=getIntent();
            userId = intent.getStringExtra("userId");
            name = intent.getStringExtra("name");
            age = intent.getStringExtra("age");
            gender = intent.getStringExtra("gender");
=======
            
            // Database reference pointing to root of database
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            // Database reference pointing to User node
            DatabaseReference usersRef = rootRef.child("Users");
            DatabaseReference groups = usersRef.child(userId).child("groups");
            DatabaseReference groupCollection = rootRef.child("Groups");
            // Database reference for recommended groups

            DatabaseReference groupId = usersRef.child(userId).child("recommendedGroups");


            //method to fetch the recommended Group IDs from Users node
            groupId.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    N = (int) snapshot.getChildrenCount();

                    //iterating over the recommended group IDs through the Users node.
                    for (DataSnapshot child : snapshot.getChildren()) {
                        recommendedGroupIds.add(child.getValue().toString());
                    }

                    //Groups IDs fetched. Fetching Group Names and Description with Group IDs
                    DatabaseReference tagName=snapshot.getRef().getRoot().child("Groups");
                    tagName.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int i, flag=0;
                            i=0;
                            for (DataSnapshot child : snapshot.getChildren()) {

                                //if recommendedGroupIds match with the group IDs in Groups node, Name and descriptions are being saved.
                                if(recommendedGroupIds.get(i).toString().equalsIgnoreCase(child.child("groupId").getValue().toString()))
                                {
                                   recommendedGroupDescriptions.add(child.child("description").getValue().toString());
                                    recommendedGroupNames.add(child.child("groupName").getValue().toString());

                                    flag++; //variable to keep the length of recommendedGroupIDs in check

                                    if(flag == recommendedGroupIds.size())
                                    {
                                        //exits the loop when group details are fetched for all recommended group Ids
                                        break;
                                    }
                                    i++;
                                }


                            }
                            //Group details fetched. Creating the dynamic XML
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
            rowTextViewTitle.setText("Recommended Groups");
            rowTextViewTitle.setGravity(1);
            rowTextViewTitle.setTextSize(28);
            my_linear_layout.addView(rowTextViewTitle);
            DatabaseReference groups = usersRef.child(userId).child("groups");
            DatabaseReference groupCollection = rootRef.child("Groups");
            // Database reference for recommended groups
            DatabaseReference groupId = usersRef.child(userId).child("recommendedGroups");
            for (int i = 0; i < N; i++) {
                // create a new textview
                final TextView rowTextView = new TextView(this);
                rowTextView.setText(recommendedGroupNames.get(i).toString()+"\n");
                rowTextView.setId(i);
                my_linear_layout.addView(rowTextView);
                //creating another text view
                final TextView rowTextView2 = new TextView(this);
                rowTextView2.setText("Group Description: "+recommendedGroupDescriptions.get(i).toString()+"\n");
                rowTextView2.setId(i);
                my_linear_layout.addView(rowTextView2);
                //creating a button
                final Button JoinButton = new Button(this);
                JoinButton.setText("Join");
                JoinButton.setId(i);
                String s = recommendedGroupIds.get(i);
                JoinButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(JoinButton.getText()=="Join")
                        {
                            groups.push().setValue(s);

                            groupCollection.child(s).child("groupMembers").push().setValue(name);

                            JoinButton.setText("Joined");

                        }
                       else
                        {
                            JoinButton.setText("Already Joined");
                        }
                    }
                });
                my_linear_layout.addView(JoinButton);
            }
            final Button nextButton = new Button(this);
            nextButton.setText("Next");
            my_linear_layout.addView(nextButton);
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoUserDashboard();
                }
            });
        }

    private void gotoUserDashboard(){
        Intent intent = new Intent(GroupRecommendation.this, userDashboard.class);

        intent.putExtra("name", name);
        intent.putExtra("userId", userId);
        intent.putExtra("age", age);
        intent.putExtra("gender", gender);

        startActivity(intent);
    }

    }
