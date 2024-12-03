package com.example.cs213_project5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NewYorkPizzaActivity extends AppCompatActivity {
    Object currentTopping = null;
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
                if (lastCheckedId != -1) {
                    // Check if the pizza type is "Build your own"
                    if (!PizzaTypes.getSelectedItem().toString().equals("Build your own")) {
                        return;
                    }
                    switchSize(lastCheckedId, checkedId);
                }
                lastCheckedId = checkedId; // Update the lastCheckedId

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

    public void onSizeSelected(String size){
        Spinner spinner = findViewById(R.id.PizzaTypes);
        String type = spinner.getSelectedItem().toString();
        Log.d("TAG",type);
        switch (type) {
            case "Deluxe":
                updatePrice(size, 16.99, 18.99, 20.99);
                break;
            case "BBQ Chicken":
                updatePrice( size, 14.99, 16.99, 19.99);
                break;
            case "Meatzza":
                updatePrice(size, 17.99, 19.99, 21.99);
                break;
            case "Build Your Own":
                updatePrice(size, 8.99,10.99,12.99);
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
        switch(type){
            case "Deluxe":
                crust.setText(getString(R.string.brooklyn));
                availableToppings.setEnabled(false);
                break;
            case "BBQ Chicken":
                crust.setText(getString(R.string.thin));
                availableToppings.setEnabled(false);
                break;
            case "Meatzza":
                crust.setText(getString(R.string.handtossed));
                availableToppings.setEnabled(false);
                break;
            case "Build Your Own":
                crust.setText(R.string.handtossed);
                availableToppings.setEnabled(true);
                break;
            default:
                availableToppings.setEnabled(false);
                break;
        }
    }
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
    private void switchSize(int oldCheckedId, int newCheckedId) {
        Spinner PizzaTypes = findViewById(R.id.PizzaTypes);
        TextView price = findViewById(R.id.priceText);



        // Mapping of price differences
        Map<String, Double> priceDifferences = new HashMap<>();
        priceDifferences.put("small-medium", 2.0);
        priceDifferences.put("small-large", 4.0);
        priceDifferences.put("medium-small", -2.0);
        priceDifferences.put("medium-large", 2.0);
        priceDifferences.put("large-medium", -2.0);
        priceDifferences.put("large-small", -4.0);

        // Get the text of the old and new buttons
        RadioButton oldButton = findViewById(oldCheckedId);
        RadioButton newButton = findViewById(newCheckedId);

        String oldButtonText = oldButton.getText().toString().toLowerCase();
        String newButtonText = newButton.getText().toString().toLowerCase();

        // Form the key for the price difference map
        String key = oldButtonText + "-" + newButtonText;

        // Update the price if the key exists
        if (priceDifferences.containsKey(key)) {
            double oldPrice = Double.parseDouble(price.getText().toString());
            double priceChange = priceDifferences.get(key);
            double newPrice = oldPrice + priceChange;
            newPrice = Math.round(newPrice * 100.0) / 100.0;
            price.setText(String.valueOf(newPrice));
        }
    }
    private void setPriceComboBox(String newValue){

        if(!newValue.equals("Build Your Own")){
            return;
        }
        switch(getSelectedButtonText()){
            case "Small":
                price.setText("8.99");
                break;
            case "Medium":
                price.setText("10.99");
                break;
            case "Large":
                price.setText("12.99");
                break;
            default:
                price.setText("0");
        }
    }

    public void launchMenu(View v){
            Log.d("Debug", "launchMenu triggered");
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
    }

}