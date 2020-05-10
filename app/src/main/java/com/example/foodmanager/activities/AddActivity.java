package com.example.foodmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.foodmanager.R;
import com.example.foodmanager.adapters.DatabaseAdapter;
import com.example.foodmanager.helpers.DatabaseHelper;
import com.example.foodmanager.models.Product;

public class AddActivity extends AppCompatActivity {
    String[] category = {"Молоко", "Крупы", "Овощи", "Фрукты", "Мясо", "Сладости"};
    String[] kind = {"Продукты", "Готовые блюда"};

    long productId = 0;
    String item;

    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    DatabaseAdapter adapter;

    TextView name;
    TextView proteins;
    TextView fats;
    TextView carbohydrates;
    TextView calories;
    TextView kindSelection;
    TextView categorySelection;
    Spinner kindSpinner;
    Spinner categorySpinner;

    int kindItem;
    int categoryItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name = (TextView) findViewById(R.id.addName);
        proteins = (TextView) findViewById(R.id.addProteins);
        fats = (TextView) findViewById(R.id.addFats);
        carbohydrates = (TextView) findViewById(R.id.addCarbo);
        calories = (TextView) findViewById(R.id.addCalories);
        kindSpinner = (Spinner) findViewById(R.id.kindSpinner);
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        kindSelection = (TextView) findViewById(R.id.kindSelection);
        kindSelection.setVisibility(View.INVISIBLE);
        categorySelection = (TextView) findViewById(R.id.categorySelection);
        categorySelection.setVisibility(View.INVISIBLE);
        categorySpinner.setVisibility(View.VISIBLE);

        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> kindAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, kind);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category);
        // Определяем разметку для использования при выборе элемента
        kindAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        kindSpinner.setAdapter(kindAdapter);
        categorySpinner.setAdapter(categoryAdapter);


        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                item = (String) parent.getItemAtPosition(position);
                kindSelection.setText(item);
                categorySelection.setText(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        kindSpinner.setOnItemSelectedListener(itemSelectedListener);
        categorySpinner.setOnItemSelectedListener(itemSelectedListener);
        kindItem = kindSpinner.getSelectedItemPosition();


        if (kindItem == 0) {
            categorySpinner.setVisibility(View.VISIBLE);
        }

        databaseHelper = new DatabaseHelper(getApplicationContext());
        // создаем базу данных
        databaseHelper.create_db();
        db = databaseHelper.open();
        adapter = new DatabaseAdapter(this);


    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    public void addButton(View v) {
        String updateName = name.getText().toString();
        double updateProteins = Double.parseDouble(proteins.getText().toString());
        double updateFats = Double.parseDouble(fats.getText().toString());
        double updateCarbohydrates = Double.parseDouble(carbohydrates.getText().toString());
        double updateCalories = Double.parseDouble(calories.getText().toString());
        double updateWeight = 100;
        int updateKind = 0;
        int updateCategory = 0;

        switch (kindItem) {
            case 0:
                updateKind = 1;
                break;
            case 1:
                updateKind = 2;
                break;
            default:
                break;
        }

        categoryItem = categorySpinner.getSelectedItemPosition();
        switch (categoryItem) {
            case 0:
                updateCategory = 1;

                break;
            case 1:
                updateCategory = 2;
                break;
            case 2:
                updateCategory = 3;
                break;
            case 3:
                updateCategory = 4;
                break;
            case 4:
                updateCategory = 5;
                break;
            case 5:
                updateCategory = 6;
                break;
            default:
                break;
        }

        Product updateProduct = new Product(updateName, updateProteins, updateFats, updateCarbohydrates, updateCalories, updateWeight, updateKind, updateCategory);
        adapter.insert(updateProduct);
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
        // Закрываем подключение и курсор
        db.close();

    }
}
