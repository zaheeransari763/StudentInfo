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

public class Fee extends AppCompatActivity {

    private TextView userTotalfee, userPaidfee,  userBalancefee;

    private DatabaseReference profileUserRef;
    private FirebaseAuth mAuth;
    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        profileUserRef = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUserId);


        userTotalfee = (TextView) findViewById(R.id.stud_totalfee);
        userPaidfee = (TextView) findViewById(R.id.stud_feepaid);
        userBalancefee = (TextView) findViewById(R.id.stud_balancefee);

        profileUserRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    String usertotalfee = dataSnapshot.child("total fee").getValue().toString();
                    String userpaidfee = dataSnapshot.child("paidfee").getValue().toString();
                    String userbalancefee = dataSnapshot.child("balance").getValue().toString();

                    userTotalfee.setText(usertotalfee);
                    userPaidfee.setText(userpaidfee);
                    userBalancefee.setText(userbalancefee);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}




