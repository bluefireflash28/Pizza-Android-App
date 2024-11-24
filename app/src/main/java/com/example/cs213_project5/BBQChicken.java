package com.example.cs213_project5;

/**
 * This class models a BBQChicken Pizza by extending all the attributes in the Pizza class.
 * @author Christopher Zhuo
 */
public class BBQChicken extends Pizza{

    /**
     Constructor for the BBQChicken class.
     */
    public BBQChicken(){
        getToppings().add(Topping.BBQ_CHICKEN);
        getToppings().add(Topping.GREEN_PEPPER);
        getToppings().add(Topping.GREEN_PEPPER);
        getToppings().add(Topping.PROVOLONE);
        getToppings().add(Topping.CHEDDAR);
    }

    @Override
    public String toString() {
        if(getStyle().equals("New York")){
            String s = "BBQ Chicken (New York Style - Thin), BBQ Chicken, green pepper, provolone, cheddar "+getSize()+" $"+price();
            return s;
        }
        String s = "BBQ Chicken (Chicago Style - Pan), BBQ Chicken, green pepper, provolone, cheddar "+getSize()+" $"+price();
        return s;
    }

    /**
     * Returns the price of the pizza based on the size of it.
     */
    @Override
    public double price(){
        switch(getSize()){
            case SMALL:
                return 14.99;
            case MEDIUM:
                return 16.99;
            case LARGE:
                return 19.99;
        }
        return 0;
    }
}