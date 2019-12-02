package com.example.splinter;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database extends AppCompatActivity {

private DatabaseReference mDatabase;

    protected void onStart() {

        super.onStart();
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

}
