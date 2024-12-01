package com.example.cs213_project5;

import java.util.ArrayList;

public class StateManager {
    private static final StateManager instance = new StateManager();

    /**
     * All current orders
     */
    private final ArrayList<Pizza> currentOrders = new ArrayList<>();  // Using ArrayList

    /**
     * All current orders string representations
     */
    private final ArrayList<String> currentOrdersStrings = new ArrayList<>();  // Using ArrayList

    /**
     * List of all orders
     */
    private final ArrayList<Order> allOrders = new ArrayList<>();  // Using ArrayList

    /**
     * Subtotal
     */
    public double subtotal = 0;

    /**
     * Sales tax
     */
    public double salesTax = 0;

    /**
     * Total order cost
     */
    public double orderTotal = 0;

    /**
     * Number of the current order
     */
    public int orderNumber = 1;

    /**
     * List of numbers for the combo box
     */
    public final ArrayList<String> numberList = new ArrayList<>();  // Using ArrayList

    /**
     * Private constructor
     */
    private StateManager() {
    }

    /**
     * Getter for instance
     *
     * @return instance of state manager
     */
    public static StateManager getInstance() {
        return instance;
    }

    /**
     * Getter for current orders
     *
     * @return list of pizzas
     */
    public ArrayList<Pizza> getCurrentOrders() {
        return currentOrders;
    }

    /**
     * Getter for list of order strings
     *
     * @return list of order strings
     */
    public ArrayList<String> getCurrentOrdersStrings() {
        return currentOrdersStrings;
    }

    /**
     * Getter for all orders
     *
     * @return list of orders
     */
    public ArrayList<Order> getAllOrders() {
        return allOrders;
    }
}