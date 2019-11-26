package com.example.splinter.ui.logout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.splinter.Home;
import com.example.splinter.MainActivity;
import com.example.splinter.R;

public class logout extends Fragment {

    private LogoutViewModel mViewModel;

    public static logout newInstance() {
        return new logout();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.logout_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LogoutViewModel.class);
        // TODO: Use the ViewModel
        goToAttract(mViewModel);

    }

    private void goToAttract(LogoutViewModel v) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }



}
