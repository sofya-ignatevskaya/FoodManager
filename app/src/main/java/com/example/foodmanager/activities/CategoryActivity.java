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
import com.example.foodmanager.helpers.DatabaseHelper;
import com.example.foodmanager.models.Category;
import com.example.foodmanager.models.Product;

public class CategoryActivity extends AppCompatActivity {

    ListView categoryList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor categoryCursor;
    SimpleCursorAdapter categoryAdapter;
    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        categoryList = (ListView) findViewById(R.id.list);
        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                Log.d(LOG_TAG, "itemClick: position = " + position + ", id = "
                        + id);
            }
        });
        databaseHelper = new DatabaseHelper(getApplicationContext());
        // создаем базу данных
        databaseHelper.create_db();
    }

    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.open();
        //получаем данные из бд в виде курсора
        categoryCursor =  db.rawQuery("select * from "+ DatabaseHelper.tCategory, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[] {DatabaseHelper.nameCategory};
        // создаем адаптер, передаем в него курсор
        categoryAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                categoryCursor, headers, new int[]{android.R.id.text1}, 0);
        categoryList.setAdapter(categoryAdapter);
    }



    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        categoryCursor.close();
    }


}
