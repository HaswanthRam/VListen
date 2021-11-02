package com.example.vlisten;
import android.app.Activity;
import android.os.Bundle;
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
public class GroupRecommendation extends Activity {
        LinearLayout my_linear_layout;
        int N;
        ArrayList<String> recommendedGroupIds = new ArrayList<>();
        ArrayList<String> recommendedGroupDescriptions = new ArrayList<>();
    ArrayList<String> recommendedGroupNames = new ArrayList<>();

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.group_recommendation);

            // Database reference pointing to root of database
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            // Database reference pointing to User node
            DatabaseReference usersRef = rootRef.child("Users");
            // Database reference for recommended groups
            DatabaseReference groupId = usersRef.child("2").child("recommendedGroups");


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
                    tagName.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int i, flag=0;
                            i=0;
                            for (DataSnapshot child : snapshot.getChildren()) {

                                //if recommendedGroupIds match with the group IDs in Groups node, Name and descriptions are being saved.
                                if(recommendedGroupIds.get(i).toString().equalsIgnoreCase(child.child("groupId").getValue().toString()))
                                {
                                   recommendedGroupDescriptions.add(child.child("tag").getValue().toString());
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
        //method to create the iterative XML
        public void createXML()
        {
            my_linear_layout = findViewById(R.id.my_linear_layout);

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
                my_linear_layout.addView(JoinButton);
            }

        }

    }
