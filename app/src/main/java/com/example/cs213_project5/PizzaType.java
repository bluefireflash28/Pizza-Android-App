package com.example.cs213_project5;

public class PizzaType {
    private String name;
    private int imageResId;

    // Constructor
    public PizzaType(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }
}
