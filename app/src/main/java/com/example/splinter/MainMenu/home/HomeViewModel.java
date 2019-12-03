/*
 * Author: Vamsi Gamidi
 * Contributors:
 * Date: November 10 2019
 */

package com.example.splinter.MainMenu.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

  private MutableLiveData<String> mText;

  public HomeViewModel() {

  }

  public LiveData<String> getText() {
    return mText;
  }
}