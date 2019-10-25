package com.example.splinter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class addBillActivity extends Activity {
    private ArrayAdapter<String> itemsAdapter;
    Button itemadd;
    EditText itemName, itemPrice, itemQuantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        itemName = findViewById(R.id.editTextItemName);
        itemPrice = findViewById(R.id.editTextItemPrice);
        itemQuantity = findViewById(R.id.editTextItemQuantity);
        itemadd = findViewById(R.id.btnAddItem);
        ListView lvItems;
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvItems = findViewById(R.id.lv_item);
        lvItems.setAdapter(itemsAdapter);

        itemadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, price, quantity;

                name = itemName.getText().toString();
                price = itemPrice.getText().toString();
                quantity =itemQuantity.getText().toString();
                itemsAdapter.add(name);
                itemsAdapter.add(quantity);
                itemsAdapter.add(price);
            }
        });



    }

}
