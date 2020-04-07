package com.example.foodmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.foodmanager.R;
import com.example.foodmanager.activities.CategoryActivity;
import com.example.foodmanager.helpers.CategoryDatabaseHelper;

public class MainActivity extends AppCompatActivity {

    ListView userList;
    CategoryDatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void chooseProduct(View view) {
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
    }




}
