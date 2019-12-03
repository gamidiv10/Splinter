/*
 * Author: Prem Kumar Menni
 * Contributors:
 * Date: 2019
 */

package com.example.splinter.MainMenu.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.example.splinter.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class profile extends Fragment {

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
  // Invoking profile fragment to display
    return inflater.inflate(R.layout.profile_fragment, container, false);

  }

}
