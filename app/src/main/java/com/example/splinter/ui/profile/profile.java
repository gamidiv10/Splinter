package com.example.splinter.ui.profile;

import androidx.lifecycle.ViewModelProviders;

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
import com.example.splinter.ui.home.HomeViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.sql.Array;
import java.util.ArrayList;

public class profile extends Fragment {

    private DatabaseReference mdatabase;
    private ProfileViewModel mViewModel;
    private Button profile_edit;
    private ProfileViewModel profileViewModel;

    private ArrayAdapter<String> adapter;
    public static profile newInstance() {
        return new profile();
    }

    private ArrayList<String> arrayList = new ArrayList<>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        profileViewModel =
//                ViewModelProviders.of(this).get(profileViewModel.getClass());
//        View root = inflater.inflate(R.layout.profile_fragment, container, false);
//        profile_edit = root.findViewById(R.id.profile_edit);
////
//        mdatabase.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                String string = dataSnapshot.getValue(String.class);
//                arrayList.add(string);
//                System.out.println(arrayList);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

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
