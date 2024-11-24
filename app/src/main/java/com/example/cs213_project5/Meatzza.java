package com.example.cs213_project5;

/**
 * This class models a Meatzza Pizza by extending all the attributes in the Pizza class.
 * @author Christopher Zhuo
 */
public class Meatzza extends Pizza{

    /**
     Constructor for the Meatzza class.
     */
    public Meatzza(){
        getToppings().add(Topping.SAUSAGE);
        getToppings().add(Topping.PEPPERONI);
        getToppings().add(Topping.BEEF);
        getToppings().add(Topping.HAM);
    }

    @Override
    public String toString() {
        if (getStyle().equals("New York")) {
            String s = "Meatzza (New York Style - Hand-tossed), sausage, pepperoni, beef, ham, " + getSize() + " $" + price();
            return s;
        }
        String s = "BBQ Chicken (Chicago Style - Stuffed), sausage, pepperoni, beef, ham, " + getSize() + " $" + price();
        return s;
    }
    /**
     * Returns the price of the pizza based on the size of it.
     */
    @Override
    public double price(){
        switch(getSize()){
            case SMALL:
                return 17.99;
            case MEDIUM:
                return 19.99;
            case LARGE:
                return 21.99;
        }
        return 0;
    }
}