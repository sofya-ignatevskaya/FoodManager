package com.example.foodmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.FormatException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
        kindSelection.setVisibility(View.INVISIBLE);
        //видимость
        categorySelection.setVisibility(View.INVISIBLE);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.create_db();
        db = databaseHelper.open();
        adapter = new DatabaseAdapter(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void addButton(View v) {
        try {
            String updateName = name.getText().toString();
            double updateProteins = Double.parseDouble(proteins.getText().toString());
            double updateFats = Double.parseDouble(fats.getText().toString());
            double updateCarbohydrates = Double.parseDouble(carbohydrates.getText().toString());
            double updateCalories = Double.parseDouble(calories.getText().toString());
            double updateWeight = 100;
            int updateKind = 0;
            int updateCategory = 0;

            kindItem = kindSpinner.getSelectedItemPosition();
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
            if (updateKind == 1) {
                categoryItem = categorySpinner.getSelectedItemPosition();
                switch (categoryItem) {
                    case 0:
                        updateCategory = 1;
                        updateKind = 1;
                        break;
                    case 1:
                        updateCategory = 2;
                        updateKind = 1;
                        break;
                    case 2:
                        updateCategory = 3;
                        updateKind = 1;

                        break;
                    case 3:
                        updateCategory = 4;
                        updateKind = 1;
                        break;
                    case 4:
                        updateCategory = 5;
                        updateKind = 1;
                        break;
                    case 5:
                        updateCategory = 6;
                        updateKind = 1;
                        break;
                    default:
                        break;
                }
            }
            Product updateProduct = new Product(updateName, updateProteins, updateFats, updateCarbohydrates, updateCalories, updateWeight, updateKind, updateCategory);
            adapter.insert(updateProduct);
            goHome();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не все поля заполнены",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void goHome() {
        // переход к главной activity
        Intent intent = new Intent(this, MainActivity.class);
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
