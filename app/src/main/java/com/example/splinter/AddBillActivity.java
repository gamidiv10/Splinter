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


    private void setToggleEvent(GridView lvItems){

        for(int i=0; i<lvItems.getChildCount(); i++) {

            final CardView cardView = (CardView) lvItems.getChildAt(i);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cardView.getCardBackgroundColor().getDefaultColor() == -1)
                    {
                        cardView.setCardBackgroundColor(Color.parseColor("#FF6F00"));
                        Toast.makeText(addBillActivity.this, "State :  True", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        Toast.makeText(addBillActivity.this, "State :  False", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    private void setSingleEvent(GridView lvItems){

        for(int i=0; i<lvItems.getChildCount(); i++)
        {

            CardView cardView = (CardView)lvItems.getChildAt(i);

            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(addBillActivity.this, "Clicked at index", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
