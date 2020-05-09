package com.example.foodmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodmanager.R;
import com.example.foodmanager.adapters.DatabaseAdapter;
import com.example.foodmanager.helpers.DatabaseHelper;
import com.example.foodmanager.models.Product;

public class UpdateActivity extends AppCompatActivity {
    long productId = 0;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Product product;
    Product updateProduct;
    DatabaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        TextView name = (TextView) findViewById(R.id.updateName);
        TextView proteins = (TextView) findViewById(R.id.updateProteins);
        TextView fats = (TextView) findViewById(R.id.updateFats);
        TextView carbohydrates = (TextView) findViewById(R.id.updateCarbo);
        TextView calories = (TextView) findViewById(R.id.updateCalories);
        Button updateButton = (Button) findViewById(R.id.updateButton);

        Bundle extrasKind = getIntent().getExtras();
        if (extrasKind != null) {
            productId = extrasKind.getLong("id_product_long");
        }
        databaseHelper = new DatabaseHelper(getApplicationContext());
        // создаем базу данных
        databaseHelper.create_db();
        db = databaseHelper.open();
        // products = new ArrayList<>();
         adapter = new DatabaseAdapter(this);
        product = adapter.getProduct(productId,"100");
       // String nameProduct = product.getName();
        name.setText(product.getName());
        proteins.setText(String.valueOf(product.getProteins()));
        fats.setText(String.valueOf(product.getFats()));
        carbohydrates.setText(String.valueOf(product.getCarbohydrates()));
        calories.setText(String.valueOf(product.getCalories()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateProduct = adapter.getProduct(productId,"100");



    }
    public void updateButton(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        adapter.update(updateProduct);
        startActivity(intent);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();

    }
}
