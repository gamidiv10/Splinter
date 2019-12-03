/*
 * Author: Vamsi Gamidi
 * Contributors: Prem Kumar Menni, Sneh Jogani
 * Date: 2019
 */

package com.example.splinter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@SuppressWarnings({"CanBeFinal", "unused"})
public class AddBillActivity extends AppCompatActivity {
  private final ArrayList<String> itemList = new ArrayList<>();
  private final ArrayList<String> itemQtyList = new ArrayList<>();
  private final ArrayList<String> itemPriceList = new ArrayList<>();
  private double billTotal = 0;
    private ArrayList<String> billNameList = new ArrayList<>();
    private ArrayList<String> billAmountList = new ArrayList<>();
    private ArrayList<String> owedAmountList = new ArrayList<>();

  private ArrayList<String> emailList = new ArrayList<>();
  private ArrayList<String> fnameList = new ArrayList<>();
  private ArrayList<String> lnameList = new ArrayList<>();

  private Menu billmenu;
  private Html html;
  private EditText itemName;
  private EditText itemPrice;
  private EditText itemQuantity;
  private TextInputEditText billName;
  @SuppressWarnings("FieldCanBeLocal")
  private boolean isBillSaved = false;
  String strEditText;
  ImageButton itemadd;
  Integer participantsCount;
  Double individualShare, amountOwed;
  MenuItem saveItem;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_bill);

    // fetching textViews
    itemName = findViewById(R.id.editTextItemName);
    itemPrice = findViewById(R.id.editTextItemPrice);
    itemQuantity = findViewById(R.id.editTextItemQuantity);
    ImageButton itemadd = findViewById(R.id.btnAddItem);
    billName = findViewById(R.id.source_txt);

    itemadd.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        // validating item name and price entries
        if (itemName.getText().toString().length() == 0 || itemPrice.getText().toString().length() == 0
                || itemQuantity.getText().toString().length() == 0) {
          if (itemName.getText().toString().length() == 0) {
            itemName.setError("Value should not be empty");
          }
          if (itemPrice.getText().toString().length() == 0) {
            itemPrice.setError("Value should not be empty");
          }
          if (itemQuantity.getText().toString().length() == 0) {
            itemQuantity.setError("Value should not be empty");
          }
        } else {
          Intent intent = getIntent();
          if (intent.getStringArrayListExtra("billName") != null) {
            billNameList = intent.getStringArrayListExtra("billName");
          }
          if (intent.getStringArrayListExtra("billAmount") != null) {
            billAmountList = intent.getStringArrayListExtra("billAmount");
          }
          if (intent.getStringArrayListExtra("owedAmount") != null) {
            owedAmountList = intent.getStringArrayListExtra("owedAmount");
          }
          // Assigning edittext ids to variables
          itemName = findViewById(R.id.editTextItemName);
          itemPrice = findViewById(R.id.editTextItemPrice);
          itemQuantity = findViewById(R.id.editTextItemQuantity);
          billName = findViewById(R.id.source_txt);
          String name, price, quantity, totals;
          name = itemName.getText().toString();
          price = itemPrice.getText().toString();
          quantity = itemQuantity.getText().toString();
          // calculating total bill amount
          double total = Double.parseDouble(price) * Double.parseDouble(quantity);
          billTotal += total;
          totals = String.valueOf(total);
          itemList.add(name);
          itemQtyList.add("Qty:" + quantity);
          itemPriceList.add("$" + totals);
          // initializing recycler view with the data received
          initRecyclerView();
          // resetting editTextView values to empty
          itemName.setText("");
          itemPrice.setText("");
          itemQuantity.setText("");
          MenuItem saveItem = billmenu.findItem(R.id.save_button);
          if (!itemList.isEmpty() && !emailList.isEmpty()) {
            saveItem.setEnabled(true);
            saveItem.getIcon().setAlpha(200);
          }
        }
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.add_bill_menu, menu);
    billmenu = menu;
    return true;
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    //noinspection unused
    MenuItem sendEmailItem = menu.findItem(R.id.send_email_button);
    //noinspection unused
    saveItem = menu.findItem(R.id.save_button);
    return super.onPrepareOptionsMenu(menu);
  }

  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    //sending data to add participant activity if the users goes back and forth between adding bills and participants
    if (id == R.id.add_participants_button) {
      Intent intentForAddingParticipant = new Intent(AddBillActivity.this, AddParticipantActivity.class);
      intentForAddingParticipant.putExtra("fnameList", fnameList);
      intentForAddingParticipant.putExtra("lnameList", lnameList);
      intentForAddingParticipant.putExtra("emailList", emailList);
      startActivityForResult(intentForAddingParticipant, 1);
    }

    // on save button click in options item menu
    if (id == R.id.save_button) {

      if((Objects.requireNonNull(billName.getText()).toString().length() == 0) || (fnameList.isEmpty()) || (itemList.isEmpty()))
      {
        // validating whether bill name not null
        if (Objects.requireNonNull(billName.getText()).toString().length() == 0) {
          billName.setError("Value should not be empty");
        }

        if (fnameList.isEmpty()) {
          Toast.makeText(this, "Please Add Participants to save", Toast.LENGTH_SHORT).show();

        }

        if (itemList.isEmpty()) {
          Toast.makeText(this, "Please Add Items to save", Toast.LENGTH_SHORT).show();

        }
      }
      else {

        // fetching participants list from firebase db
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        ArrayList<Participant> participantObjList = new ArrayList<>();

        // adding participants to local arrayList
        for (int i = 0; i < fnameList.size(); i++) {
          String itemID = mDatabase.push().getKey();

          Participant participant = new Participant(fnameList.get(i), lnameList.get(i), emailList.get(i));
          mDatabase.child("users").child(itemID).setValue(participant);
          participantObjList.add(participant);

        }
        ArrayList<Item> itemObjList = new ArrayList<>();
        Double totalAmount = (double) 0;

        for (int i = 0; i < itemList.size(); i++) {
          String itemID = mDatabase.push().getKey();
          Item itemObj = new Item(itemList.get(i), itemPriceList.get(i).substring(1), itemQtyList.get(i).substring(4));
          itemObjList.add(itemObj);
          totalAmount += Double.parseDouble(itemObj.itemPrice);
          mDatabase.child("items").child(itemID).setValue(itemObj);
        }
        Date dateNow = Calendar.getInstance().getTime();

        String itemID = mDatabase.push().getKey();
        Bill bill = new Bill(billName.getText().toString(), dateNow, totalAmount, itemObjList, participantObjList);
        assert itemID != null;
        mDatabase.child("bills").child(itemID).setValue(bill);

        if (billNameList != null) {
          billNameList.add(billName.getText().toString());
        }
        if (billAmountList != null) {
          billAmountList.add("" + totalAmount);
          owedAmountList.add("" + totalAmount/(participantsCount+1));
        }
        //sending data back to parent activity to display data in home screen
        Intent intent = new Intent();
        intent.putExtra("billName", billName.getText().toString());
        intent.putExtra("billAmount", totalAmount);
        intent.putExtra("billNameList", billNameList);
        intent.putExtra("bilAmountList", billAmountList);
        intent.putExtra("fnameList", fnameList);
        intent.putExtra("owedAmountList", owedAmountList);
        setResult(RESULT_OK, intent);
        finish();
      }
      MenuItem sendEmailItem = billmenu.findItem(R.id.send_email_button);
      /// if bill is saved then allow send email
      if (isBillSaved) {
        sendEmailItem.setEnabled(true);
        sendEmailItem.getIcon().setAlpha(200);
      }
    }
    if(id == R.id.share)
    {
      Intent intentForBlutoothSharing = new Intent(AddBillActivity.this, Bluetooth.class);
      startActivity(intentForBlutoothSharing);
    }
    // email body
    String body = "<html> <header> Hi, you are added into the bill with name  </header> <body> <div> Splinter" +
            "dated November 11 2019 by Team 7 </div> <br>" +
            "<p> Totalbill amount is 100$ <p> <br>" +
            "Amount you have to pay to the user is 20$ <br>";

    if (id == R.id.send_email_button) {
      // email subject
      String subject = "Splitter - New Bill Added";
      //noinspection unused
      String message = "Bill Details";
      String[] mailList = new String[emailList.size()];
      for (int i = 0; i < emailList.size(); i++) {
        mailList[i] = emailList.get(i);
      }
      Intent intent = new Intent(Intent.ACTION_SEND);
      intent.putExtra(Intent.EXTRA_EMAIL, mailList);
      intent.putExtra(Intent.EXTRA_SUBJECT, subject);
      intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(body));
      intent.setType("text/html");
      startActivity(Intent.createChooser(intent, "Choose an Email client :"));
    }
    return super.onOptionsItemSelected(item);
  }
  //getting users data from participants page
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1) {
      if (resultCode == RESULT_OK) {
        MenuItem sendEmailItem = billmenu.findItem(R.id.send_email_button);
        emailList = data.getStringArrayListExtra("emailList");
        //noinspection unused
        participantsCount = data.getIntExtra("participantsCount", 0);
        fnameList = data.getStringArrayListExtra("fnameList");
        lnameList = data.getStringArrayListExtra("lnameList");
        MenuItem saveItem = billmenu.findItem(R.id.save_button);
      }
    }
  }
// Recycler view to display items in Ui
  private void initRecyclerView() {
    RecyclerView recyclerView = findViewById(R.id.recycler_view);
    RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, itemList, itemQtyList, itemPriceList);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

}
