/*
 * Author: Vamsi Gamidi
 * Contributors: Prem Kumar Menni, Sneh Jogani
 * Date: 2019
 */

package com.example.splinter;

import java.util.ArrayList;
import java.util.Date;

//Model for Bill
public class Bill {

  public String billName;
  public Date date;
  public ArrayList<Item> items;
  public ArrayList<Participant> participants;
  public Double totalAmount;

  public Bill() {
    // Default constructor required for calls to DataSnapshot.getValue(User.class)
  }

  // Initialising the values
  public Bill(String name, Date date, Double totalAmount, ArrayList<Item> itemList, ArrayList<Participant> participantList) {
    this.billName = name;
    this.date = date;
    this.items = itemList;
    this.totalAmount = totalAmount;
    this.participants = participantList;
  }
}
