package com.example.foodmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.foodmanager.R;
import com.example.foodmanager.activities.CategoryActivity;
import com.example.foodmanager.helpers.DatabaseHelper;

public class MainActivity extends AppCompatActivity {


    ListView userList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    long userPosition = 0;
    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userList = (ListView) findViewById(R.id.userList);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        // создаем базу данных
        databaseHelper.create_db();

        // передается id объекта
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userPosition = extras.getLong("id_product");

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.open();
        //получаем данные из бд в виде курсора
        userCursor = db.rawQuery("select * from Product where  Product.ProductId =?", new String[]{String.valueOf(userPosition)});
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DatabaseHelper.idProduct, DatabaseHelper.nameProduct, DatabaseHelper.Proteins, DatabaseHelper.Fats,
                DatabaseHelper.Carbohydrates, DatabaseHelper.Calories};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, R.layout.list_of_product,
                userCursor, headers, new int[]{R.id.id_of_product, R.id.name_of_product, R.id.proteins_of_product,
                R.id.fats_of_product, R.id.carbohydrates_of_product, R.id.calories_of_product}, 0);
        userList.setAdapter(userAdapter);
    }


    public void chooseProduct(View view) {
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }


}
