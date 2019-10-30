package com.example.splinter;
import android.content.Intent;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Jasper Jiao on 2019-9-10.
 */
public class MainActivity extends AppCompatActivity {
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Intent i = new Intent(this,Bluetooth.class);
    startActivity(i);
  }
}