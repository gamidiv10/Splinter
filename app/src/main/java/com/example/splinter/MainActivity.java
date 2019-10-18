package com.example.splinter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //  Initialize variable
    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvCreateAccount;
    String EMAIL, PASSWORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the context of variables in activity
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        tvCreateAccount = findViewById(R.id.tv_createAccount);
        btnLogin = findViewById(R.id.btn_login);

        /* Email & Password Validation are remaining */

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EMAIL = etEmail.getText().toString();
                PASSWORD = etPassword.getText().toString();
                Toast.makeText(getApplicationContext(), "Email: " + EMAIL, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Password: " + PASSWORD, Toast.LENGTH_SHORT).show();

            }
        });

        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToSignup = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intentToSignup);

            }
        });

    }
}
