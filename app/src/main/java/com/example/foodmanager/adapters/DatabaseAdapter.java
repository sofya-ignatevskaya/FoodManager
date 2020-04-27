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

import com.example.foodmanager.R;
import com.example.foodmanager.helpers.DatabaseHelper;
import com.example.foodmanager.models.Product;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter extends ArrayAdapter<Product> {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    private LayoutInflater inflater;
    private int layout;
    private List<Product> products;

    public DatabaseAdapter(Context context, int resource, List<Product> products) {
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
    public DatabaseAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DatabaseHelper.idProduct, DatabaseHelper.nameProduct,DatabaseHelper.Proteins, DatabaseHelper.Fats,
                DatabaseHelper.Carbohydrates, DatabaseHelper.Calories};
        return  database.query(DatabaseHelper.tProduct, columns, null, null, null, null, null);
    }

    public List<Product> getProducts(){
        ArrayList<Product> products = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.idProduct));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.nameProduct));
                double proteins = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.Proteins));
                double fats = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.Fats));
                double carbohydrates = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.Carbohydrates));
                double calories = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.Calories));
                products.add(new Product(id, name, proteins, fats, carbohydrates, calories));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return  products;
    }

   /* public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DatabaseHelper.tProduct);
    }*/

    public Product getProduct(long id){
        Product product = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?", DatabaseHelper.tProduct, DatabaseHelper.idProduct);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.nameProduct));
            double proteins = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.Proteins));
            double fats = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.Fats));
            double carbohydrates = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.Carbohydrates));
            double calories = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.Carbohydrates));
            product = new Product(id, name, proteins, fats, carbohydrates, calories);
        }
        cursor.close();
        return  product;
    }

    public long insert(Product product){

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.nameProduct, product.getName());
        cv.put(DatabaseHelper.Proteins, product.getProteins());
        cv.put(DatabaseHelper.Fats, product.getFats());
        cv.put(DatabaseHelper.Carbohydrates, product.getCarbohydrates());
        cv.put(DatabaseHelper.Calories, product.getCalories());

        return  database.insert(DatabaseHelper.tProduct, null, cv);
    }

    public long delete(long productId){

        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(productId)};
        return database.delete(DatabaseHelper.tProduct, whereClause, whereArgs);
    }

    public long update(Product product){

        String whereClause = DatabaseHelper.idProduct + "=" + String.valueOf(product.getId());
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.nameProduct, product.getName());
        cv.put(DatabaseHelper.Proteins, product.getProteins());
        cv.put(DatabaseHelper.Fats, product.getFats());
        cv.put(DatabaseHelper.Carbohydrates, product.getCarbohydrates());
        cv.put(DatabaseHelper.Calories, product.getCalories());
        return database.update(DatabaseHelper.tProduct, cv, whereClause, null);
    }
}
