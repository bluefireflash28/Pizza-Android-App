package com.example.cs213_project5;


import java.util.ArrayList;

/**
 * This class keeps track of the order numbers and lists of all instances in the Pizza Class.
 * @author Christopher Zhuo
 */
public class Order {
    private int number;
    private ArrayList<Pizza> pizzaList;
    private ArrayList<String> pizzaListString;
    double total;

    /**
     * order constructor
     * @param number order id
     * @param total cost
     * @param pizzaList list of pizzas
     * @param pizzaListString string representations of pizzas
     */
    public Order(int number, double total, ArrayList<Pizza> pizzaList, ArrayList<String> pizzaListString) {
        this.number = number;
        this.pizzaList = new ArrayList<>();
        this.pizzaListString = new ArrayList<>();

        // Add all elements from the provided lists to the custom lists
        for (int i = 0; i < pizzaList.size(); i++) {
            this.pizzaList.add(pizzaList.get(i));
        }
        for (int i = 0; i < pizzaListString.size(); i++) {
            this.pizzaListString.add(pizzaListString.get(i));
        }
        this.total = total;
    }

    /**
     * getter for  pizzas string list
     * @return pizza string list
     */
    public ArrayList<String> getPizzaListString(){
        return pizzaListString;
    }

    /**
     * number getter
     * @return number
     */
    public int getNumber() {
        return number;
    }


    /**
     * get total including sales tax
     *
     * @return total including sales tax
     */
    public double getTotal() {
        double total = 0;
        for(Pizza p: pizzaList){
            total+=p.price();
        }
        total*=1.06625;
        total = Math.round(total*100.0)/100.0;
        return total;
    }


}