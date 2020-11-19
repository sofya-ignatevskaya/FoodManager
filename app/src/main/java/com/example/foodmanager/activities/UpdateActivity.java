package com.example.foodmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodmanager.R;
import com.example.foodmanager.adapters.DatabaseAdapter;
import com.example.foodmanager.helpers.DatabaseHelper;
import com.example.foodmanager.models.Product;

public class UpdateActivity extends AppCompatActivity {
    long productId = 0;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Product product;
    DatabaseAdapter adapter;
    TextView name;
    TextView proteins;
    TextView fats;
    TextView carbohydrates;
    TextView calories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        name = (TextView) findViewById(R.id.updateName);
        proteins = (TextView) findViewById(R.id.updateProteins);
        fats = (TextView) findViewById(R.id.updateFats);
        carbohydrates = (TextView) findViewById(R.id.updateCarbo);
        calories = (TextView) findViewById(R.id.updateCalories);

        Bundle extrasKind = getIntent().getExtras();
        if (extrasKind != null) {
            productId = extrasKind.getLong("id_product_long");
        }

        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.create_db();
        db = databaseHelper.open();
        adapter = new DatabaseAdapter(this);

        product = adapter.getProduct(productId, "100");
        name.setText(product.getName());
        proteins.setText(String.valueOf(product.getProteins()));
        fats.setText(String.valueOf(product.getFats()));
        carbohydrates.setText(String.valueOf(product.getCarbohydrates()));
        calories.setText(String.valueOf(product.getCalories()));
    }

    public void updateButton(View v) {

        try {
            String updateName = name.getText().toString();
            double updateProteins = Double.parseDouble(proteins.getText().toString());
            double updateFats = Double.parseDouble(fats.getText().toString());
            double updateCarbohydrates = Double.parseDouble(carbohydrates.getText().toString());
            double updateCalories = Double.parseDouble(calories.getText().toString());
            double updateWeight = 100;
            Product updateProduct = new Product(productId, updateName, updateProteins, updateFats, updateCarbohydrates, updateCalories, updateWeight);
            adapter.update(updateProduct);
            goHome();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не все поля заполнены",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteButton(View v) {
        adapter.delete(productId);
        goHome();
    }

    private void goHome() {
        // переход к главной activity
        Intent intent = new Intent(this, ProductActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение
        db.close();
    }
}
