package com.example.splinter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

  private Button addBillButton, addParticipantButton;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    addBillButton = findViewById(R.id.addBillButton);
    addParticipantButton = findViewById(R.id.addParticipantButton);

    addBillButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Intent intentForAddingBill = new Intent(MainActivity.this, AddBillActivity.class);

        startActivity(intentForAddingBill);

      }
    });

    addParticipantButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Intent intentForAddingParticipant = new Intent(MainActivity.this, AddParticipantActivity.class);

        startActivity(intentForAddingParticipant);
      }


    });
  }

}
