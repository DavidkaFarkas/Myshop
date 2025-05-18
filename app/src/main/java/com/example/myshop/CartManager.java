package com.example.myshop;

import com.example.myshop.CartItem;
import com.example.myshop.MobilePackage;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<CartItem> cartItems = new ArrayList<>();

    private CartManager() {}

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(MobilePackage mobilePackage) {
        for (CartItem item : cartItems) {
            if (item.getMobilePackage().getName().equals(mobilePackage.getName())) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        cartItems.add(new CartItem(mobilePackage, 1));
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void clearCart() {
        cartItems.clear();
    }
}
