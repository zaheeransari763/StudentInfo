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

public class Profile extends AppCompatActivity {


    private TextView userFullname, userUID,  userEmail, userContact;

    private DatabaseReference profileUserRef;
    private FirebaseAuth mAuth;
    String currentUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        profileUserRef = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUserId);



        userFullname = (TextView) findViewById(R.id.fragment_profile_fullname);
        userEmail = (TextView) findViewById(R.id.fragment_profile_email);
        userUID = (TextView) findViewById(R.id.fragment_profile_uidno);
        userContact = (TextView) findViewById(R.id.fragment_profile_contact);

        profileUserRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)

            {
                if(dataSnapshot.exists())
                {
                    String userfullname = dataSnapshot.child("Fullname").getValue().toString();
                    String useremail = dataSnapshot.child("E-Mail").getValue().toString();
                    String useruid = dataSnapshot.child("UID").getValue().toString();
                    String usercontact = dataSnapshot.child("Contact").getValue().toString();

                    userFullname.setText(userfullname);
                    userContact.setText(usercontact);
                    userEmail.setText(useremail);
                    userUID.setText(useruid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}



