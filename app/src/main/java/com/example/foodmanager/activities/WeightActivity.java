package com.example.foodmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.foodmanager.R;
import com.example.foodmanager.adapters.DatabaseAdapter;
import com.example.foodmanager.models.ListProduct;
import com.example.foodmanager.models.Product;

import java.util.List;

public class WeightActivity extends AppCompatActivity {
    long productId = 0;
    EditText weightEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        weightEditText = (EditText) findViewById(R.id.weightEditText);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            productId = extras.getLong("id_product");
        }
    }

    public void weightButton (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
   /* @Override
    protected void onResume() {
        super.onResume();
        ListProduct lp = new ListProduct();
        DatabaseAdapter adapter = new DatabaseAdapter(this);

        if (productId > 0) {

            //list = adapter.getProducts();
            Product onePr = adapter.getProduct(productId, weightEditText.getText().toString());
            // lp.changeWeight(onePr, weightEditText.getText().toString());
                lp.products.add(onePr);
            }
            //productAdapter.add();
            /* new Product(onePr.getId(),onePr.getName(), onePr.getProteins(),onePr.getFats(),onePr.getCarbohydrates(), onePr.getCalories())

        }*/
    }


