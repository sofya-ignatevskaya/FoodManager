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

public class CategoryActivity extends AppCompatActivity {

    ListView categoryList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor categoryCursor;
    SimpleCursorAdapter categoryAdapter;
    long kindId = 0;
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
                intent.putExtra("id_category", id);
                startActivity(intent);
                Log.d(LOG_TAG, "itemClick: position = " + position + ", id = "
                        + id);
            }
        });

        // передается id объекта
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            kindId = extras.getLong("id_kind");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.create_db();
        // открываем подключение
        db = databaseHelper.open();
        //получаем данные из бд в виде курсора
        categoryCursor = db.rawQuery("select * from " + DatabaseHelper.tCategory +
                " where " + DatabaseHelper.categoryKindId + "=?", new String[]{String.valueOf(kindId)});
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DatabaseHelper.nameCategory};
        // создаем адаптер, передаем в него курсор
        categoryAdapter = new SimpleCursorAdapter(this, R.layout.list_for_kind_and_category,
                categoryCursor, headers, new int[]{R.id.name}, 0);
        categoryList.setAdapter(categoryAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        categoryCursor.close();
    }
}
