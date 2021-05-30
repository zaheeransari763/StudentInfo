package com.example.studentinfo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button LoginButton;
    private EditText UserEmail,UserPassword;
    private TextView Regtext;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        UserEmail = (EditText) findViewById(R.id.et_email);
        UserPassword = (EditText) findViewById(R.id.et_password);
        LoginButton = (Button) findViewById(R.id.btn_login);
        LoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AllowUserToLogin();
            }
        });
        Regtext=(TextView) findViewById(R.id.reg_id);
        Regtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"You are on the registration page",Toast.LENGTH_LONG);
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);


            }
        });



    }



    private void AllowUserToLogin() {
        final String email = UserEmail.getText().toString();
        String password = UserPassword.getText().toString();
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "E-mail is Mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Toast.makeText(MainActivity.this, "Incorrect E-mail", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Pass is Mandatory...", Toast.LENGTH_SHORT).show();
        }
        else

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if (task.isSuccessful())
                    {
                        SendUserToMainActivity();
                        Toast.makeText(MainActivity.this, "Logged In Successfully...", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        String message = task.getException().getMessage();
                        Toast.makeText(MainActivity.this, "Error Occurred:" + message, Toast.LENGTH_SHORT).show();

                    }
                }
            });
    }


    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(MainActivity.this,StartActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();

    }

}
