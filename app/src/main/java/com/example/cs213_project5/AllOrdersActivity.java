package com.example.cs213_project5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AllOrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_orders);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner spinner = findViewById(R.id.spinner);
        ArrayList<String> numberList = StateManager.getInstance().numberList;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item, // Layout for each item
                numberList
        );
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<Order> orders = StateManager.getInstance().getAllOrders();
                String selectedValue = spinner.getSelectedItem().toString();
                ListView listView = findViewById(R.id.allOrders);
                for(Order o : orders){
                    if(String.valueOf(o.getNumber()).equals(selectedValue)){
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                                AllOrdersActivity.this,
                                android.R.layout.simple_list_item_1, // Predefined layout for list items
                                o.getPizzaListString()
                        );
                        listView.setAdapter(arrayAdapter);
                        TextView orderTotal = findViewById(R.id.orderTotal);
                        orderTotal.setText(String.valueOf(o.getTotal()));
                        return;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ListView listView = findViewById(R.id.allOrders);
                TextView orderTotal = findViewById(R.id.orderTotal);
                orderTotal.setText(String.valueOf(0));
                ArrayList<String> arr = new ArrayList<>();
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                        AllOrdersActivity.this,
                        android.R.layout.simple_list_item_1, // Predefined layout for list items
                        arr
                );
                listView.setAdapter(arrayAdapter);
            }
        });
    }

    public void cancelOrder(View view){
        if(StateManager.getInstance().getAllOrders().isEmpty()){
            return;
        }
        Spinner orderNumberBox = findViewById(R.id.spinner);
        ListView listView = findViewById(R.id.allOrders);
        int selectedIndex = orderNumberBox.getSelectedItemPosition();
        StateManager.getInstance().getAllOrders().remove(selectedIndex);
        StateManager.getInstance().numberList.remove(selectedIndex);
        ArrayList<String> numberList = StateManager.getInstance().numberList;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item, // Layout for each item
                numberList
        );
        orderNumberBox.setAdapter(adapter);
        if(orderNumberBox.getSelectedItem()==null){
            TextView orderTotal = findViewById(R.id.orderTotal);
            orderTotal.setText(String.valueOf(0));
            ArrayList<String> arr = new ArrayList<>();
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                    AllOrdersActivity.this,
                    android.R.layout.simple_list_item_1, // Predefined layout for list items
                    arr
            );
            listView.setAdapter(arrayAdapter);
        }

    }


    public void launchMenu(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }
}