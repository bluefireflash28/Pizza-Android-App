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

import java.util.ArrayList;
import java.util.Arrays;

public class NewYorkPizzaActivity extends AppCompatActivity {

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
        public void launchMenu(View v){
            Log.d("Debug", "launchMenu triggered");
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
    }

}