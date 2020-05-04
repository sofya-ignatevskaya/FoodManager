package com.example.foodmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.foodmanager.R;
import com.example.foodmanager.adapters.DatabaseAdapter;
import com.example.foodmanager.models.ListProduct;
import com.example.foodmanager.models.Product;

import java.util.List;

public class WeightActivity extends AppCompatActivity {
    long productId = 0;
    EditText weightEditText;
    Button weightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        weightEditText = (EditText) findViewById(R.id.weightEditText);
        weightButton = (Button) findViewById(R.id.weightButton);

       /* Bundle extras = getIntent().getExtras();
        if (extras != null) {
            productId = extras.getLong("id_product");
        }*/
    }

    /*public void weightButton (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }*/

    public void weightButton (View v) {
        switch (v.getId()) {
            case R.id.weightButton:
                // Говорим между какими Activity будет происходить связь
                Intent intent = new Intent(this, MainActivity.class);

                // указываем первым параметром ключ, а второе значение
                // по ключу мы будем получать значение с Intent
                intent.putExtra("weight", weightEditText.getText().toString());


                // показываем новое Activity
                startActivity(intent);
                break;
            default:
                break;
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
    }


