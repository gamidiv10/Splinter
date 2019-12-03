package com.example.splinter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Vamsi Gamidi on 2019-12-02.
 */
public class RecyclerViewAdapterHomeScreenBills extends  RecyclerView.Adapter<RecyclerViewAdapterHomeScreenBills.ViewHolder> {

    private Context rvContext;
    public RecyclerViewAdapterHomeScreenBills.ViewHolder holder;
    String billName;
    Double billAmount;
    ArrayList<String> billNameList = new ArrayList<>();
    ArrayList<String> billAmountList = new ArrayList<>();


    //constructor to initialize adapter
    public RecyclerViewAdapterHomeScreenBills(Context context, ArrayList<String> nameList, ArrayList<String> amountList)
    {
        billNameList = nameList;
        billAmountList = amountList;
        rvContext = context;
            }


    //initialzing the view holder
    @NonNull
    @Override
    public RecyclerViewAdapterHomeScreenBills.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_bills_recyclerview, parent, false);
        holder = new RecyclerViewAdapterHomeScreenBills.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterHomeScreenBills.ViewHolder holder, int position)
    {
        holder.billName.setText(billNameList.get(position));
        holder.billAmount.setText("" + billAmountList.get(position));

    }

    @Override
    public int getItemCount() {
        return billNameList.size();
    }


    //creating the view holder
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        TextView billName, billAmount;
        RelativeLayout parent_layout;
        ImageButton deleteButton;
        AppCompatCheckBox checkBox;


        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            parent_layout = itemView.findViewById(R.id.recycler_view_bills_homepage);
            billName = itemView.findViewById(R.id.tv_bill_name);
            billAmount = itemView.findViewById(R.id.tv_bill_amount);

            deleteButton = itemView.findViewById(R.id.delete_bill);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        billNameList.remove(position);
                        billAmountList.remove(position);


                        notifyItemRemoved(position);
                    }
                }
            });
        }
    }
    }
