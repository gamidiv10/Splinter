/*
 * Author: Prem Kumar Menni
 * Date: 2019
 */

package com.example.splinter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Help extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_help);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    final ListView listview = (ListView) findViewById(R.id.faq_list);
    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(Help.this, Help_content.class);
        intent.putExtra("Help Text", listview.getItemAtPosition(position).toString());
        startActivity(intent);
      }
    });
  }

}
