/*
 * Author: Yashesh Savani
 * Contributors: Sneh Jogani
 * Date: 2019
 */


package com.example.splinter;

import android.content.Intent;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
/***************************************************************************************
 *    Title: Font
 *    Code version: 1.0
 *    Availability: Coding in Flow, https://fonts.google.com/specimen/Dancing+Script?selection.family=Dancing+Script
 ***************************************************************************************/

    Thread thread = new Thread() {
      @Override
      public void run() {
        try {
          sleep(3000);
          startActivity(new Intent(MainActivity.this, LoginActivity.class));
          finish();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };
    thread.start();
  }
}
