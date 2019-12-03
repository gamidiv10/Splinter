/*
 * Author: Yashesh Savani
 * Contributors: Sneh Jogani
 * Date: 2019
 */

package com.example.splinter.MainMenu.logout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.splinter.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class logout extends Fragment {

  // Firebase authentication
  private FirebaseAuth logoutFireBaseAuthentication;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    logoutFireBaseAuthentication = FirebaseAuth.getInstance();
    // calling signout function for log out
    signOut();
  }

  private void signOut() {
    // firebase authentication logout
    logoutFireBaseAuthentication.signOut();
    Toast.makeText(getContext(), "Logged Out", Toast.LENGTH_SHORT).show();
    Intent intent = new Intent(getContext(), LoginActivity.class);
    // Removal of Back stacking activities
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }


}
