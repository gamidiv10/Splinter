/*
 * Author: Jasper Jiao
 * Contributors:
 * Date: 2019
 */

package com.example.splinter.MainMenu.currency;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.splinter.Locations;
import com.example.splinter.R;
import com.example.splinter.MainMenu.home.HomeViewModel;

public class currency extends Fragment {

  private HomeViewModel homeViewModel;
// Class constructor
  public static currency newInstance() {
    return new currency();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {

    // Creating a root view to display currency_fragment layout
    View root = inflater.inflate(R.layout.currency_fragment, container, false);
    startActivity(new Intent(getContext(), Locations.class));


    return root;
  }

}
