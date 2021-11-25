package com.example.vlisten;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import android.widget.Switch;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public ArrayList<Switch> s = new ArrayList<>();
    public ArrayList<String> concerns = new ArrayList<>();
    public ArrayList<Integer> recommendedGroups = new ArrayList<>();
    Button one;
    int count=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //take values from checkboxes

        s.add(this.findViewById(R.id.switch1));
        s.add(this.findViewById(R.id.switch2));
        s.add(this.findViewById(R.id.switch3));
        s.add(this.findViewById(R.id.switch4));
        s.add(this.findViewById(R.id.switch5));
        s.add(this.findViewById(R.id.switch6));
        s.add(this.findViewById(R.id.switch7));
        s.add(this.findViewById(R.id.switch8));
        s.add(this.findViewById(R.id.switch9));
        s.add(this.findViewById(R.id.switch10));
        s.add(this.findViewById(R.id.switch11));
        s.add(this.findViewById(R.id.switch12));
        s.add(this.findViewById(R.id.switch13));
        s.add(this.findViewById(R.id.switch14));

        one=findViewById(R.id.button1);
        one.setOnClickListener(v -> {
            //physical violence
            if(s.get(0).isChecked())
            {
                concerns.add("Physical Violence");
                recommendedGroups.add(1);
            }
            //LGBTQ+
            if(s.get(1).isChecked())
            {
                concerns.add("LGBTQ+");
                recommendedGroups.add(2);
            }
            //Specially Abled
            if(s.get(12).isChecked())
            {
                concerns.add("Specially Abled");
                recommendedGroups.add(3);
            }
            //Drug Abuse
            if(s.get(13).isChecked())
            {
                concerns.add("Drug Abuse");
                recommendedGroups.add(4);
            }

            //Anxiety
            for(int j=2 ; j<=6 ; j++)
            {
                if(s.get(j).isChecked())
                {
                    count++;
                }
            }
            if(count>=3)
            {
                concerns.add("anxiety");
                recommendedGroups.add(5);
            }
            else if(count > 0 && count < 3)
            {
                concerns.add("partial anxiety");
                recommendedGroups.add(7);
            }
            count = 0 ;
            //Depression
            for(int j=7 ; j<=11 ; j++)
            {
                if(s.get(j).isChecked())
                {
                    count++;
                }
            }
            if(count>=3)
            {
                concerns.add("depression");
                recommendedGroups.add(6);
            }
            else if(count > 0 && count < 3)
            {
                concerns.add("partial depression");
                recommendedGroups.add(8);
            }


            // Database reference pointing to root of database
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            // Database reference pointing to demo node
            DatabaseReference usersRef = rootRef.child("Users");
            usersRef.child("1").child("concerns").setValue(concerns);

            // Database reference pointing to demo node
            usersRef.child("1").child("recommendedGroups").setValue(recommendedGroups);

              gotoGroupRecommendation();
        });

        }
    private void gotoGroupRecommendation(){
        Intent intent = new Intent(MainActivity.this, GroupRecommendation.class);
        startActivity(intent);
    }
}