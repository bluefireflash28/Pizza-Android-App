package com.example.cs213_project5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class keeps track of the order numbers and lists of all instances in the Pizza Class.
 * @author Christopher Zhuo
 */
public class Order {
    private int number;
    private ObservableList<Pizza> pizzaList;
    private ObservableList<String> pizzaListString;
    double total;

    /**
     * order constructor
     * @param number order id
     * @param total cost
     * @param pizzaList list of pizzas
     * @param pizzaListString string representations of pizzas
     */
    public Order(int number, double total,ObservableList<Pizza> pizzaList, ObservableList<String> pizzaListString) {
        this.number = number;
        this.pizzaList = FXCollections.observableArrayList(pizzaList);
        this.pizzaListString = FXCollections.observableArrayList(pizzaListString);
        this.total = total;
    }

    /**
     * getter for  pizzas string list
     * @return pizza string list
     */
    public ObservableList<String> getPizzaListString(){
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