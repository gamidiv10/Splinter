package com.example.splinter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    EditText etEnterEmail, etEnterPassword, etReenterPassword, etFirstName, etLastName;
    Button btnSignup;
    String enteredEmail, enteredPassword, reenteredPassword, firstName, lastName;
    TextInputLayout inputLayoutEnterEmail, inputLayoutEnterPassword, inputLayoutReenterPassword, inputLayoutFirstName, inputLayoutLastName;


    // Firebase Authentication
    FirebaseAuth mSignupAuthentication;


    // Password validation pattern //https://codinginflow.com/tutorials/android/validate-email-password-regular-expressions
    public static final Pattern PATTERN_PASSWORD =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,24}" +               //at least between 8 to 24 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etEnterEmail = findViewById(R.id.et_enterEmail);
        etEnterPassword = findViewById(R.id.et_enterPassword);
        etReenterPassword = findViewById(R.id.et_reenterPassword);
        etFirstName = findViewById(R.id.et_firstName);
        etLastName = findViewById(R.id.et_lastName);
        btnSignup = findViewById(R.id.btn_signup);
        inputLayoutEnterEmail = findViewById(R.id.inputLayout_enterEmail);
        inputLayoutEnterPassword = findViewById(R.id.inputLayout_enterPassword);
        inputLayoutReenterPassword = findViewById(R.id.inputLayout_reenterPassword);
        inputLayoutFirstName = findViewById(R.id.inputLayout_firstName);
        inputLayoutLastName = findViewById(R.id.inputLayout_lastName);


        // Firebase get running Instance
        mSignupAuthentication = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isvalidEmail() & isvalidPassword() & isvalidConfirmPassword()) {
                    Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_SHORT).show();
                    createUSerInDatabase();
                }

            }
        });
    }

    private void createUSerInDatabase() {

        mSignupAuthentication.createUserWithEmailAndPassword(enteredEmail, enteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Error in User creation! Try Again!", Toast.LENGTH_SHORT).show();
                } else {
                    // After Signin it will go to the dashBoard
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });
    }

    public Boolean isvalidEmail() {
        enteredEmail = etEnterEmail.getText().toString().trim();
        if (enteredEmail.isEmpty()) {
            inputLayoutEnterEmail.setError("Field cannot be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(enteredEmail).matches()) {
            inputLayoutEnterEmail.setError("Please enter a valid EMAIL address");
            return false;
        } else {
            inputLayoutEnterEmail.setError(null);
            return true;
        }
    }

    private Boolean isvalidPassword() {
        enteredPassword = etEnterPassword.getText().toString().trim();
        if (enteredPassword.isEmpty()) {
            inputLayoutEnterPassword.setError("Field cannot be empty");
            return false;
        } else if (!PATTERN_PASSWORD.matcher(enteredPassword).matches()) {
            inputLayoutEnterPassword.setError("Password should be between 8 to 24 character\n" +
                    "at least 1 special character [@#$%^&+=]\n" +
                    "at least 1 digit\n" +
                    "at least 1 capital letter\n" +
                    "at least 1 small letter\n");
            etEnterPassword.setText("");
            return false;
        } else {
            inputLayoutEnterPassword.setError(null);
            return true;
        }
    }

    private Boolean isvalidConfirmPassword() {
        reenteredPassword = etReenterPassword.getText().toString().trim();
        if (reenteredPassword.isEmpty()) {
            inputLayoutReenterPassword.setError("Field cannot be Empty");
            return false;
        } else if (!enteredPassword.equals(reenteredPassword)) {
            inputLayoutReenterPassword.setError("Doesn't match the given Password");
            etReenterPassword.setText("");
            return false;
        } else {
            inputLayoutReenterPassword.setError(null);
            return true;
        }
    }
}
