package com.example.javabtvn_8;

public class Drink {
    private String name;
    private int price;
    private int imageResId;

    public Drink(String name, int price, int imageResId) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }
}
