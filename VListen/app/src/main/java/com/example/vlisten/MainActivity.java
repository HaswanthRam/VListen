package com.example.vlisten;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RelativeLayout rel;
    //Switch[]={switch1, switch2, switch3, switch4, switch5, switch6, switch7, switch8,switch9, switch10, switch11, switch12, switch13, switch14}
    ArrayList<Switch> s = new ArrayList<>();
    ArrayList<String> concerns = new ArrayList<>();
    ArrayList<Integer> recommendedGroups = new ArrayList<>();
    Button one;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //take values from checkboxes

        s.add((Switch) this.findViewById(R.id.switch1));
        s.add((Switch) this.findViewById(R.id.switch2));
        s.add((Switch) this.findViewById(R.id.switch3));
        s.add((Switch) this.findViewById(R.id.switch4));
        s.add((Switch) this.findViewById(R.id.switch5));
        s.add((Switch) this.findViewById(R.id.switch6));
        s.add((Switch) this.findViewById(R.id.switch7));
        s.add((Switch) this.findViewById(R.id.switch8));
        s.add((Switch) this.findViewById(R.id.switch9));
        s.add((Switch) this.findViewById(R.id.switch10));
        s.add((Switch) this.findViewById(R.id.switch11));
        s.add((Switch) this.findViewById(R.id.switch12));
        s.add((Switch) this.findViewById(R.id.switch13));
        s.add((Switch) this.findViewById(R.id.switch14));

        one=(Button)findViewById(R.id.button6);
        one.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
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
                //Specially Able
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




//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference myRef = database.getReference("Concerns ");
//                myRef.setValue(concerns);
                // Database reference pointing to root of database
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                // Database reference pointing to demo node
                DatabaseReference usersRef = rootRef.child("Users");
                usersRef.child("2").child("concerns").setValue(concerns);



                // Database reference pointing to demo node
                usersRef.child("2").child("recommendedGroups").setValue(recommendedGroups);

            }});
    }
}