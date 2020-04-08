package com.example.foodmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.foodmanager.R;
import com.example.foodmanager.helpers.DatabaseHelper;

public class ProductActivity extends AppCompatActivity {

    ListView productList;

    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor productCursor;
    SimpleCursorAdapter productAdapter;
    long productId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        productList = (ListView) findViewById(R.id.list_for_product);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        // создаем базу данных
        databaseHelper.create_db();

        // передается id объекта
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            productId = extras.getLong("id");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.open();
        //получаем данные из бд в виде курсора
        productCursor = db.rawQuery("select * from " + DatabaseHelper.tProduct, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DatabaseHelper.nameProduct, DatabaseHelper.Proteins, DatabaseHelper.Fats,
                DatabaseHelper.Carbohydrates, DatabaseHelper.Calories};
        // создаем адаптер, передаем в него курсор
        productAdapter = new SimpleCursorAdapter(this, R.layout.list_of_product,
                productCursor, headers, new int[]{R.id.name_of_product, R.id.proteins_of_product,
                R.id.fats_of_product, R.id.carbohydrates_of_product, R.id.calories_of_product}, 0);
        productList.setAdapter(productAdapter);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        productCursor.close();
    }
}


