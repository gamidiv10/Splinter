/*
 * Author: Yashesh Savani
 * Contributors:
 * Date: 2019
 */

package com.example.splinter;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

import static com.example.splinter.R.id;
import static com.example.splinter.R.layout;
import static com.example.splinter.R.string;

public class SignupActivity extends AppCompatActivity {

    // Password validation pattern

    /***************************************************************************************
     *    Title: Validate Email and Password with Regular Expression
     *    Author: Coding in Flow
     *    Code version: 1.0
     *    Availability: Coding in Flow, https://codinginflow.com/tutorials/android/validate-email-password-regular-expressionscoo
     ***************************************************************************************/
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
    public EditText etEnterEmail, etEnterPassword, etReenterPassword, etFirstName, etLastName, etPhoneNumber;
    public Button btnSignup;
    public String enteredEmail, enteredPassword, reenteredPassword, firstName, lastName, phoneNumber, countryCode;
    public TextInputLayout inputLayoutEnterEmail, inputLayoutEnterPassword, inputLayoutReenterPassword;
    public TextInputLayout inputLayoutFirstName, inputLayoutLastName, inputLayoutPhoneNumber;
    Spinner countryCodeSpinner;

    String passwordError = "Password should be between 8 to 24 character\n" +
            "at least 1 special character [@#$%^&+=]\n" +
            "at least 1 digit\n" +
            "at least 1 capital letter\n" +
            "at least 1 small letter\n";
    // Firebase Authentication
    FirebaseAuth mSignupAuthentication;

    FirebaseDatabase signUpDB = FirebaseDatabase.getInstance("https://splinter-f86ee.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_signup);

        etEnterEmail = findViewById(id.et_enterEmail);
        etEnterPassword = findViewById(id.et_enterPassword);
        etReenterPassword = findViewById(id.et_reenterPassword);
        etFirstName = findViewById(id.et_firstName);
        etLastName = findViewById(id.et_lastName);
        btnSignup = findViewById(id.btn_signup);
        inputLayoutEnterEmail = findViewById(id.inputLayout_enterEmail);
        inputLayoutEnterPassword = findViewById(id.inputLayout_enterPassword);
        inputLayoutReenterPassword = findViewById(id.inputLayout_reenterPassword);
        inputLayoutFirstName = findViewById(id.inputLayout_firstName);
        inputLayoutLastName = findViewById(id.inputLayout_lastName);
        inputLayoutPhoneNumber = findViewById(id.inputLayout_phoneNumber);
        countryCodeSpinner = findViewById(id.spinner_countryCode);
        etPhoneNumber = findViewById(id.et_phoneNumber);

        // Firebase get running Instance
        mSignupAuthentication = FirebaseAuth.getInstance();

        countryCodeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                countryCode = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isvalidEmail() & isvalidPassword() & isvalidConfirmPassword() & isvalidFirstname() & isvalidLastname() & isvalidPhoneNumber()) {
//                    Toast.makeText(getApplicationContext(), " Creating Account...", Toast.LENGTH_SHORT).show();

                    Boolean success = writeUserdata();
                    if (success) {
                        createUserInDatabase();
                    } else {
                        Toast.makeText(getApplicationContext(), string.error_in_user_creation, Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    public Boolean writeUserdata() {
        try {
            DatabaseReference referenceToRoot = signUpDB.getReference();
            DatabaseReference userRef = referenceToRoot.child("Users");
            DatabaseReference emailRef = userRef.push().child("Email");
            emailRef.child("Email_value").setValue(enteredEmail);
            emailRef.child("First_name").setValue(firstName);
            emailRef.child("Last_name").setValue(lastName);
            emailRef.child("Phone_Number").setValue(countryCode + phoneNumber);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager Internet_cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return Internet_cm.getActiveNetworkInfo() != null && Internet_cm.getActiveNetworkInfo().isConnected();
    }

    private void createUserInDatabase() {

        if (isNetworkConnected()) {
            mSignupAuthentication.createUserWithEmailAndPassword(enteredEmail, enteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), string.error_in_user_creation, Toast.LENGTH_SHORT).show();

                    } else {
                        // After Signin it will go to the dashBoard
                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Please check the internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public Boolean isvalidFirstname() {
        firstName = etFirstName.getText().toString().trim();
        if (firstName.isEmpty()) {
            inputLayoutFirstName.setError(getString(R.string.no_empty_field));
            return false;
        } else {
            inputLayoutFirstName.setError(null);
            return true;
        }
    }

    public Boolean isvalidLastname() {
        lastName = etLastName.getText().toString().trim();
        if (lastName.isEmpty()) {
            inputLayoutLastName.setError(getString(string.no_empty_field));
            return false;
        } else {
            inputLayoutLastName.setError(null);
            return true;
        }
    }

    public Boolean isvalidEmail() {
        enteredEmail = etEnterEmail.getText().toString().trim();
        if (enteredEmail.isEmpty()) {
            inputLayoutEnterEmail.setError(getString(string.no_empty_field));
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
            inputLayoutEnterPassword.setError(getString(string.no_empty_field));
            return false;
        } else if (!PATTERN_PASSWORD.matcher(enteredPassword).matches()) {
            inputLayoutEnterPassword.setError(passwordError);
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
            inputLayoutReenterPassword.setError(getString(string.no_empty_field));
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

    private Boolean isvalidPhoneNumber() {
        phoneNumber = etPhoneNumber.getText().toString().trim();
        if (phoneNumber.isEmpty()) {
            inputLayoutPhoneNumber.setError(getString(string.no_empty_field));
            return false;
        } else if (phoneNumber.length() != 10) {
            inputLayoutPhoneNumber.setError("Number not less than 10 digits");
            etPhoneNumber.setText("");
            return false;
        } else {
            inputLayoutPhoneNumber.setError(null);
            return true;
        }
    }
}
