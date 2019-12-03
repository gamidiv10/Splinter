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
import com.example.splinter.SignupActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class profile extends Fragment {

    public TextView profileShowName, profileShowEmail, profilePhoneNumber, profileName, profileEmail;
    public FirebaseAuth mLoginAuthentication;

    String name, fbEmail, fbFirstName, fbLastName, fbPhoneNumber;
    FirebaseDatabase databaseRef = FirebaseDatabase.getInstance("https://splinter-f86ee.firebaseio.com/");
    private ProfileViewModel mViewModel;
    private Button profile_edit;
    private ProfileViewModel profileViewModel;

    public static profile newInstance() {
        return new profile();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mLoginAuthentication = FirebaseAuth.getInstance();

        View root = inflater.inflate(R.layout.profile_fragment, container, false);
        profile_edit = root.findViewById(R.id.profile_edit);
        profileEmail = root.findViewById(R.id.tv_profileEmail);
        profileShowEmail = root.findViewById(R.id.tv_profileShowEmail);
        profileShowName = root.findViewById(R.id.tv_profileShowName);
        profileName = root.findViewById(R.id.tv_profileName);
        profilePhoneNumber = root.findViewById(R.id.tv_profilePhoneNumber);

        FirebaseUser currentUser = mLoginAuthentication.getCurrentUser();

        final String user_email = mLoginAuthentication.getCurrentUser().getEmail();
        if (currentUser != null) {

            DatabaseReference referenceToRoot = databaseRef.getReference().child("Users");
            referenceToRoot.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    HashMap<String, Object> UserData = (HashMap<String, Object>) dataSnapshot.getValue();
                    System.out.println(UserData);
                    for (Map.Entry<String, Object> users : UserData.entrySet()) {
                        Object rootID = users.getValue();
                        try {
                            JSONObject rootObject = new JSONObject(rootID.toString());
                            JSONObject email = rootObject.getJSONObject("Email");
                            fbEmail = email.getString("Email_value");
                            if (fbEmail.equals(user_email)) {
                                fbFirstName = email.getString("First_name");
                                fbLastName = email.getString("Last_name");
                                fbPhoneNumber = email.getString("Phone_Number");
                                break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            profileShowEmail.setText(fbEmail);
            profileEmail.setText(fbEmail);
            name = fbFirstName + " " + fbLastName;
            profileShowName.setText(name);
            profileName.setText(name);
            profilePhoneNumber.setText(fbPhoneNumber);
        }
        return root;

    }
}