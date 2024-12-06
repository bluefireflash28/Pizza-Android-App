package com.example.cs213_project5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class CurrentOrderActivity extends AppCompatActivity {
    int selectedIndex = -1;
    NYPizza nypizza = new NYPizza();
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_current_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView subtotal = findViewById(R.id.subtotal);
        subtotal.setText(String.valueOf(StateManager.getInstance().subtotal));
        TextView salestax = findViewById(R.id.salesTax);
        salestax.setText(String.valueOf(StateManager.getInstance().salesTax));
        TextView orderTotal = findViewById(R.id.curOrderTotal);
        orderTotal.setText(String.valueOf(StateManager.getInstance().orderTotal));
        TextView orderNumber = findViewById(R.id.orderNumber);
        orderNumber.setText(String.valueOf(StateManager.getInstance().orderNumber));


        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1, // Predefined layout for list items
                StateManager.getInstance().getCurrentOrdersStrings()
        );
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Get the selected item
            String selectedTopping = String.valueOf(position);
            selectedIndex = position;
            // Display the selected topping
            Toast.makeText(getApplicationContext(), "Selected pizza: " + selectedTopping, Toast.LENGTH_SHORT).show();
        });
        Button orderButton = findViewById(R.id.orderButton);
        orderButton.setEnabled(!StateManager.getInstance().getCurrentOrders().isEmpty());

    }
    public void placeOrder(View view) {
        TextView orderNumber = findViewById(R.id.orderNumber);
        Order o = new Order(Integer.parseInt((String) orderNumber.getText()), StateManager.getInstance().orderTotal, StateManager.getInstance().getCurrentOrders(), StateManager.getInstance().getCurrentOrdersStrings());
        StateManager.getInstance().getAllOrders().add(o);
        StateManager.getInstance().numberList.add((String) orderNumber.getText());

        StateManager.getInstance().orderNumber++;
        orderNumber.setText(String.valueOf(StateManager.getInstance().orderNumber));

        StateManager.getInstance().getCurrentOrders().clear();
        StateManager.getInstance().getCurrentOrdersStrings().clear();
        StateManager.getInstance().subtotal = 0.0;
        StateManager.getInstance().salesTax = 0.0;
        StateManager.getInstance().orderTotal = 0.0;
        TextView subtotal = findViewById(R.id.subtotal);
        subtotal.setText("0");
        TextView salesTax = findViewById(R.id.salesTax);
        salesTax.setText("0");
        TextView orderTotal = findViewById(R.id.curOrderTotal);
        orderTotal.setText("0");
        Button orderButton = findViewById(R.id.orderButton);
        orderButton.setEnabled(!StateManager.getInstance().getCurrentOrders().isEmpty());

        ArrayList<String> arr = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                CurrentOrderActivity.this,
                android.R.layout.simple_list_item_1, // Predefined layout for list items
                arr
        );
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
    }
    public void removePizza(View view){
        if(selectedIndex==-1){
            Toast.makeText(getApplicationContext(), "No Pizza Selected", Toast.LENGTH_LONG).show();
            return;
        }
        StateManager.getInstance().subtotal -= StateManager.getInstance().getCurrentOrders().get(selectedIndex).price();
        StateManager.getInstance().subtotal = Math.round((StateManager.getInstance().subtotal * 100.0)) / 100.0;
        StateManager.getInstance().salesTax -= StateManager.getInstance().getCurrentOrders().get(selectedIndex).price() * 0.06625;
        StateManager.getInstance().salesTax = Math.round((StateManager.getInstance().salesTax * 100.0)) / 100.0;
        StateManager.getInstance().orderTotal -= StateManager.getInstance().getCurrentOrders().get(selectedIndex).price() + StateManager.getInstance().getCurrentOrders().get(selectedIndex).price() * 0.06625;
        StateManager.getInstance().orderTotal = Math.round((StateManager.getInstance().orderTotal * 100.0)) / 100.0;
        StateManager.getInstance().getCurrentOrders().remove(selectedIndex); // Remove item at the selected index
        StateManager.getInstance().getCurrentOrdersStrings().remove(selectedIndex); // Remove item at the selected index
        TextView subtotal = findViewById(R.id.subtotal);
        subtotal.setText(String.valueOf(StateManager.getInstance().subtotal));
        TextView salesTax = findViewById(R.id.salesTax);
        salesTax.setText(String.valueOf(StateManager.getInstance().salesTax));
        TextView orderTotal = findViewById(R.id.curOrderTotal);
        orderTotal.setText(String.valueOf(StateManager.getInstance().orderTotal));
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1, // Predefined layout for list items
                StateManager.getInstance().getCurrentOrdersStrings()
        );
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        Button orderButton = findViewById(R.id.orderButton);
        orderButton.setEnabled(!StateManager.getInstance().getCurrentOrders().isEmpty());


    }

    public void launchMenu(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public Pizza addNYPizza(String size, String pizzaType){

        switch(pizzaType){
            case "Deluxe":
                Pizza a =  (nypizza.createDeluxe(size));
                a.setStyle("New York");
                StateManager.getInstance().subtotal+=a.price();
                StateManager.getInstance().subtotal = roundToTwoDecimals(StateManager.getInstance().subtotal);
                StateManager.getInstance().salesTax += a.price()* 0.06625;
                StateManager.getInstance().salesTax = roundToTwoDecimals(StateManager.getInstance().salesTax);
                StateManager.getInstance().orderTotal += a.price()*1.06625;
                StateManager.getInstance().orderTotal = roundToTwoDecimals(StateManager.getInstance().orderTotal);
                return a;

            case "BBQ Chicken":
                Pizza b =  (nypizza.createBBQChicken(size));
                b.setStyle("New York");
                StateManager.getInstance().subtotal+=b.price();
                StateManager.getInstance().subtotal = Math.round((StateManager.getInstance().subtotal * 100.0))/100.0;
                StateManager.getInstance().salesTax += b.price()* 0.06625;
                StateManager.getInstance().salesTax = Math.round((StateManager.getInstance().salesTax * 100.0))/100.0;
                StateManager.getInstance().orderTotal += b.price()*1.06625;
                StateManager.getInstance().orderTotal = Math.round((StateManager.getInstance().orderTotal * 100.0))/100.0;
                return b;

            case "Meatzza":
                Pizza c =  (nypizza.createMeatzza(size));
                c.setStyle("New York");
                StateManager.getInstance().subtotal+=c.price();
                StateManager.getInstance().subtotal = Math.round((StateManager.getInstance().subtotal * 100.0))/100.0;
                StateManager.getInstance().salesTax += c.price()* 0.06625;
                StateManager.getInstance().salesTax = Math.round((StateManager.getInstance().salesTax * 100.0))/100.0;
                StateManager.getInstance().orderTotal += c.price()*1.06625;
                StateManager.getInstance().orderTotal = Math.round((StateManager.getInstance().orderTotal * 100.0))/100.0;
                return c;
        }
        return null;
    }
    /**
     * creates ny byo pizza
     * @param size of pizza
     * @param toppingsList toppings
     * @return byo pizza
     */
    public Pizza addNYPizzaBYO(String size, ArrayList<Topping> toppingsList){
        Pizza pizza = nypizza.createBuildYourOwn(size);
        for(Topping t: toppingsList){
            pizza.addTopping(t);
        }
        pizza.setStyle("New York");
        StateManager.getInstance().subtotal+= pizza.price();
        StateManager.getInstance().subtotal = Math.round((StateManager.getInstance().subtotal * 100.0))/100.0;
        StateManager.getInstance().salesTax += pizza.price()* 0.06625;
        StateManager.getInstance().salesTax = Math.round((StateManager.getInstance().salesTax * 100.0))/100.0;
        StateManager.getInstance().orderTotal += pizza.price()*1.06625;
        StateManager.getInstance().orderTotal = Math.round((StateManager.getInstance().orderTotal * 100.0))/100.0;
        return pizza;
    }
    private double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public void clearButton(View view) {

        StateManager.getInstance().getCurrentOrders().clear();
        StateManager.getInstance().getCurrentOrdersStrings().clear();
        StateManager.getInstance().subtotal = 0.0;
        StateManager.getInstance().salesTax = 0.0;
        StateManager.getInstance().orderTotal = 0.0;
        TextView subtotal = findViewById(R.id.subtotal);
        subtotal.setText("0");
        TextView salesTax = findViewById(R.id.salesTax);
        salesTax.setText("0");
        TextView orderTotal = findViewById(R.id.curOrderTotal);
        orderTotal.setText("0");
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1, // Predefined layout for list items
                StateManager.getInstance().getCurrentOrdersStrings()
        );
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

}