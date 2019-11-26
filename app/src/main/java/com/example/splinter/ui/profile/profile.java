package com.example.splinter.ui.profile;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.splinter.R;
import com.example.splinter.ui.home.HomeViewModel;

public class profile extends Fragment {

    private ProfileViewModel mViewModel;
    private Button profile_edit;
    private ProfileViewModel profileViewModel;

    public static profile newInstance() {
        return new profile();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        profileViewModel =
//                ViewModelProviders.of(this).get(profileViewModel.getClass());
//        View root = inflater.inflate(R.layout.profile_fragment, container, false);
//        profile_edit = root.findViewById(R.id.profile_edit);

        return inflater.inflate(R.layout.profile_fragment, container, false);

    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
//        // TODO: Use the ViewModel
//
//    }

}
