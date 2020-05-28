package com.example.foodmanager.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodmanager.R;
import com.example.foodmanager.helpers.DatabaseHelper;
import com.example.foodmanager.models.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context) {

        dbHelper = new DatabaseHelper(context.getApplicationContext());
        dbHelper.create_db(); // тут изменила
    }

    private Cursor getAllEntries() {
        database = dbHelper.open();
        String[] columns = new String[]{DatabaseHelper.idProduct, DatabaseHelper.nameProduct, DatabaseHelper.Proteins, DatabaseHelper.Fats,
                DatabaseHelper.Carbohydrates, DatabaseHelper.Calories};
        return database.query(DatabaseHelper.tProduct, columns, null, null, null, null, null);

    }
    public List<Product> getProducts() {

        database = dbHelper.open();

        ArrayList<Product> products = new ArrayList<>();
        Cursor cursor = getAllEntries();

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.idProduct));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.nameProduct));
                double proteins = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.Proteins));
                double fats = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.Fats));
                double carbohydrates = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.Carbohydrates));
                double calories = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.Calories));
                double weight = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.Weight));
                products.add(new Product(id, name, proteins, fats, carbohydrates, calories,weight));
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        return products;
    }

    public Product getProduct(long id, String text) {

        database = dbHelper.open();
        Product product = null;

        Cursor cursor = database.rawQuery("select * from " + DatabaseHelper.tProduct + " where " + DatabaseHelper.idProduct + "=?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {

            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.nameProduct));
            double proteins = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.Proteins));
            double fats = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.Fats));
            double carbohydrates = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.Carbohydrates));
            double calories = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.Calories));
            double weight = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.Weight));
            product = new Product(id, name, proteins, fats, carbohydrates, calories, weight);
        }

        DecimalFormat df = new DecimalFormat("#.##");
        double proteins = product.getProteins() / 100 * Double.parseDouble(text);
        product.setProteins(roundAvoid(proteins,2));
        double fats = product.getFats() / 100 * Double.parseDouble(text);
        product.setFats(roundAvoid(fats,2));
        double carbohydrates = product.getCarbohydrates() / 100 * Double.parseDouble(text);
        product.setCarbohydrates(roundAvoid(carbohydrates,2));
        double calories = product.getCalories() / 100 * Double.parseDouble(text);
        product.setCalories(roundAvoid(calories,0));
        double weight =  Double.parseDouble(text);
        product.setWeight(roundAvoid(weight, 0));

        cursor.close();
        return product;
    }

    public  double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
    public long insert(Product product) {
        database = dbHelper.open();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.nameProduct, product.getName());
        cv.put(DatabaseHelper.Proteins, product.getProteins());
        cv.put(DatabaseHelper.Fats, product.getFats());
        cv.put(DatabaseHelper.Carbohydrates, product.getCarbohydrates());
        cv.put(DatabaseHelper.Calories, product.getCalories());
        cv.put(DatabaseHelper.Weight, product.getWeight());
        cv.put(DatabaseHelper.productKindId, product.getKind());
        cv.put(DatabaseHelper.CategoryId, product.getCategory());

        return database.insert(DatabaseHelper.tProduct, null, cv);
    }
    public long delete(long productId) {

        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(productId)};
        return database.delete(DatabaseHelper.tProduct, whereClause, whereArgs);
    }

    public long update(Product product) {
        String whereClause = DatabaseHelper.idProduct + "=" + String.valueOf(product.getId());
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.nameProduct, product.getName());
        cv.put(DatabaseHelper.Proteins, product.getProteins());
        cv.put(DatabaseHelper.Fats, product.getFats());
        cv.put(DatabaseHelper.Carbohydrates, product.getCarbohydrates());
        cv.put(DatabaseHelper.Calories, product.getCalories());
        cv.put(DatabaseHelper.Weight, product.getWeight());
        return database.update(DatabaseHelper.tProduct, cv, whereClause, null);
    }
}
