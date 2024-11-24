package com.example.cs213_project5;

/**
 * This class models a Deluxe Pizza by extending all the attributes in the Pizza class.
 * @author Christopher Zhuo
 */
public class Deluxe extends Pizza {
    /**
     * Default constructor
     */
    public Deluxe(){
        getToppings().add(Topping.SAUSAGE);
        getToppings().add(Topping.PEPPERONI);
        getToppings().add(Topping.GREEN_PEPPER);
        getToppings().add(Topping.ONION);
        getToppings().add(Topping.MUSHROOM);
    }

    @Override
    public String toString() {
        if(getStyle().equals("New York")){
            String s = "Deluxe (New York Style - Brooklyn), sausage, pepperoni, green pepper, onion, mushroom, "+getSize()+" $"+price();
            return s;
        }
        String s = "Deluxe (Chicago Style - Deep Dish), sausage, pepperoni, green pepper, onion, mushroom, "+getSize()+" $"+price();
        return s;
    }

    /**
     Constructor for the Deluxe Pizza class.
     * @param size is the size of the pizza.
     */
    public Deluxe(Size size){
        this.setSize(size);
        getToppings().add(Topping.SAUSAGE);
        getToppings().add(Topping.PEPPERONI);
        getToppings().add(Topping.GREEN_PEPPER);
        getToppings().add(Topping.ONION);
        getToppings().add(Topping.MUSHROOM);
    }


    /**
     * Returns the price of the pizza based on the size of it.
     */
    @Override
    public double price(){
        switch(getSize()){
            case SMALL:
                return 16.99;
            case MEDIUM:
                return 18.99;
            case LARGE:
                return 20.99;
        }
        return 0;
    }
}