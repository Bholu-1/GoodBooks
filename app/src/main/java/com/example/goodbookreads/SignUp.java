package com.example.goodbookreads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    private EditText etmail,ename,etpass,etcnf;
    private Button Signup,Login;

    private FirebaseAuth mauth2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ename = (EditText) findViewById(R.id.etFullName);
        etmail = (EditText) findViewById(R.id.etEmail);
        etpass = (EditText) findViewById(R.id.etPassword);
        etcnf = (EditText) findViewById(R.id.etCnf);

        Signup = (Button) findViewById(R.id.SignUp);
        Login = (Button) findViewById(R.id.Login);

        mauth2 = FirebaseAuth.getInstance();
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = ename.getText().toString().trim();
                final String mail = etmail.getText().toString().trim();
                final String pass = etpass.getText().toString().trim();
                final String cnf = etcnf.getText().toString().trim();

                if (TextUtils.isEmpty(name)){
                    Toast.makeText(SignUp.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(mail)){
                    Toast.makeText(SignUp.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty((pass))){
                    Toast.makeText(SignUp.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(cnf)){
                    Toast.makeText(SignUp.this, "Please Enter Confirm Password", Toast.LENGTH_SHORT).show();
                }
                if (pass.length()<6){
                    Toast.makeText(SignUp.this, "Password Is Short!", Toast.LENGTH_SHORT).show();
                }
                if (pass.equals(cnf)) {

                    mauth2.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                Intent i = new Intent(SignUp.this ,MainActivity.class);
                                startActivity(i);
                                finish();
                            } else
                                Toast.makeText(SignUp.this, "Authintication Fail", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }
}
