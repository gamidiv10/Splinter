
//
// Created by Yashesh Savani on 11/26/2019.
//

package com.example.splinter.Model;

import com.example.splinter.SignupActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupDatabaseActivity {


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    protected DatabaseReference referenceToRoot = firebaseDatabase.getReference("https://splinter-f86ee.firebaseio.com/");

    public void writeUserdata() {
        SignupActivity signupActivity = new SignupActivity();
        referenceToRoot.child("Users").child("Email").setValue(signupActivity.enteredEmail);
        referenceToRoot.child("Email").child("First_Name").setValue(signupActivity.firstName);
        referenceToRoot.child("Email").child("Last_Name").setValue(signupActivity.lastName);

    }

}
