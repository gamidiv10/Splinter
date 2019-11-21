package com.example.splinter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{
    private ArrayList<String> itemList = new ArrayList<>();
    private ArrayList<String> itemQty = new ArrayList<>();
    private ArrayList<String> itemPrice = new ArrayList<>();
    private Context rvContext;

    public RecyclerViewAdapter(Context context, ArrayList<String> itemNameList, ArrayList<String> qty, ArrayList<String> totalPrice)
    {
        itemList = itemNameList;
        itemQty = qty;
        itemPrice = totalPrice;
        rvContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_recyclerview, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.itemName.setText(itemList.get(position));
        holder.qty.setText(itemQty.get(position));
        holder.price.setText(itemPrice.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView itemName, qty, price;
        RelativeLayout parent_layout;
        ImageButton editButton;
        Intent intent;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            itemName = itemView.findViewById(R.id.tv_item_name);
            qty = itemView.findViewById(R.id.tv_qty);
            price = itemView.findViewById(R.id.tv_tPrice);
            parent_layout = itemView.findViewById(R.id.parent_layout);

            editButton = itemView.findViewById(R.id.edit_item_button);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    intent = new Intent(RecyclerViewAdapter.this(), RecyclerViewAdapter.class);

                }
            });

        }
    }
}
