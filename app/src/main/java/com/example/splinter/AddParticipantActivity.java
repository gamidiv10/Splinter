package com.example.splinter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

public class AddParticipantActivity extends AppCompatActivity {

    Button addParticipantButton;
    EditText firstName, lastName, email;
    private ArrayList<String> fnameList = new ArrayList<>();
    private ArrayList<String> lnameList = new ArrayList<>();
    private ArrayList<String> emailIdList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_participants);

        firstName = findViewById(R.id.editTextFirstName);
        lastName = findViewById(R.id.editTextLastName);
        email = findViewById(R.id.editTextEMail);

        addParticipantButton = findViewById(R.id.btnAddParticipant);


        addParticipantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fName, lName, eMail;

                fName = firstName.getText().toString();
                lName = lastName.getText().toString();
                eMail = email.getText().toString();
                fnameList.add(fName);
                lnameList.add(lName);
                emailIdList.add(eMail);

                initRecyclerView();

                firstName.setText("");
                lastName.setText("");
                email.setText("");
            }
        });
    }
    private void initRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_participants);
        RecyclerViewAdapterParticipants adapter = new RecyclerViewAdapterParticipants(this, fnameList, lnameList, emailIdList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
