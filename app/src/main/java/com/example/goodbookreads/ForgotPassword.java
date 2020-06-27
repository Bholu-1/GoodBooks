package com.example.goodbookreads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText etres;
    private Button reset;
    private FirebaseAuth mauth3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etres = (EditText) findViewById(R.id.etreset);
        reset = (Button) findViewById(R.id.reset);
        mauth3 = FirebaseAuth.getInstance();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usermail = etres.getText().toString().trim();

                if (usermail.equals("")){
                    Toast.makeText(ForgotPassword.this,"Please Enter Your Registered Email Id",Toast.LENGTH_SHORT).show();
                }else{
                    mauth3.sendPasswordResetEmail(usermail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                             if(task.isSuccessful()){
                                 Toast.makeText(ForgotPassword.this,"Reset Email link sent!",Toast.LENGTH_LONG);
                                 Intent i = new Intent(ForgotPassword.this,MainActivity.class);
                                 startActivity(i);
                                 finish();
                             }else{
                                 Toast.makeText(ForgotPassword.this,"Error",Toast.LENGTH_SHORT);
                             }
                        }
                    });
                }

            }
        });
    }
}
