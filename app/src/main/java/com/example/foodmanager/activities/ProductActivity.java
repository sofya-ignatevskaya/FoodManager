package com.example.foodmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.foodmanager.R;
import com.example.foodmanager.adapters.DatabaseAdapter;
import com.example.foodmanager.adapters.ProductAdapter;
import com.example.foodmanager.helpers.DatabaseHelper;
import com.example.foodmanager.models.Product;

public class ProductActivity extends AppCompatActivity {

    ListView productList;
    EditText searchEditText;
    DatabaseHelper databaseHelper;
    DatabaseAdapter databaseAdapter;
    SQLiteDatabase db;
    Cursor productCursor;
    SimpleCursorAdapter productAdapter;
    DatabaseAdapter adapter;

    long categoryId = 0;
    long productId = 0;
    long kindId = 0;
    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        searchEditText = (EditText) findViewById(R.id.searchEditText);
        productList = (ListView) findViewById(R.id.list_for_product);
        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> productList, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), WeightActivity.class);
                intent.putExtra("id_product", id);
                startActivity(intent);
                Log.d(LOG_TAG, "itemClick: position = " + position + ", id = "
                        + id);
            }
        });
        productList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> productList, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), UpdateActivity.class);
                intent.putExtra("id_product_long", id);
                startActivity(intent);
                Log.d(LOG_TAG, "itemClick: position = " + position + ", id = "
                        + id);
                return true;
            }

        });
        databaseHelper = new DatabaseHelper(getApplicationContext());
        // создаем базу данных
        databaseHelper.create_db();

        // передается id объекта
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            categoryId = extras.getLong("id_category");
        }

        Bundle extrasKind = getIntent().getExtras();
        if (extrasKind != null) {
            kindId = extrasKind.getLong("id_kind");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.open();
        //получаем данные из бд в виде курсора
        if (kindId == 2) {
            productCursor = db.rawQuery("select * from " + DatabaseHelper.tProduct + " where " + DatabaseHelper.productKindId + "=?", new String[]{String.valueOf(kindId)});
            // определяем, какие столбцы из курсора будут выводиться в ListView
            String[] headers = new String[]{DatabaseHelper.idProduct, DatabaseHelper.nameProduct, DatabaseHelper.Proteins, DatabaseHelper.Fats,
                    DatabaseHelper.Carbohydrates, DatabaseHelper.Calories, DatabaseHelper.Weight};
            // создаем адаптер, передаем в него курсор
            productAdapter = new SimpleCursorAdapter(this, R.layout.list_of_product,
                    productCursor, headers, new int[]{R.id.id_of_product, R.id.name_of_product, R.id.proteins_of_product,
                    R.id.fats_of_product, R.id.carbohydrates_of_product, R.id.calories_of_product, R.id.weight_of_product}, 0);

            searchEditText.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                // при изменении текста выполняем фильтрацию
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    productAdapter.getFilter().filter(s.toString());
                }
            });
            productAdapter.setFilterQueryProvider(new FilterQueryProvider() {
                @Override
                public Cursor runQuery(CharSequence constraint) {

                    if (constraint == null || constraint.length() == 0) {

                        return db.rawQuery("select * from " + DatabaseHelper.tProduct + " where " + DatabaseHelper.productKindId + "=?", new String[]{String.valueOf(kindId)});
                    } else {
                        return db.rawQuery("select * from " + DatabaseHelper.tProduct + " where " + DatabaseHelper.productKindId + "=?" + "and " +
                                DatabaseHelper.nameProduct + " like ?", new String[]{String.valueOf(kindId), "%" + constraint.toString() + "%"});
                    }
                }
            });

            productList.setAdapter(productAdapter);
        } else {
            productCursor = db.rawQuery("select * from " + DatabaseHelper.tProduct + " where " + DatabaseHelper.CategoryId + "=?", new String[]{String.valueOf(categoryId)});
            // определяем, какие столбцы из курсора будут выводиться в ListView
            String[] headers = new String[]{DatabaseHelper.idProduct, DatabaseHelper.nameProduct, DatabaseHelper.Proteins, DatabaseHelper.Fats,
                    DatabaseHelper.Carbohydrates, DatabaseHelper.Calories, DatabaseHelper.Weight};
            // создаем адаптер, передаем в него курсор
            productAdapter = new SimpleCursorAdapter(this, R.layout.list_of_product,
                    productCursor, headers, new int[]{R.id.id_of_product, R.id.name_of_product, R.id.proteins_of_product,
                    R.id.fats_of_product, R.id.carbohydrates_of_product, R.id.calories_of_product, R.id.weight_of_product}, 0);
            searchEditText.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                // при изменении текста выполняем фильтрацию
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    productAdapter.getFilter().filter(s.toString());
                }
            });
            productAdapter.setFilterQueryProvider(new FilterQueryProvider() {
                @Override
                public Cursor runQuery(CharSequence constraint) {

                    if (constraint == null || constraint.length() == 0) {

                        return db.rawQuery("select * from " + DatabaseHelper.tProduct + " where " + DatabaseHelper.CategoryId + "=?", new String[]{String.valueOf(categoryId)});
                    } else {
                        return db.rawQuery("select * from " + DatabaseHelper.tProduct + " where " + DatabaseHelper.CategoryId + "=?" + "and " +
                                DatabaseHelper.nameProduct + " like ?", new String[]{String.valueOf(categoryId), "%" + constraint.toString() + "%"});
                    }
                }
            });
            productList.setAdapter(productAdapter);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        productCursor.close();
    }


}


