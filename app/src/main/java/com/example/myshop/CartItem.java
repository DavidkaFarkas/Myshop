package com.example.myshop;

import com.example.myshop.MobilePackage;

public class CartItem {
    private MobilePackage mobilePackage;
    private int quantity;

    public CartItem(MobilePackage mobilePackage, int quantity) {
        this.mobilePackage = mobilePackage;
        this.quantity = quantity;
    }

    public MobilePackage getMobilePackage() { return mobilePackage; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
