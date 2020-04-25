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

public class KindActivity extends AppCompatActivity {

    ListView kindList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor kindCursor;
    SimpleCursorAdapter kindAdapter;
    final String LOG_TAG = "myLogs";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kind);

        kindList = (ListView) findViewById(R.id.kindList);
        kindList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        intent = new Intent(KindActivity.this, CategoryActivity.class); //Заполняем Intent
                        break;
                    case 1:
                        intent = new Intent(KindActivity.this, ProductActivity.class); //Заполняем Intent
                        break;
                }
                //intent = new Intent(getApplicationContext(), CategoryActivity.class);
                intent.putExtra("id_kind", id);
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
        kindCursor = db.rawQuery("select * from " + DatabaseHelper.tKind, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DatabaseHelper.nameKind};
        // создаем адаптер, передаем в него курсор
        kindAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                kindCursor, headers, new int[]{android.R.id.text1}, 0);
        kindList.setAdapter(kindAdapter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        kindCursor.close();
    }
}
