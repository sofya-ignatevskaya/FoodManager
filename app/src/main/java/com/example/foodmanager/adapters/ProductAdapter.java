package com.example.foodmanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodmanager.R;
import com.example.foodmanager.models.Product;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {
    private LayoutInflater inflater;
    private int layout;
    public List<Product> products;

    public ProductAdapter(Context context, int resource, List<Product> products) {
        super(context, resource, products);
        this.products = products;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        TextView idProduct = (TextView) view.findViewById(R.id.id_of_product);
        TextView name = (TextView) view.findViewById(R.id.name_of_product);
        TextView proteins = (TextView) view.findViewById(R.id.proteins_of_product);
        TextView fats = (TextView) view.findViewById(R.id.fats_of_product);
        TextView carbohydrates = (TextView) view.findViewById(R.id.carbohydrates_of_product);
        TextView calories = (TextView) view.findViewById(R.id.calories_of_product);

        Product product = products.get(position);

        idProduct.setText(String.valueOf(product.getId()));
        name.setText(product.getName());
        proteins.setText(String.valueOf(product.getProteins()));
        fats.setText(String.valueOf(product.getFats()));
        carbohydrates.setText(String.valueOf(product.getCarbohydrates()));
        calories.setText(String.valueOf(product.getCalories()));

        return view;
    }
}
