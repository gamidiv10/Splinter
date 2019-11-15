package com.example.splinter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

public class AddParticipantActivity extends AppCompatActivity {

    private ArrayAdapter<String> itemsAdapter;
    Button addParticipantButton;
    EditText firstName, lastName, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_participants);

        firstName = findViewById(R.id.editTextFirstName);
        lastName = findViewById(R.id.editTextLastName);
        email = findViewById(R.id.editTextEMail);
        addParticipantButton = findViewById(R.id.btnAddParticipant);
        GridView lvItems;
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvItems = findViewById(R.id.lv_item);
        lvItems.setAdapter(itemsAdapter);

        itemsAdapter.add("First Name");
        itemsAdapter.add("Last Name");
        itemsAdapter.add("Email");

        addParticipantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fName, lName, eMail;

                fName = firstName.getText().toString();
                lName = lastName.getText().toString();
                eMail = email.getText().toString();
                itemsAdapter.add(fName);
                itemsAdapter.add(lName);
                itemsAdapter.add(eMail);
            }
        });
    }
}
