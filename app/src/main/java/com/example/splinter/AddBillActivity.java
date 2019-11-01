package com.example.splinter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

public class AddBillActivity extends Activity {
    private ArrayAdapter<String> itemsAdapter;
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
        billDelete = findViewById(R.id.billDelete);
        GridView lvItems;
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvItems = findViewById(R.id.lv_item);
        lvItems.setAdapter(itemsAdapter);
        itemsAdapter.add("Item");
        itemsAdapter.add("Qty");
        itemsAdapter.add("Price");
        itemsAdapter.add("Total");
        itemadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, price, quantity,totals;
                name = itemName.getText().toString();
                price = itemPrice.getText().toString();
                quantity =itemQuantity.getText().toString();
                double total = Integer.parseInt(price) * Integer.parseInt(quantity);
                totals = String.valueOf(total);
                itemsAdapter.add(name);
                itemsAdapter.add(quantity);
                itemsAdapter.add(price);
                itemsAdapter.add(totals);
            }
        });

        billDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });

    }







}
