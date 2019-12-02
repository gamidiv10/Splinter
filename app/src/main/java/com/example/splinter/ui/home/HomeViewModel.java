/*
 * Author: Vamsi Gamidi
 * Contributors:
 * Date: 2019
 */

package com.example.splinter.ui.home;

import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

  public Button profile_edit;
  private MutableLiveData<String> mText;

  public HomeViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is home fragment");


  }

  public LiveData<String> getText() {
    return mText;
  }
}