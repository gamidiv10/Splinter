package com.example.splinter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddBillActivity extends AppCompatActivity {
    private ArrayList<String> itemList = new ArrayList<>();
    private ArrayList<String> itemQtyList = new ArrayList<>();
    private ArrayList<String> itemPriceList = new ArrayList<>();
    private double billTotal = 0;

    private ArrayList<String> emailList = new ArrayList<>();
    private ArrayList<String> fnameList = new ArrayList<>();
    private ArrayList<String> lnameList = new ArrayList<>();


    public boolean isBillSaved = false;
    public Menu billmenu;
    String strEditText;
    Integer participantsCount;
    Html html;
    ImageButton itemadd;
    EditText itemName, itemPrice, itemQuantity;
    TextInputEditText billName;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);


        itemName = findViewById(R.id.editTextItemName);
        itemPrice = findViewById(R.id.editTextItemPrice);
        itemQuantity = findViewById(R.id.editTextItemQuantity);
        itemadd = findViewById(R.id.btnAddItem);
        billName = findViewById(R.id.source_txt);

        itemadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(itemName.getText().toString().length() == 0 || itemPrice.getText().toString().length() == 0
                        || itemQuantity.getText().toString().length() == 0)
                {
                    if (itemName.getText().toString().length() == 0)
                    {
                        itemName.setError("Value should not be empty");
                    }
                    if (itemPrice.getText().toString().length() == 0)
                    {
                        itemPrice.setError("Value should not be empty");
                    }
                    if (itemQuantity.getText().toString().length() == 0)
                    {
                        itemQuantity.setError("Value should not be empty");
                    }
                }
                else
                    {

                    String name, price, quantity, totals;
                    name = itemName.getText().toString();
                    price = itemPrice.getText().toString();
                    quantity = itemQuantity.getText().toString();
                    double total = Double.parseDouble(price) * Double.parseDouble(quantity);
                    billTotal += total;
                    totals = String.valueOf(total);
                    itemList.add(name);
                    itemQtyList.add("Qty:" + quantity);
                    itemPriceList.add("$" + totals);

                    // calculating amount the user is owed
                    // individualShare = billTotal / (participantsCount + 1)
                    // amountOwed = individualShare * participantsCount

                    initRecyclerView();

                    itemName.setText("");
                    itemPrice.setText("");
                    itemQuantity.setText("");


                        MenuItem saveItem = billmenu.findItem(R.id.save_button);

                        if(!itemList.isEmpty() && !emailList.isEmpty())
                        {
                            saveItem.setEnabled(true);
                            saveItem.getIcon().setAlpha(200);
                        }
                }
            }
        });

   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.add_bill_menu, menu);
        billmenu = menu;
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        MenuItem sendEmailItem = menu.findItem(R.id.send_email_button);
        MenuItem saveItem = menu.findItem(R.id.save_button);

//        if (!isBillSaved) {
//            sendEmailItem.setEnabled(false);
//            sendEmailItem.getIcon().setAlpha(130);
//        }

//        if(itemList.isEmpty() || emailList.isEmpty())
//        {
//            saveItem.setEnabled(false);
//            saveItem.getIcon().setAlpha(130);
//        }
        return super.onPrepareOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_participants_button) {
            Intent intentForAddingParticipant = new Intent(AddBillActivity.this, AddParticipantActivity.class);
            startActivityForResult(intentForAddingParticipant, 1);
        }

        if (id == R.id.save_button)
        {

            if (billName.getText().toString().length() == 0)
            {
                billName.setError("Value should not be empty");
            }
            else {

                mDatabase = FirebaseDatabase.getInstance().getReference();
                ArrayList<Participant> participantObjList = new ArrayList<Participant>();

                for (int i = 0; i < fnameList.size(); i++) {
                    //String itemID = mDatabase.push().getKey();

                    Participant participant = new Participant(fnameList.get(i), lnameList.get(i), emailList.get(i));
                    //mDatabase.child("users").child(itemID).setValue(participant);
                    participantObjList.add(participant);

                }
                ArrayList<Item> itemObjList = new ArrayList<Item>();
                Double totalAmount = Double.valueOf(0);

                for (int i = 0; i < itemList.size(); i++) {
                    //String itemID = mDatabase.push().getKey();
                    Item itemObj = new Item(itemList.get(i), itemPriceList.get(i).substring(1), itemQtyList.get(i).substring(4));
                    itemObjList.add(itemObj);
                    totalAmount += Double.parseDouble(itemObj.itemPrice);

                    //mDatabase.child("items").child(itemID).setValue(itemObj);

                }
                Date dateNow = Calendar.getInstance().getTime();

                String itemID = mDatabase.push().getKey();
                Bill bill = new Bill(billName.getText().toString(), dateNow, totalAmount, itemObjList, participantObjList);
                mDatabase.child("bills").child(itemID).setValue(bill);

                Intent intent = new Intent();
                intent.putExtra("billName", billName.getText());
                intent.putExtra("totalAmount", totalAmount);

                setResult(RESULT_OK, intent);
                finish();

            }
            MenuItem sendEmailItem = billmenu.findItem(R.id.send_email_button);
            if (isBillSaved) {
                sendEmailItem.setEnabled(true);
                sendEmailItem.getIcon().setAlpha(200);
            }
        }
        String body = new String("<html> <header> Hi, you are added into the bill with name  </header> <body> <div> Splinter" +
                "dated November 11 2019 by Team 7 </div> <br>" +
                "<p> Totalbill amount id 100$ <p> <br>" +
                "Amount you have to pay to the user is 20$ <br>");

        if (id == R.id.send_email_button) {

            String subject = "Splitter - New Bill Added";
            String message = "Bill Details";
            String[] mailList = new String[emailList.size()];

            for (int i = 0; i < emailList.size(); i++) {
                mailList[i] = emailList.get(i);
            }
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, mailList);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, html.fromHtml(body));
            intent.setType("text/html");
            startActivity(Intent.createChooser(intent, "Choose an Email client :"));
        } else {
            Toast.makeText(getApplicationContext(), "Please save the bill to send via email", Toast.LENGTH_SHORT);
        }

        return super.onOptionsItemSelected(item);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                MenuItem sendEmailItem = billmenu.findItem(R.id.send_email_button);

                emailList = data.getStringArrayListExtra("emailList");
                participantsCount = data.getIntExtra("participantsCount", 0);
                fnameList = data.getStringArrayListExtra("fnameList");
                lnameList = data.getStringArrayListExtra("lnameList");

//                mDatabase = FirebaseDatabase.getInstance().getReference();

//                String id = mDatabase.push().getKey();
                for(int i = 0; i < fnameList.size() ; i++)
                {
//                    String id = mDatabase.push().getKey();

//                    Participant participant = new Participant(fnameList.get(i), lnameList.get(i), emailList.get(i));
//                    mDatabase.child("users").child(id).setValue(participant);

                }

//                if (isBillSaved) {
//                    sendEmailItem.setEnabled(true);
//                    sendEmailItem.getIcon().setAlpha(200);
//                }
                MenuItem saveItem = billmenu.findItem(R.id.save_button);

//                if(!itemList.isEmpty() && !emailList.isEmpty())
//                {
//                    saveItem.setEnabled(true);
//                    saveItem.getIcon().setAlpha(200);
//                }
            }
        }

    }

    private void initRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, itemList, itemQtyList, itemPriceList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
