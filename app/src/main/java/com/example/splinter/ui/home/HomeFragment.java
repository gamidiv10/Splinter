/*
 * Author: Vamsi Gamidi
 * Contributors:
 * Date: 2019
 */

package com.example.splinter.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splinter.AddBillActivity;
import com.example.splinter.Help;
import com.example.splinter.R;
import com.example.splinter.RecyclerViewAdapter;
import com.example.splinter.RecyclerViewAdapterHomeScreenBills;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public String billName;
    public double totalAmount = 0;
    TextView viewOwesYou;
    public Double billAmount;
    View root;
    private ArrayList<String> billNameList = new ArrayList<>();
    private ArrayList<String> billAmountList = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

    viewOwesYou = root.findViewById(R.id.owes_you);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        help = root.findViewById(R.id.action_help);
//        ((View) help).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), Help.class);
//                startActivity(intent);
//            }
//        });
    // View share = root.findViewById(R.id.action_share);

//        share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), share.class);
//                startActivity(intent);
//            }
//        });
        FloatingActionButton fab = root.findViewById(R.id.addBill_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AddBillActivity.class);
                intent.putExtra("billName", billNameList);
                intent.putExtra("billAmount", billAmountList);
                startActivityForResult(intent, 1);

            }
        });
        return root;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                billName = data.getStringExtra("billName");
                //totalAmount = data.getDoubleExtra("billAmount", 0);
                billNameList = data.getStringArrayListExtra("billNameList");
                billAmountList = data.getStringArrayListExtra("bilAmountList");

                totalAmount = 0;

                for (int i = 0; i < billAmountList.size(); i++) {
                    totalAmount += Double.parseDouble(billAmountList.get(i));
                }
                //billAmountList.add("" + billAmount);
                //billNameList.add(billName);


                viewOwesYou.setText("" + totalAmount);
                billAmount = totalAmount;

                initRecyclerView(root);

            }
        }
    }
    private void initRecyclerView(View root)
    {
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view_bills_homepage);
        RecyclerViewAdapterHomeScreenBills adapter = new RecyclerViewAdapterHomeScreenBills(getContext(), billNameList, billAmountList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}