package com.example.cs213_project5;

/**
 * This class models a BuildYourOwn Pizza by extending all the attributes in the Pizza class.
 * @author Christopher Zhuo
 */
public class BuildYourOwn extends Pizza{
    /**
       Constructor for the build your own pizza class.
     */
    public BuildYourOwn(){
    }

    /**
     Method to add topping to the buildyourownpizza.
     * @param t is the topping to be added.
     */
    public void addTopping(Topping t){
        getToppings().add(t);
    }

    @Override
    public String toString() {
        if (getStyle().equals("New York")) {
            String s = "Build your own (New York Style - Hand-tossed), "+toppingsToString() + getSize() + " $" + price();
            return s;
        }
        String s = "Build your own (Chicago Style - Pan), "+toppingsToString() + getSize() + " $" + price();
        return s;
    }

    /**
     * Returns the price of the pizza based on the size of it.
     */
    @Override
    public double price(){
        switch(getSize()){
            case SMALL:
                return Math.round((8.99 + (getToppings().size() * 1.69))*100.0)/100.0;
            case MEDIUM:
                return Math.round((10.99 + (getToppings().size() * 1.69))*100.0)/100.0;
            case LARGE:
                return Math.round((12.99 + (getToppings().size() * 1.69))*100.0)/100.0;
        }
        return 0;
    }

    /**
     * string representation of toppings
     * @return string of toppings
     */
    public String toppingsToString(){
        StringBuilder sb = new StringBuilder();
        List<Topping> items = getToppings();
        for(int i = 0; i<items.size();i++){
            sb.append(items.get(i));
            sb.append(", ");
        }
        return sb.toString();
    }
}