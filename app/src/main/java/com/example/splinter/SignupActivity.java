package com.example.splinter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    EditText etEnterEmail, etEnterPassword, etReenterPassword;
    Button btnSignup;
    String enteredEmail, enteredPassword, reenteredPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etEnterEmail = findViewById(R.id.et_enterEmail);
        etEnterPassword = findViewById(R.id.et_enterPassword);
        etReenterPassword = findViewById(R.id.et_reenterPassword);
        btnSignup = findViewById(R.id.btn_signup);


        /* Email & Password validation are remaining */

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enteredEmail = etEnterEmail.getText().toString();
                enteredPassword = etEnterPassword.getText().toString();
                reenteredPassword = etReenterPassword.getText().toString();

//                Toast.makeText(getApplicationContext(), "Entered Email: " + enteredEmail, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "Entered Password: " + enteredPassword, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(),"Reentered Password: " + reenteredPassword,Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_SHORT).show();
                Intent intentToLogin = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intentToLogin);
            }
        });

    }
}
