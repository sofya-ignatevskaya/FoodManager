package com.example.foodmanager.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListProduct {
    public  List<Product> products = new ArrayList<>();

    public Product changeWeight( Product product, String text)
    {
        //long id = product.getId();
        //String name = product.getName();
        double proteins = product.getProteins()/100 * Double.valueOf(text);
        product.setProteins(proteins);
        double fats = product.getFats()/100 * Double.valueOf(text);
        product.setFats(fats);
        double carbohydrates = product.getCarbohydrates()/100 * Double.valueOf(text);
        product.setCarbohydrates(carbohydrates);
        double calories = product.getCalories()/100 * Double.valueOf(text);
        product.setCarbohydrates(calories);
        return product;
    }
}
