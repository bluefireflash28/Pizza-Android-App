package com.example.cs213_project5;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NewYorkPizzaActivity extends AppCompatActivity {
    Object currentTopping = null;
    private RecyclerView recyclerView;
    private PizzaAdapter pizzaAdapter;
    private List<PizzaType> pizzaList;
    CurrentOrderActivity currentOrderActivity = new CurrentOrderActivity();
    private int lastCheckedId = -1; // Track the previous checked ID
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_york_pizza);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.pizzaRecyclerViewNY);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize pizza list
        pizzaList = new List<>();
        pizzaList.add(new PizzaType("Deluxe", R.drawable.deluxe_pizza));
        pizzaList.add(new PizzaType("BBQ Chicken", R.drawable.bbq_chicken_pizza));
        pizzaList.add(new PizzaType("Meatzza", R.drawable.meatzza_pizza));
        pizzaList.add(new PizzaType("Build Your Own", R.drawable.build_your_own_pizza));

        // Set the adapter for the RecyclerView
        pizzaAdapter = new PizzaAdapter(pizzaList);
        recyclerView.setAdapter(pizzaAdapter);

        RadioGroup radioGroup = findViewById(R.id.pizzaSize);
        // sets the available toppings
        ListView availableToppings = findViewById(R.id.availableToppings);
        ArrayList<Topping> toppings = new ArrayList<>(Arrays.asList(Topping.values()));
        ArrayAdapter<Topping> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1, // Layout for each item
                toppings
        );
        availableToppings.setAdapter(adapter);
        availableToppings.setOnItemClickListener((parent, view, position, id) -> {
            // Get the selected item
            String selectedTopping = parent.getItemAtPosition(position).toString();
            currentTopping = parent.getItemAtPosition(position);
            // Display the selected topping
            Toast.makeText(getApplicationContext(), "Selected: " + selectedTopping, Toast.LENGTH_SHORT).show();
        });

        ListView selectedToppings = findViewById(R.id.selectedToppings);
        selectedToppings.setOnItemClickListener((parent, view, position, id) -> {
            // Get the selected item
            String selectedTopping = parent.getItemAtPosition(position).toString();
            currentTopping = parent.getItemAtPosition(position);
            // Display the selected topping
            Toast.makeText(getApplicationContext(), "Selected: " + selectedTopping, Toast.LENGTH_SHORT).show();
        });

        Spinner PizzaTypes = findViewById(R.id.PizzaTypes);

// Set up the OnItemSelectedListener
        PizzaTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item (if needed)
                String selectedPizzaType = parent.getItemAtPosition(position).toString();
                setCrust(selectedPizzaType);
                addToppings();
                Log.d("Debug", selectedPizzaType);

                // Get the selected RadioButton ID
                int checkedId = radioGroup.getCheckedRadioButtonId();

                // Perform actions based on the checked RadioButton
                if (checkedId == R.id.smallButton) {
                    onSizeSelected(getString(R.string.small));
                } else if (checkedId == R.id.mediumButton) {
                    onSizeSelected(getString(R.string.medium));
                } else if (checkedId == R.id.largeButton) {
                    onSizeSelected(getString(R.string.large));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when no item is selected (optional)
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // Call a method based on the selected RadioButton
                if(checkedId==R.id.smallButton){
                    onSizeSelected(getString(R.string.small));
                } else if(checkedId==R.id.mediumButton){
                    onSizeSelected(getString(R.string.medium));
                } else if(checkedId==R.id.largeButton){
                    onSizeSelected(getString(R.string.large));
                }
            }
        });
    }
    public void placeOrder(View view) {
        ArrayList<Pizza> orders = StateManager.getInstance().getCurrentOrders();
        ArrayList<String> ordersStrings = StateManager.getInstance().getCurrentOrdersStrings();
        if(getPizzaSize() ==null || getPizzaSize() == null){
            Toast.makeText(getApplicationContext(), "Make a pizza", Toast.LENGTH_LONG).show();
            return;
        }
        if (!getPizzaType().equals("Build Your Own")) {
            Pizza a = currentOrderActivity.addNYPizza(getPizzaSize(),getPizzaType());
            showOrderPopup();
            ordersStrings.add(a.toString());
            orders.add(a);
        } else {
            ListView selectedToppings = findViewById(R.id.selectedToppings);
            ArrayAdapter<Topping> adapter = (ArrayAdapter<Topping>) selectedToppings.getAdapter();
            ArrayList<Topping> toppingList = new ArrayList<>();
            for (int i = 0; i < adapter.getCount(); i++) {
                toppingList.add(adapter.getItem(i));
            }
            Pizza b = currentOrderActivity.addNYPizzaBYO(getPizzaSize(), toppingList);
            showOrderPopup();
            ordersStrings.add(b.toString());
            orders.add(b);
        }
    }
    public void showOrderPopup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title")
                .setMessage("Order Placed")
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Action for "Cancel" button
                        dialog.dismiss(); // Dismiss the dialog
                    }
                });

// Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public String getPizzaType(){
        Spinner spinner = findViewById(R.id.PizzaTypes);
        String type = spinner.getSelectedItem().toString();
        return type;
    }
    public String getPizzaSize(){
        RadioGroup radioGroup = findViewById(R.id.pizzaSize);
        int checkedId = radioGroup.getCheckedRadioButtonId();
        if(checkedId==R.id.smallButton){
            return "small";
        } else if(checkedId==R.id.mediumButton){
            return "medium";
        } else if(checkedId==R.id.largeButton){
            return "large";
        }
        Log.d("TAG", "NULL SIZE RETURNED ");
        return null;
    }


    public void onSizeSelected(String size) {
        Spinner spinner = findViewById(R.id.PizzaTypes);
        String type = spinner.getSelectedItem().toString();
        Log.d("TAG", type);

        ListView selectedToppingsListView = findViewById(R.id.selectedToppings);
        int numToppings = selectedToppingsListView.getAdapter() != null ? selectedToppingsListView.getAdapter().getCount() : 0;
        double toppingCost = 1.69 * numToppings; // Each topping costs 1.69

        switch (type) {
            case "Deluxe":
                updatePrice(size, 16.99, 18.99, 20.99);
                break;
            case "BBQ Chicken":
                updatePrice(size, 14.99, 16.99, 19.99);
                break;
            case "Meatzza":
                updatePrice(size, 17.99, 19.99, 21.99);
                break;
            case "Build Your Own":
                double basePriceSmall = 8.99;
                double basePriceMedium = 10.99;
                double basePriceLarge = 12.99;

                // Add the topping cost to the base price depending on the size
                switch (size) {
                    case "Small":
                        updatePrice(size, basePriceSmall + toppingCost, basePriceSmall + toppingCost, basePriceSmall + toppingCost);
                        break;
                    case "Medium":
                        updatePrice(size, basePriceMedium + toppingCost, basePriceMedium + toppingCost, basePriceMedium + toppingCost);
                        break;
                    case "Large":
                        updatePrice(size, basePriceLarge + toppingCost, basePriceLarge + toppingCost, basePriceLarge + toppingCost);
                        break;
                }
                break;
        }
    }

    public void updatePrice(String size, double small, double medium, double large){
        TextView priceText = findViewById(R.id.priceText);
        switch(size){
            case "Small":
                priceText.setText(getString(R.string.pizzaPrice,small));
                break;
            case "Medium":
                priceText.setText(getString(R.string.pizzaPrice,medium));
                break;
            case "Large":
                priceText.setText(getString(R.string.pizzaPrice,large));
                break;
        }
    }
    public void setCrust(String type){
        ListView availableToppings = findViewById(R.id.availableToppings);
        TextView crust = findViewById(R.id.crustText);
        Button addTopping = (Button) findViewById(R.id.addTopping);
        Button removeTopping = (Button) findViewById(R.id.removeTopping);
        switch(type){
            case "Deluxe":
                crust.setText(getString(R.string.brooklyn));
                availableToppings.setEnabled(false);
                addTopping.setEnabled(false);
                removeTopping.setEnabled(false);
                break;
            case "BBQ Chicken":
                crust.setText(getString(R.string.thin));
                availableToppings.setEnabled(false);
                addTopping.setEnabled(false);
                removeTopping.setEnabled(false);
                break;
            case "Meatzza":
                crust.setText(getString(R.string.handtossed));
                availableToppings.setEnabled(false);
                addTopping.setEnabled(false);
                removeTopping.setEnabled(false);
                break;
            case "Build Your Own":
                crust.setText(R.string.handtossed);
                availableToppings.setEnabled(true);
                addTopping.setEnabled(true);
                removeTopping.setEnabled(true);
                break;
            default:
                availableToppings.setEnabled(false);
                addTopping.setEnabled(false);
                removeTopping.setEnabled(false);
                break;
        }
    }

    /**
     * Method adds topping to the pizza depending on which type of pizza it is.
     */
    public void addToppings(){
        Spinner PizzaTypes = findViewById(R.id.PizzaTypes);
        ListView selectedToppings = findViewById(R.id.selectedToppings);
        ArrayList<Topping> toppings;
        ArrayAdapter<Topping> adapter;
        switch(PizzaTypes.getSelectedItem().toString()){
            case "Deluxe":
                toppings = new ArrayList<>(Arrays.asList(Topping.SAUSAGE,Topping.PEPPERONI,Topping.GREEN_PEPPER,Topping.ONION,Topping.MUSHROOM));
                adapter = new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1, // Layout for each item
                        toppings
                );
                selectedToppings.setAdapter(adapter);
                break;
            case "BBQ Chicken":
                toppings = new ArrayList<>(Arrays.asList(Topping.BBQ_CHICKEN,Topping.GREEN_PEPPER,Topping.PROVOLONE,Topping.CHEDDAR));
                adapter = new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1, // Layout for each item
                        toppings
                );
                selectedToppings.setAdapter(adapter);
                break;
            case "Meatzza":
                toppings = new ArrayList<>(Arrays.asList(Topping.SAUSAGE,Topping.PEPPERONI,Topping.BEEF,Topping.HAM));
                adapter = new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1, // Layout for each item
                        toppings
                );
                selectedToppings.setAdapter(adapter);
                break;
            case "Build Your Own":
                toppings = new ArrayList<>();
                adapter = new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1, // Layout for each item
                        toppings
                );
                selectedToppings.setAdapter(adapter);
                break;
        }
    }

    /**
     * add a Topping to the Build Your Own Pizza.
     */
    public void addSelectedTopping(View view){
        TextView priceText = findViewById(R.id.priceText);
        if(priceText.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Select Size", Toast.LENGTH_SHORT).show();
            return;
        }
        ListView availableToppings = findViewById(R.id.availableToppings);
        ListView chosenToppings = findViewById(R.id.selectedToppings);
        ArrayAdapter<Topping> adapter = (ArrayAdapter<Topping>) availableToppings.getAdapter();
        ArrayAdapter<Topping> adapter2 = (ArrayAdapter<Topping>) chosenToppings.getAdapter();
        ArrayList<Topping> chooseToppings = new ArrayList<>();
        ArrayList<Topping> selectedToppings = new ArrayList<>();
        if(adapter2.getCount()>=7){
            return;
        }
        if (adapter != null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                chooseToppings.add(adapter.getItem(i)); // Add each item to the ArrayList
            }
        }
        if(adapter!=null){
            for(int i = 0; i<adapter2.getCount();i++){
                selectedToppings.add(adapter2.getItem(i));
            }
        }

        if(!chooseToppings.contains(currentTopping)){
            return;
        }
        selectedToppings.add((Topping) currentTopping);
        chooseToppings.remove((Topping)currentTopping);

        double oldPrice = Double.parseDouble(priceText.getText().toString());
        double newPrice = oldPrice + 1.69;
        newPrice = Math.round(newPrice * 100.0) / 100.0;
        priceText.setText(getString(R.string.pizzaPrice,newPrice));

        ArrayAdapter<Topping> availableAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1, // Layout for each item
                chooseToppings
        );
        ArrayAdapter<Topping> selectedAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1, // Layout for each item
                selectedToppings
        );


        availableToppings.setAdapter(availableAdapter);
        chosenToppings.setAdapter(selectedAdapter);
    }

    /**
     * Method to remove Topping from Build Your Own Pizza.
     */
    public void removeSelectedTopping(View view){
        TextView priceText = findViewById(R.id.priceText);
        double [] arr = {8.99,10.99,12.99};
        if(priceText.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Select Size", Toast.LENGTH_SHORT).show();
            return;
        }
        for(double i : arr){
            if(Double.parseDouble(priceText.getText().toString())==i) {
                Toast.makeText(getApplicationContext(), "Add a Topping", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        ListView availableToppings = findViewById(R.id.availableToppings);
        ListView chosenToppings = findViewById(R.id.selectedToppings);
        ArrayAdapter<Topping> adapter = (ArrayAdapter<Topping>) availableToppings.getAdapter();
        ArrayAdapter<Topping> adapter2 = (ArrayAdapter<Topping>) chosenToppings.getAdapter();
        ArrayList<Topping> chooseToppings = new ArrayList<>();
        ArrayList<Topping> selectedToppings = new ArrayList<>();
        if (adapter != null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                chooseToppings.add(adapter.getItem(i)); // Add each item to the ArrayList
            }
        }
        if(adapter!=null){
            for(int i = 0; i<adapter2.getCount();i++){
                selectedToppings.add(adapter2.getItem(i));
            }
        }

        if(chooseToppings.contains((Topping) currentTopping)){
            Toast.makeText(getApplicationContext(), "Choose Selected Topping", Toast.LENGTH_SHORT).show();
            return;
        }
        selectedToppings.remove((Topping) currentTopping);
        chooseToppings.add((Topping)currentTopping);

        double oldPrice = Double.parseDouble(priceText.getText().toString());
        double newPrice = oldPrice - 1.69;
        newPrice = Math.round(newPrice * 100.0) / 100.0;
        priceText.setText(getString(R.string.pizzaPrice,newPrice));
        ArrayAdapter<Topping> availableAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1, // Layout for each item
                chooseToppings
        );
        ArrayAdapter<Topping> selectedAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1, // Layout for each item
                selectedToppings
        );

        availableToppings.setAdapter(availableAdapter);
        chosenToppings.setAdapter(selectedAdapter);
    }

    /**
     * Method to return to the main menu.
     */
    public void launchMenu(View v){
        Log.d("Debug", "launchMenu triggered");
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

}