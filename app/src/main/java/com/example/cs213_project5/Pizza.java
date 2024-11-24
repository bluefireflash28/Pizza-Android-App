package com.example.cs213_project5;

/**
 * This is an abstract class which models a Pizza and it's attributes.
 * @author Christopher Zhuo
 */
public abstract class Pizza {
    private List<Topping> toppings;
    private Crust crust;
    private Size size;
    private String style;

    /**
     * getter for price
     * @return price of pizza
     */
    public abstract double price();

    /**
     * A constructor for a pizza which initializes a list of toppings.
     */
    public Pizza(){
        toppings = new List<>();
    }

    /**
     * A setter for the crust of the pizza.
     * @param crust is the size of the pizza.
     */
    public void setCrust(Crust crust){
        this.crust = crust;
    }

    /**
     * A setter for the size of the pizza.
     * @param size is the size of the pizza.
     */
    public void setSize(Size size){
        this.size = size;
    }

    /**
     * getter for crust
     * @return crust
     */
    public Crust getCrust(){
        return this.crust;
    }

    /**
     * getter for size
     * @return size
     */
    public Size getSize(){
        return size;
    }

    /**
     * getter for toppings
     * @return list of toppings
     */
    public List<Topping> getToppings(){
        return toppings;
    }

    /**
     * adds a topping
     * @param t topping to be added
     */
    public void addTopping(Topping t){
        toppings.add(t);
    }

    /**
     * setter for style
     * @param style of pizza
     */
    public void setStyle(String style){
        this.style = style;
    }

    /**
     * getter for style
     * @return style of pizza
     */
    public String getStyle(){
        return style;
    }

    public abstract String toString();



}