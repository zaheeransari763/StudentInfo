package com.example.studentinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Academics extends AppCompatActivity {

    private TextView userField, userYear,  userShift;

    private DatabaseReference profileUserRef;
    private FirebaseAuth mAuth;
    String currentUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academics);


        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        profileUserRef = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUserId);


        userField = (TextView) findViewById(R.id.stud_field);
        userYear = (TextView) findViewById(R.id.stud_year);
        userShift = (TextView) findViewById(R.id.stud_shift);

        profileUserRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    String userfield = dataSnapshot.child("Field").getValue().toString();
                    String useryear = dataSnapshot.child("Year").getValue().toString();
                    String usershift = dataSnapshot.child("shift").getValue().toString();

                    userField.setText(userfield);
                    userYear.setText(useryear);
                    userShift.setText(usershift);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
    }
}


