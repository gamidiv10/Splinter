/*
 * Author: Vamsi Gamidi
 * Contributors:
 * Date: 2019
 */

package com.example.splinter;

/**
 * Created by Vamsi Gamidi on 2019-12-01.
 */
public class Participant {
  public String firstName;
  public String lastName;
  public String email;

  public Participant() {
    // Default constructor required for calls to DataSnapshot.getValue(User.class)
  }

  public Participant(String firstname, String lastname, String email) {
    this.firstName = firstname;
    this.lastName = lastname;
    this.email = email;
  }

}
