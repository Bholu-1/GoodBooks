package com.example.goodbookreads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText etmail,etpassword;
    private Button Login, SignUp;
    private TextView forgot;

    private FirebaseAuth mauth1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etmail = (EditText) findViewById(R.id.etEmail);
        etpassword = (EditText) findViewById(R.id.etPassword);
        forgot = (TextView) findViewById(R.id.txtForgotPassword);

        Login = (Button) findViewById(R.id.Login1);
        SignUp = (Button) findViewById(R.id.SignUp1);

        mauth1 = FirebaseAuth.getInstance();

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(MainActivity.this,ForgotPassword.class);
                startActivity(signup);
                finish();

            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(MainActivity.this,SignUp.class);
                startActivity(signup);
                finish();

            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name1 = etmail.getText().toString().trim();
                final String pass1 = etpassword.getText().toString().trim();

                mauth1.signInWithEmailAndPassword(name1, pass1).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        Intent i = new Intent(MainActivity.this, Try.class);
                        startActivity(i);

                        finish();
                    }
                }).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }

        });

    }
}
