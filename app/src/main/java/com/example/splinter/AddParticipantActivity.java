/*
 * Created By: Vamsi Gamidi
 */

package com.example.splinter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddParticipantActivity extends AppCompatActivity {

  ImageButton addParticipantButton;
  EditText firstName, lastName, email;
  private ArrayList<String> fnameList = new ArrayList<>();
  private ArrayList<String> lnameList = new ArrayList<>();
  private ArrayList<String> emailList = new ArrayList<>();
  private ArrayList<String> selectedfnameList = new ArrayList<>();
  private ArrayList<String> selectedlnameList = new ArrayList<>();
  private ArrayList<String> selectedemailList = new ArrayList<>();
  FirebaseDatabase database;
  ArrayList<String> data;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_adding_participants);

    firstName = findViewById(R.id.editTextFirstName);
    lastName = findViewById(R.id.editTextLastName);
    email = findViewById(R.id.editTextEmail);
    database = FirebaseDatabase.getInstance();


    addParticipantButton = findViewById(R.id.btnAddParticipant);

    addParticipantButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (firstName.getText().toString().length() == 0 || lastName.getText().toString().length() == 0) {
          if (firstName.getText().toString().length() == 0) {
            firstName.setError("Value should not be empty");
          }
          if (lastName.getText().toString().length() == 0) {
            lastName.setError("Value should not be empty");
          }
          if (email.getText().toString().length() == 0) {
            email.setError("Value should not be empty");
          }

        } else {
          String fName, lName, mail;

          fName = firstName.getText().toString();
          lName = lastName.getText().toString();
          mail = email.getText().toString();
          fnameList.add(fName);
          lnameList.add(lName);
          emailList.add(mail);
          initRecyclerView();

          firstName.setText("");
          lastName.setText("");
          email.setText("");

        }
      }
    });

  }


  private void initRecyclerView() {
    RecyclerView recyclerView = findViewById(R.id.recycler_view_participants);
    RecyclerViewAdapterParticipants adapter = new RecyclerViewAdapterParticipants(this, fnameList, lnameList, emailList);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.add_partcipants_menu, menu);
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();


    if (id == R.id.save_button) {

      Intent intent = new Intent();
      intent.putExtra("emailList", emailList);
      intent.putExtra("participantsCount", fnameList.size());
      intent.putExtra("fnameList", fnameList);
      intent.putExtra("lnameList", lnameList);
      setResult(RESULT_OK, intent);
      finish();
    }

    return super.onOptionsItemSelected(item);
  }

}
