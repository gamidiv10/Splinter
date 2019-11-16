package com.example.splinter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class AddBillActivity extends AppCompatActivity {
    private ArrayList<String> itemList = new ArrayList<>();
    private ArrayList<String> itemQtyList = new ArrayList<>();
    private ArrayList<String> itemPriceList = new ArrayList<>();


    Button itemadd, billDelete;
    EditText itemName, itemPrice, itemQuantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);


        itemName = findViewById(R.id.editTextItemName);
        itemPrice = findViewById(R.id.editTextItemPrice);
        itemQuantity = findViewById(R.id.editTextItemQuantity);
        itemadd = findViewById(R.id.btnAddItem);


        itemadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, price, quantity,totals;
                name = itemName.getText().toString();
                price = itemPrice.getText().toString();
                quantity =itemQuantity.getText().toString();
                //double total = Integer.parseInt(price) * Integer.parseInt(quantity);
                double total = Double.parseDouble(price) * Double.parseDouble(quantity);
                totals = String.valueOf(total);
                itemList.add(name);
                itemQtyList.add("Qty:" + quantity);
                itemPriceList.add("$" + totals);

                initRecyclerView();

                itemName.setText("");
                itemPrice.setText("");
                itemQuantity.setText("");

            }
        });
//
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.add_participants_button) {

            Intent intentForAddingParticipant = new Intent(AddBillActivity.this, AddParticipantActivity.class);

            startActivity(intentForAddingParticipant);
        }

        return super.onOptionsItemSelected(item);
    }


    private void initRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, itemList, itemQtyList, itemPriceList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
