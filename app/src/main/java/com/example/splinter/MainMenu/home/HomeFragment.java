/*
 * Author: Vamsi Gamidi
 * Contributors: Prem kumar Menni, Sneh jogani
 * Date: November 10 2019
 */

package com.example.splinter.MainMenu.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.splinter.AddBillActivity;
import com.example.splinter.R;
import com.example.splinter.RecyclerViewAdapterHomeScreenBills;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public String billName;
    public double totalAmount = 0, owedAmount;
    TextView viewOwesYou;
    public Double billAmount;
    View root;
    private ArrayList<String> billNameList = new ArrayList<>();
    private ArrayList<String> billAmountList = new ArrayList<>();
    private ArrayList<String> owedAmountList = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

    viewOwesYou = root.findViewById(R.id.owes_you);

        FloatingActionButton fab = root.findViewById(R.id.addBill_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AddBillActivity.class);
                intent.putExtra("billName", billNameList);
                intent.putExtra("billAmount", billAmountList);
                intent.putExtra("owedAmount", owedAmountList);
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
                owedAmountList = data.getStringArrayListExtra("owedAmountList");

                totalAmount = 0;
                owedAmount = 0;

                for (int i = 0; i < billAmountList.size(); i++) {
                    totalAmount += Double.parseDouble(billAmountList.get(i));
                    owedAmount += Double.parseDouble(owedAmountList.get(i));
                }

                viewOwesYou.setText("" + (totalAmount-owedAmount));
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