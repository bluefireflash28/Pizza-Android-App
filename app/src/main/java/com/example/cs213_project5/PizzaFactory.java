package com.example.cs213_project5;

/**
 * this class is an interface for creating pizzas
 */
public interface PizzaFactory {
    /**
     * creates deluxe pizza
     * @param size of pizza
     * @return deluxe pizza
     */
    Pizza createDeluxe(String size);

    /**
     * creates meatzza pizza
     * @param size of pizza
     * @return meatzza pizza
     */
    Pizza createMeatzza(String size);

    /**
     * creates bbqchicken pizza
     * @param size of pizza
     * @return bbqchicken pizza
     */
    Pizza createBBQChicken(String size);

    /**
     * creates byo pizza
     * @param size of pizza
     * @return byo pizza
     */
    Pizza createBuildYourOwn(String size);
}