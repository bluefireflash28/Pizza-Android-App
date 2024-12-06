package com.example.cs213_project5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {

    private List<PizzaType> pizzaList;
    // Constructor
    public PizzaAdapter(List<PizzaType> pizzaList) {
        this.pizzaList = pizzaList;
    }

    @Override
    public PizzaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item layout for each pizza
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pizza_item, parent, false);
        return new PizzaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PizzaViewHolder holder, int position) {
        // Get the current pizza
        PizzaType pizza = pizzaList.get(position);

        // Set the pizza name and image
        holder.pizzaNameTextView.setText(pizza.getName());
        holder.pizzaImageView.setImageResource(pizza.getImageResId());
    }

    @Override
    public int getItemCount() {
        return pizzaList.size();
    }

    // ViewHolder class to hold the references for the views
    public static class PizzaViewHolder extends RecyclerView.ViewHolder {
        TextView pizzaNameTextView;
        ImageView pizzaImageView;

        public PizzaViewHolder(View itemView) {
            super(itemView);
            pizzaNameTextView = itemView.findViewById(R.id.pizza_name);
            pizzaImageView = itemView.findViewById(R.id.pizza_image);
        }
    }

}
