/*
 * Author: Prem Kumar Menni
 * Contributors:
 * Date: 2019
 */

package com.example.splinter.MainMenu.contact_us;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.splinter.Help;
import com.example.splinter.R;

public class contact_us extends Fragment {

  // Initialising contactusviewmodel class
  private ContactUsViewModel mViewModel;

  Button help;

  // Creating constructor instance
  public static contact_us newInstance() {
    return new contact_us();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.contact_us_fragment, container, false);
    help = root.findViewById(R.id.btn_help);
    // Calling help layout
    help.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //startActivity(new Intent(this, Help.class));
        startActivity(new Intent(getActivity(), Help.class));
      }
    });
    return root;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(ContactUsViewModel.class);

  }

}
