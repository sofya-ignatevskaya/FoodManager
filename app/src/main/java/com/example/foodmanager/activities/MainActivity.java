package com.example.foodmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.foodmanager.R;
import com.example.foodmanager.activities.CategoryActivity;
import com.example.foodmanager.adapters.DatabaseAdapter;
import com.example.foodmanager.adapters.ProductAdapter;
import com.example.foodmanager.helpers.DatabaseHelper;
import com.example.foodmanager.models.ListProduct;
import com.example.foodmanager.models.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    ListView userList;
    EditText weightEditText;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    String txtWeight;
    Product onePr; //= new Product(1, "Молоко", 3.2, 3.6, 4.8, 64);
    public static final String KEY_CONNECTIONS = "KEY_CONNECTIONS";

    long productId = 0;

    final String LOG_TAG = "myLogs";
    ArrayList<Product> products;
    ArrayList<Product> anotherProducts;
    Product productWithWeight;
    ProductAdapter productAdapter;
    List<Product> connectionsGet;
    List<Product> connections;
    SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userList = (ListView) findViewById(R.id.userList);

        // передается id объекта
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            productId = extras.getLong("id_product");
        }
        /* txtWeight = getIntent().getStringExtra("weight");*/
        // Intent productWeight = getIntent();
        //products =getIntent().getParcelableArrayListExtra("productsViaWeight");
        // Toast.makeText(this, products.get(1).getName(), Toast.LENGTH_LONG).show();

        //получаю продукт из WA
        Intent intent = getIntent();
        productWithWeight = (Product) intent.getParcelableExtra("productsViaWeight");

        databaseHelper = new DatabaseHelper(getApplicationContext());
        // создаем базу данных
        databaseHelper.create_db();


        // слушатель выбора в списке
        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // получаем выбранный пункт
                Product selectedProduct = (Product) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Был выбран пункт " + selectedProduct.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        };
        userList.setOnItemClickListener(itemListener);
        // adapter.close();


    }

    @Override
    protected void onResume() {
        super.onResume();

        String connectionsJSONString = getPreferences(MODE_PRIVATE).getString(KEY_CONNECTIONS, null);
        Type type = new TypeToken<List<Product>>() {}.getType();
        connectionsGet = new Gson().fromJson(connectionsJSONString, type);


        //получаем данные из бд в виде курсора
        /*userCursor = db.rawQuery("select * from Product where  Product._id =?", new String[]{String.valueOf(productId)});
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DatabaseHelper.idProduct, DatabaseHelper.nameProduct, DatabaseHelper.Proteins, DatabaseHelper.Fats,
                DatabaseHelper.Carbohydrates, DatabaseHelper.Calories};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, R.layout.list_of_product,
                userCursor, headers, new int[]{R.id.id_of_product, R.id.name_of_product, R.id.proteins_of_product,
                R.id.fats_of_product, R.id.carbohydrates_of_product, R.id.calories_of_product}, 0);
        userList.setAdapter(userAdapter);*/

        //setInitialData();

        db = databaseHelper.open();
        ListProduct lp = new ListProduct();
        anotherProducts = new ArrayList<>();

        productAdapter = new ProductAdapter(this, R.layout.list_of_product, anotherProducts);
        userList.setAdapter(productAdapter);
        if (connectionsGet != null) {
            productAdapter.addAll(connectionsGet);
        }

        if (productWithWeight != null) {

            productAdapter.add(productWithWeight);
            productAdapter.notifyDataSetChanged();
        }


        // DatabaseAdapter adapter = new DatabaseAdapter(this);

        //List<Product> list ;if(null != profile)
      /*if (productId > 0 && txtWeight != null) {
            //list = adapter.getProducts();
            onePr = adapter.getProduct(productId, txtWeight);
            lp.products.add(onePr);
           productAdapter.add(onePr);
       }*/
        // lp.changeWeight(onePr, weightEditText.getText().toString());
        // productAdapter.add(onePr);

        // new Product(onePr.getId(),onePr.getName(), onePr.getProteins(),onePr.getFats(),onePr.getCarbohydrates(), onePr.getCalories())


    }

    @Override
    protected void onPause() {
        super.onPause();

        if (productWithWeight != null) {
             editor = getPreferences(MODE_PRIVATE).edit();
            connections = new ArrayList<>();
            if (connectionsGet != null) {
                connections.addAll(connectionsGet);
            }
            connections.add(productWithWeight);
            String connectionsJSONString = new Gson().toJson(connections);
            editor.putString(KEY_CONNECTIONS, connectionsJSONString);
            editor.apply();
        }
    }


    private void setInitialData() {

        products.add(new Product(1, "Молоко", 3.2, 3.6, 4.8, 64));
        products.add(new Product(2, "Кефир", 2.8, 3.5, 3.9, 50));
        products.add(new Product(3, "Ряженка", 2.9, 2.5, 4.2, 54));
        products.add(new Product(4, "Йогурт", 5, 3.2, 3.5, 66));
        products.add(new Product(5, "Рис", 7, 1, 71.4, 330));
    }

    public void cleanProduct (View view) {
        editor = getPreferences(MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        productAdapter.clear();
        productAdapter.notifyDataSetChanged();


    }

    /*public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cleanProduct:

                break;
            default:
                break;
        }
    }*/

    public void chooseProduct(View view) {
        Intent intent = new Intent(this, KindActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        // userCursor.close();
    }


}
