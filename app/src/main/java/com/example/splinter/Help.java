/*
 * Author: Prem Kumar Menni
 * Date: 2019
 */

package com.example.splinter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class Help extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_help);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

  }

}
