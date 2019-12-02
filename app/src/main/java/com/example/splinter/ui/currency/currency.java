package com.example.splinter.ui.currency;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.splinter.R;
import com.example.splinter.ui.home.HomeViewModel;

public class currency extends Fragment implements AdapterView.OnItemClickListener {

    private ListView lvHomePage;
    private String[] items;
    private View view;
    private CurrencyViewModel mViewModel;

    private HomeViewModel homeViewModel;

    ListView lv;
    private Spinner currencySpinner;
    public static currency newInstance() {
        return new currency();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


//        View view = inflater.inflate(R.layout.currency_fragment, container, false);
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.currency_fragment, container, false);
        //lv = root.findViewById(R.id.select_period);
//        lv.setOnItemClickListener(this);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.currency,
                android.R.layout.activity_list_item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        String currency = (String)parent.getItemAtPosition(position);
        if (currency.equals("Euro")){
            Toast.makeText(parent.getContext(),"Currency has been changes to Euro", Toast.LENGTH_LONG).show();
        }

    }
}
