package com.example.cs213_project5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class AllOrdersActivity extends AppCompatActivity {

    private Spinner orderNumberSpinner;
    private ListView ordersList;
    private EditText orderTotal;
    private StateManager stateManager = StateManager.getInstance();
    private ArrayAdapter<String> numberListAdapter;
    private ArrayAdapter<String> ordersListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders);  // Assuming the layout XML is named activity_all_orders

        orderNumberSpinner = findViewById(R.id.orderNumberSpinner);
        ordersList = findViewById(R.id.ordersList);
        orderTotal = findViewById(R.id.orderTotal);

        setupSpinner();
        setupCancelOrderButton();
    }

    private void setupSpinner() {
        numberListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stateManager.numberList);
        numberListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderNumberSpinner.setAdapter(numberListAdapter);

        orderNumberSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = (String) parent.getItemAtPosition(position);
                updateOrderDetails(selectedValue);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                ordersList.setAdapter(null);
                orderTotal.setText("0");
            }
        });
    }

    private void updateOrderDetails(String selectedValue) {
        ArrayList<Order> orders = stateManager.getAllOrders();
        for (Order o : orders) {
            if (String.valueOf(o.getNumber()).equals(selectedValue)) {
                ordersListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, o.getPizzaListString());
                ordersList.setAdapter(ordersListAdapter);
                orderTotal.setText(String.valueOf(o.getTotal()));
                return;
            }
        }
    }

    private void setupCancelOrderButton() {
        Button cancelOrderButton = findViewById(R.id.cancelOrderButton);
        cancelOrderButton.setOnClickListener(v -> {
            int selectedIndex = orderNumberSpinner.getSelectedItemPosition();
            if (selectedIndex >= 0 && !stateManager.getAllOrders().isEmpty()) {
                stateManager.getAllOrders().remove(selectedIndex);
                stateManager.numberList.remove(selectedIndex);
                numberListAdapter.notifyDataSetChanged();
                ordersList.setAdapter(null);
                orderTotal.setText("0");
            }
        });
    }
    public void launchMenu(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}