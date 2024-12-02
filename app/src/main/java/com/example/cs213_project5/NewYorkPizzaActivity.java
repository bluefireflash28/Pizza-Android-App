package com.example.cs213_project5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

//        RadioGroup radioGroup = findViewById(R.id.pizzaSize);
//        radioGroup.setOnCheckedChangeListener((group,checkedId)->{
//            setPrice();
//        });
        Spinner pizzaTypes = findViewById(R.id.PizzaTypes);
        pizzaTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Call your setPrice() function when an item is selected
                setPrice(view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: Handle cases where nothing is selected
            }
        });
    }

    public void setPrice(View view) {
        Spinner spinner = findViewById(R.id.PizzaTypes);
        String type = spinner.getSelectedItem().toString();
        TextView price = findViewById(R.id.priceNY);

        // Check if a RadioButton is selected
        String size = getSelectedButtonText();
        if (size == null) {
            Toast.makeText(this, "Please select a size", Toast.LENGTH_SHORT).show();
            return; // Exit if no size is selected
        }

        switch (type) {
            case "Deluxe":
                updatePrice(price, size, 16.99, 18.99, 20.99);
                break;
            case "BBQ Chicken":
                updatePrice(price, size, 14.99, 16.99, 19.99);
                break;
            case "Meatzza":
                updatePrice(price, size, 17.99, 19.99, 21.99);
                break;
        }
    }

    // Helper method to update the price based on size
    private void updatePrice(TextView price, String size, double small, double medium, double large) {
        switch (size) {
            case "small":
                price.setText(getString(R.string.pizzaPrice, small));
                break;
            case "medium":
                price.setText(getString(R.string.pizzaPrice, medium));
                break;
            case "large":
                price.setText(getString(R.string.pizzaPrice, large));
                break;
        }
    }

    private String getSelectedButtonText(){
        RadioGroup group = findViewById(R.id.pizzaSize);
        int id = group.getCheckedRadioButtonId();
        if(id!=-1){
            RadioButton r = findViewById(id);
            return r.getText().toString();
        }
        return null;
    }


        public void launchMenu(View v){
    Intent i = new Intent(this, MainActivity.class);
    startActivity(i);
    }

}