package com.example.myshop;

public class MobilePackage {
    private String name;
    private int price;
    private String description;

    public MobilePackage() {} // Firestore-hoz kell

    public MobilePackage(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }
    public String getDescription() { return description; }
}

