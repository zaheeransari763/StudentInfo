package com.example.studentinfo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText userName, userEmail, userCon, userField, userYear, userPwd, userUid;
    private Button userReg;

    private FirebaseAuth mAuth;
    private DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ProgressDialog loadingbar = new ProgressDialog(this);

        userName = findViewById(R.id.stud_name);
        userEmail = findViewById(R.id.stud_email);
        userCon = findViewById(R.id.stud_phone);
        userField = findViewById(R.id.stud_field);
        userYear = findViewById(R.id.stud_year);
        userPwd = findViewById(R.id.stud_pass);
        userUid = findViewById(R.id.stud_uid);

        userReg = findViewById(R.id.btn_regis);

        mAuth = FirebaseAuth.getInstance();

        userReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }
        });

    }

    private void CreateNewAccount() {
        final String name = userName.getText().toString();
        final String email = userEmail.getText().toString();
        final String contact = userCon.getText().toString();
        final String field = userField.getText().toString();
        final String year = userYear.getText().toString();
        final String password = userPwd.getText().toString();
        final String uid = userUid.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Name is Mandatory...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "E-mail is empty...", Toast.LENGTH_SHORT).show();
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Toast.makeText(RegisterActivity.this, "Incorrect E-mail", Toast.LENGTH_SHORT).show();
        }if (TextUtils.isEmpty(contact)) {
            Toast.makeText(this, "Contact is empty...", Toast.LENGTH_SHORT).show();
        } else if (contact.length() != 10) {
            Toast.makeText(this, "Invalid Contact...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(field)) {
            Toast.makeText(this, "Field be empty...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(uid)) {
            Toast.makeText(this, "UID required...", Toast.LENGTH_SHORT).show();
        } else if (uid.length() != 10) {
            Toast.makeText(this, "Invaild UID...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Create Password...", Toast.LENGTH_SHORT).show();
        } else {

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        String studUserId = firebaseUser.getUid();
                        reference = FirebaseDatabase.getInstance().getReference("Students").child(studUserId);

                        HashMap<String, String> studMap = new HashMap();
                        studMap.put("Fullname", name);
                        studMap.put("E-Mail",email);
                        studMap.put("Contact",contact);
                        studMap.put("Field", field);
                        studMap.put("Year",year);
                        studMap.put("Password",password);
                        studMap.put("UID",uid);
                        studMap.put("shift","Morning");
                        studMap.put("total fee","26000");
                        studMap.put("balance","13000");
                        studMap.put("paidfee","12000");


                        reference.setValue(studMap).addOnCompleteListener(new OnCompleteListener<Void>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if (task.isSuccessful())
                                {
                                    Intent studMainIntent = new Intent(RegisterActivity.this,MainActivity.class);
                                    studMainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(studMainIntent);
                                    finish();
                                }
                            }
                        });

                        Toast.makeText(RegisterActivity.this, "Authenticated Successfully...", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        String message = task.getException().getMessage();
                        Toast.makeText(RegisterActivity.this, "Error Occurred ;" + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}