package com.example.vlisten;
import android.app.Activity;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
public class GroupRecommendation extends Activity {
        public static int count;
        LinearLayout my_linear_layout;
        LinearLayout my_linear_layout1;
        LinearLayout my_linear_layout2;
        int N;
        public static String a;
        ArrayList<String> recommendedGroupIds = new ArrayList<>();
        ArrayList<String> recommendedGroupDescriptions = new ArrayList<>();
        String[] str = new String[6];
        final Button[] JoinButton = new Button[N];
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.group_recommendation);

            // Database reference pointing to root of database
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            // Database reference pointing to demo node
            DatabaseReference usersRef = rootRef.child("Users");
            DatabaseReference groupId = usersRef.child("2").child("recommendedGroups");

            ArrayList<Integer> groupIdList = new ArrayList<>();

            //method to create the XML dynamically based on the count of recommended groups
            groupId.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // this method is call to get the realtime
                    // updates in the data.
                    // this method is called when the data is
                    // changed in our Firebase console.
                    // below line is for getting the data from
                    // snapshot of our database.
                    N = (int) snapshot.getChildrenCount();

                    for (DataSnapshot child : snapshot.getChildren()) {
                        recommendedGroupIds.add(child.getValue().toString());
                    }
                    DatabaseReference tagName=snapshot.getRef().getRoot().child("Groups");
                    tagName.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int i, flag=0;
                            i=0;
                            for (DataSnapshot child : snapshot.getChildren()) {


                                if(recommendedGroupIds.get(i).toString().equalsIgnoreCase(child.child("groupId").getValue().toString()))
                                {
                                   recommendedGroupDescriptions.add(child.child("tag").getValue().toString());

                                    Log.d("Group Description 1",recommendedGroupDescriptions.toString());
                                    flag++;
                                    if(flag == recommendedGroupIds.size())
                                    {
                                        break;
                                    }
                                    i++;
                                }


                            }

                            createXML(recommendedGroupDescriptions);

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
        public void createXML(ArrayList<String> recommendedGroupDescriptions1)
        {
            my_linear_layout = findViewById(R.id.my_linear_layout);
            my_linear_layout1 = findViewById(R.id.my_linear_layout1);
            my_linear_layout2 = findViewById(R.id.my_linear_layout2);
            final TextView[] myTextViews = new TextView[N]; // create an empty array;
            final LinearLayout[] myRelativeLayout = new LinearLayout[N];
            for (int i = 0; i < N; i++) {
                // create a new textview
                final TextView rowTextView = new TextView(this);
                final Button JoinButton = new Button(this);
                rowTextView.setText("Group ID:" +recommendedGroupIds.get(i).toString()+"\n");
                //rowTextView.TEXT_ALIGNMENT_CENTER=5;
                rowTextView.setId(i);
                my_linear_layout.addView(rowTextView);
                final TextView rowTextView2 = new TextView(this);
                rowTextView2.setText("Group Description: "+recommendedGroupDescriptions.get(i).toString()+"\n");
                rowTextView2.setId(i);
                my_linear_layout.addView(rowTextView2);

                JoinButton.setText("Join");
                JoinButton.setId(i);
                my_linear_layout.addView(JoinButton);
            }

        }

    }
