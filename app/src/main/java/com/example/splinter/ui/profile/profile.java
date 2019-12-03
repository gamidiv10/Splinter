package com.example.splinter.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.splinter.LoginActivity;
import com.example.splinter.R;

public class profile extends Fragment {

    public TextView profileShowName, profileShowEmail, profilePhoneNumber, profileName, profileEmail;
    LoginActivity loginActivity = new LoginActivity();
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
        View root = inflater.inflate(R.layout.profile_fragment, container, false);
        profile_edit = root.findViewById(R.id.profile_edit);
        profileEmail = root.findViewById(R.id.tv_profileEmail);
        profileShowEmail = root.findViewById(R.id.tv_profileShowEmail);
        profileShowName = root.findViewById(R.id.tv_profileShowName);
        profileName = root.findViewById(R.id.tv_profileName);
        profilePhoneNumber = root.findViewById(R.id.tv_profilePhoneNumber);

        String name = loginActivity.fbFirstName + " " + loginActivity.fbLastName;
        profileEmail.setText(loginActivity.fbEmail);
        profileShowEmail.setText(loginActivity.fbEmail);
        profileShowName.setText(name);
        profileName.setText(name);
        profilePhoneNumber.setText(loginActivity.fbPhoneNumber);

        return root;
    }
}