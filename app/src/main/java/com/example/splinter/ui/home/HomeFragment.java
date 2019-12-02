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

import com.example.splinter.AddBillActivity;
import com.example.splinter.Help;
import com.example.splinter.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public String billName;
    public double totalAmount;
    TextView viewOwesYou;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

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
                startActivityForResult(intent, 1);
            }
        });
        return root;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {

                billName = data.getStringExtra("billName");
                totalAmount = data.getDoubleExtra("totalAmount", 0);
                viewOwesYou.setText("" + totalAmount);


            }
        }

    }
}