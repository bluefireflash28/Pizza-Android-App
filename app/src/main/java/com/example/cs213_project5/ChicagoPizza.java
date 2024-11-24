package com.example.cs213_project5;

/**
 * this class creates instances of pizza
 */
public class ChicagoPizza implements PizzaFactory{
    @Override
    public Pizza createDeluxe(String sizeString) {
        Pizza pizza = new Deluxe();
        switch(sizeString){
            case "small":
                pizza.setSize(Size.SMALL);
                break;
            case "medium":
                pizza.setSize(Size.MEDIUM);
                break;
            case "large":
                pizza.setSize(Size.LARGE);
                break;
        }
        pizza.setCrust(Crust.DEEPDISH);
        return pizza;
    }

    @Override
    public Pizza createMeatzza(String sizeString) {
        Pizza pizza = new Meatzza();
        switch(sizeString){
            case "small":
                pizza.setSize(Size.SMALL);
                break;
            case "medium":
                pizza.setSize(Size.MEDIUM);
                break;
            case "large":
                pizza.setSize(Size.LARGE);
                break;
        }
        pizza.setCrust(Crust.STUFFED);
        return pizza;
    }

    @Override
    public Pizza createBBQChicken(String sizeString) {
        Pizza pizza = new BBQChicken();
        switch(sizeString){
            case "small":
                pizza.setSize(Size.SMALL);
                break;
            case "medium":
                pizza.setSize(Size.MEDIUM);
                break;
            case "large":
                pizza.setSize(Size.LARGE);
                break;
        }
        pizza.setCrust(Crust.PAN);
        return pizza;
    }

    @Override
    public Pizza createBuildYourOwn(String sizeString) {
        Pizza pizza = new BuildYourOwn();
        switch(sizeString){
            case "small":
                pizza.setSize(Size.SMALL);
                break;
            case "medium":
                pizza.setSize(Size.MEDIUM);
                break;
            case "large":
                pizza.setSize(Size.LARGE);
                break;
        }
        pizza.setCrust(Crust.PAN);
        return pizza;
    }

}