/*
 * Author: Vamsi Gamidi
 * Contributors:
 * Date: 2019
 */

package com.example.splinter;
// Class for setting item details
public class Item {
  public String itemName;
  public String itemPrice;
  public String itemQty;

  public Item() {
    // Default constructor required for calls to DataSnapshot.getValue(User.class)
  }

  public Item(String name, String price, String qty) {
    this.itemName = name;
    this.itemPrice = price;
    this.itemQty = qty;
  }
}
