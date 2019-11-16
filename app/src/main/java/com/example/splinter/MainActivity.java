/*
 * Created By: Yashesh Savani
 * Data Created:
 *
 * */


package com.example.splinter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);



      // Get the current Instance
      mLoginAuthentication = FirebaseAuth.getInstance();

      // Get the context of variables in activity
      etEMAIL = findViewById(R.id.et_email);
      etPassword = findViewById(R.id.et_password);
      tvCreateAccount = findViewById(R.id.tv_createAccount);
      btnLogin = findViewById(R.id.btn_login);
      textInputLayoutEmail = findViewById(R.id.inputLayout_email);
      textInputLayoutPassword = findViewById(R.id.inputLayout_password);

      //Firebase Authentication Listener


      btnLogin.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              if (validateEMAIL() & validatePassword()) {

                  // Intent to home page
//                  Toast.makeText(getApplicationContext(), "EMAIL: " + EMAIL, Toast.LENGTH_SHORT).show();
//                  Toast.makeText(getApplicationContext(), "Password: " + PASSWORD, Toast.LENGTH_SHORT).show();
                  startSignIn();
              }

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


//      Initialize variable
    EditText etEMAIL, etPassword;
    Button btnLogin;
    TextView tvCreateAccount;
    String EMAIL, PASSWORD;
    TextInputLayout textInputLayoutEmail, textInputLayoutPassword;

    public FirebaseAuth mLoginAuthentication;

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
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mLoginAuthentication.getCurrentUser();
        if (currentUser != null) {
            Toast.makeText(getApplicationContext(), "User already Logged OUT", Toast.LENGTH_SHORT).show();
            signOut();


        } else {
            Toast.makeText(getApplicationContext(), "User already Logged in", Toast.LENGTH_SHORT).show();

        }
    }

    private void signOut() {
        mLoginAuthentication.signOut();
    }

    public Boolean validateEMAIL() {
        EMAIL = etEMAIL.getText().toString().trim();
        if (EMAIL.isEmpty()) {
            textInputLayoutEmail.setError("Field cannot be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()) {
            textInputLayoutEmail.setError("Please enter a valid EMAIL address");
            return false;
        } else {
            textInputLayoutEmail.setError(null);
            return true;
        }
    }

    public Boolean validatePassword() {
        PASSWORD = etPassword.getText().toString().trim();
        if (PASSWORD.isEmpty()) {
            textInputLayoutPassword.setError("Field cannot be empty");
            return false;
        } else if (!PATTERN_PASSWORD.matcher(PASSWORD).matches()) {
            textInputLayoutPassword.setError("Password should be between 8 to 24 character\n" +
                    "at least 1 special character [@#$%^&+=]\n" +
                    "at least 1 digit\n" +
                    "at least 1 capital letter\n" +
                    "at least 1 small letter\n");
            etPassword.setText("");
            return false;
        } else {
            textInputLayoutPassword.setError(null);
            return true;
        }

    }

    private void startSignIn() {
        mLoginAuthentication.signInWithEmailAndPassword(EMAIL, PASSWORD).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Sign IN Problem", Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(new Intent(MainActivity.this, Home.class));

                }

            }
        });
    }

}
