package com.example.foodmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodmanager.R;
import com.example.foodmanager.adapters.DatabaseAdapter;
import com.example.foodmanager.helpers.DatabaseHelper;
import com.example.foodmanager.models.Product;

import java.util.ArrayList;

public class WeightActivity extends AppCompatActivity {

    long productId = 0;
    EditText weightEditText;
    Button weightButton;
    ArrayList<Product> products;
    Product onePr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        weightEditText = (EditText) findViewById(R.id.weightEditText);
        weightButton = (Button) findViewById(R.id.weightButton);
        products = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            productId = extras.getLong("id_product");
        }
    }

    public void weightButton(View v) {
        try {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            DatabaseAdapter adapter = new DatabaseAdapter(this);
            String w = weightEditText.getText().toString();
            if (productId > 0 && w != null) {
                onePr = adapter.getProduct(productId, w);
            }
            intent.putExtra("productsViaWeight", new Product(onePr.getId(), onePr.getName(), onePr.getProteins(),
                    onePr.getFats(), onePr.getCarbohydrates(), onePr.getCalories(), onePr.getWeight()));
            startActivity(intent);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Введите вес продукта",
                    Toast.LENGTH_SHORT).show();
        }
    }
}



