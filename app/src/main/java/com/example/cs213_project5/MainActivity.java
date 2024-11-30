package com.example.cs213_project5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button chicagoPizzaButton = findViewById(R.id.button);
        Button nyPizzaButton = findViewById(R.id.button2);
        Button checkAllOrdersButton = findViewById(R.id.button3);
        Button checkCurrentOrderButton = findViewById(R.id.button4);

        chicagoPizzaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToChicagoPizza();
            }
        });

        nyPizzaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToNYPizza();
            }
        });

        checkAllOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToCheckAllOrders();
            }
        });

        checkCurrentOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToCheckCurrentOrders();
            }
        });
    }

    // Method to switch to Chicago Pizza Activity
    private void switchToChicagoPizza() {
        Intent intent = new Intent(MainActivity.this, ChicagoPizzaActivity.class);
        startActivity(intent);
    }

    // Method to switch to NY Pizza Activity
    private void switchToNYPizza() {
        Intent intent = new Intent(MainActivity.this, NewYorkPizzaActivity.class);
        startActivity(intent);
    }

    // Method to switch to Check All Orders Activity
    private void switchToCheckAllOrders() {
        Intent intent = new Intent(MainActivity.this, AllOrdersActivity.class);
        startActivity(intent);
    }

    // Method to switch to Check Current Orders Activity
    private void switchToCheckCurrentOrders() {
        Intent intent = new Intent(MainActivity.this, CurrentOrderActivity.class);
        startActivity(intent);
    }
}
