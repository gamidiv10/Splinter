/*
 * Author: Vamsi Gamidi
 * Contributors: Prem Kumar Menni, Sneh Jogani
 * Date: 2019
 */

package com.example.splinter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
  private ArrayList<String> itemList = new ArrayList<>();
  private ArrayList<String> itemQty = new ArrayList<>();
  private ArrayList<String> itemPrice = new ArrayList<>();
  public Context rvContext;
  public Dialog dialogEditBill;
  public Button buttonSave, buttonCancel;

  public ViewHolder holder;
  public EditText editTextItemName, editTextQty, editTextPrice;

//RecyclerViewAdapter for Adding Items to the bill
  public RecyclerViewAdapter(Context context, ArrayList<String> itemNameList, ArrayList<String> qty, ArrayList<String> totalPrice) {
    itemList = itemNameList;
    itemQty = qty;
    itemPrice = totalPrice;
    rvContext = context;
  }


  //Initializing the view holder
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    dialogEditBill = new Dialog(rvContext);
    dialogEditBill.setContentView(R.layout.dialog_edit_bill);
    editTextItemName = dialogEditBill.findViewById(R.id.edit_text_item_name);
    editTextQty = dialogEditBill.findViewById(R.id.edit_text_qty);
    editTextPrice = dialogEditBill.findViewById(R.id.edit_text_price);
    buttonSave = dialogEditBill.findViewById(R.id.button_save);
    buttonCancel = dialogEditBill.findViewById(R.id.button_cancel);
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_recyclerview, parent, false);
    holder = new ViewHolder(view);

    return holder;
  }

  // View holder to display items
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.itemName.setText(itemList.get(position));
    holder.qty.setText(itemQty.get(position));
    holder.price.setText(itemPrice.get(position));
  }

  //getting the count of items to iterate recycler view
  @Override
  public int getItemCount() {
    return itemList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    TextView itemName, qty, price;
    RelativeLayout parent_layout;
    ImageButton editButton, deleteButton;


    //creating the view holder
    public ViewHolder(@NonNull final View itemView) {
      super(itemView);
      itemName = itemView.findViewById(R.id.tv_item_name);
      qty = itemView.findViewById(R.id.tv_qty);
      price = itemView.findViewById(R.id.tv_tPrice);
      parent_layout = itemView.findViewById(R.id.parent_layout);


      editButton = itemView.findViewById(R.id.edit_item_button);
      editButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          editTextItemName.setText(itemList.get(getAdapterPosition()));
          editTextQty.setText(itemQty.get(getAdapterPosition()).substring(4));

          double tPrice = Double.parseDouble(itemPrice.get(getAdapterPosition()).substring(1));
          final double qty = Double.parseDouble(editTextQty.getText().toString());
          double unitPrice = tPrice / qty;
          editTextPrice.setText("$" + unitPrice);

          dialogEditBill.show();

          // Handling save button
          buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              itemList.set(getAdapterPosition(), editTextItemName.getText().toString());
              itemQty.set(getAdapterPosition(), "Qty:" + editTextQty.getText());
              double updatedUnitPrice = Double.parseDouble(editTextPrice.getText().toString().substring(1));
              double updatedQuantity = Double.parseDouble(editTextQty.getText().toString());
              itemPrice.set(getAdapterPosition(), "$" + updatedUnitPrice * updatedQuantity);
              notifyItemChanged(getAdapterPosition());
              dialogEditBill.cancel();
            }
          });
          buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              dialogEditBill.cancel();
            }
          });
        }
      });
      // Deleting items in view
      deleteButton = itemView.findViewById(R.id.delete_item_button);
      deleteButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

          int position = getAdapterPosition();
          if (position != RecyclerView.NO_POSITION) {
            itemList.remove(position);
            itemQty.remove(position);
            itemPrice.remove(position);
            notifyItemRemoved(position);
          }

        }
      });
    }
  }
}
