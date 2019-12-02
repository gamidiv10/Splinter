package com.example.splinter;

/**
 * Created by Vamsi Gamidi on 2019-12-01.
 */
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
