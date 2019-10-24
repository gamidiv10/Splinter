package com.example.splinter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

  private Button addBillButton;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    addBillButton = findViewById(R.id.addBillButton);

    addBillButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      Intent intentForAddingBill = new Intent(MainActivity.this, addBillActivity.class);

        startActivity(intentForAddingBill);

      }
    });



  }
//  @Override
//  public boolean onCreateOptionsMenu(Menu menu) {
//    getMenuInflater().inflate(R.menu.activity_main, menu);
//    return true;
//  }

}
