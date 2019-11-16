package com.example.splinter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecyclerViewAdapterParticipants extends  RecyclerView.Adapter<RecyclerViewAdapterParticipants.ViewHolder>
{
    private ArrayList<String> firstNameList = new ArrayList<>();
    private ArrayList<String> lastNameList = new ArrayList<>();
    private ArrayList<String> emailList = new ArrayList<>();
    private Context rvContext;

    public RecyclerViewAdapterParticipants(Context context, ArrayList<String> fnameList, ArrayList<String> lnameList, ArrayList<String> emailIdList)
    {
        firstNameList = fnameList;
        lastNameList = lnameList;
        emailList = emailIdList;
        rvContext = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterParticipants.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_participants_recyclerview, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterParticipants.ViewHolder holder, int position)
    {
        holder.itemName.setText(firstNameList.get(position));
        holder.qty.setText(lastNameList.get(position));
        holder.price.setText(emailList.get(position));

    }

    @Override
    public int getItemCount() {
        return firstNameList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView itemName, qty, price;
        RelativeLayout parent_layout;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            itemName = itemView.findViewById(R.id.tv_item_name);
            qty = itemView.findViewById(R.id.tv_qty);
            price = itemView.findViewById(R.id.tv_tPrice);
            parent_layout = itemView.findViewById(R.id.recycler_view_participants);
        }
    }
}
