package com.example.foodmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodmanager.R;
import com.example.foodmanager.adapters.ProductAdapter;
import com.example.foodmanager.helpers.DatabaseHelper;
import com.example.foodmanager.models.DeleteDialogFragment;
import com.example.foodmanager.models.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import interfaces.deleteInterface;


public class MainActivity extends AppCompatActivity
        implements deleteInterface.Datable {

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
    Product selectedProduct;
    TextView caloriesText;
    TextView proteinsText;
    TextView fatsText;
    TextView carbohydratesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userList = (ListView) findViewById(R.id.userList);
        caloriesText = (TextView) findViewById(R.id.numberCal);
        proteinsText = (TextView) findViewById(R.id.numberProteins);
        fatsText = (TextView) findViewById(R.id.numberFats);
        carbohydratesText = (TextView) findViewById(R.id.numberCarbo);

        // передается id объекта
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            productId = extras.getLong("id_product");
        }

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
                selectedProduct = (Product) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Был выбран пункт " + selectedProduct.getName(),
                        Toast.LENGTH_SHORT).show();

                DeleteDialogFragment dialog = new DeleteDialogFragment();
                Bundle args = new Bundle();
                args.putString("selectedProduct", selectedProduct.getName());
                dialog.setArguments(args);
                dialog.show(getSupportFragmentManager(), "custom");
            }
        };
        userList.setOnItemClickListener(itemListener);
    }

    @Override
    public void remove(String name) {
        if (selectedProduct.getName().equals(name)) {
            productAdapter.remove(selectedProduct);
            productAdapter.notifyDataSetChanged();
        }

        double calories = 0;
        double proteins = 0;
        double fats = 0;
        double carbohydrates = 0;

        for (Product i : anotherProducts) {
            calories += i.getCalories();
            proteins += i.getProteins();
            fats += i.getFats();
            carbohydrates += i.getCarbohydrates();
        }
        caloriesText.setText(String.valueOf(roundAvoid(calories, 1)));
        proteinsText.setText(String.valueOf(roundAvoid(proteins, 1)));
        fatsText.setText(String.valueOf(roundAvoid(fats, 1)));
        carbohydratesText.setText(String.valueOf(roundAvoid(carbohydrates, 1)));

    }

    @Override
    protected void onResume() {
        super.onResume();

        String connectionsJSONString = getPreferences(MODE_PRIVATE).getString(KEY_CONNECTIONS, null);
        Type type = new TypeToken<List<Product>>() {
        }.getType();
        connectionsGet = new Gson().fromJson(connectionsJSONString, type);


        db = databaseHelper.open();
        //ListProduct lp = new ListProduct();
        anotherProducts = new ArrayList<>();

        productAdapter = new ProductAdapter(this, R.layout.list_of_product, anotherProducts);
        userList.setAdapter(productAdapter);
        if (connectionsGet != null) {
            productAdapter.addAll(connectionsGet);
            productAdapter.notifyDataSetChanged();
        }
        if (productWithWeight != null) {

            productAdapter.add(productWithWeight);
            productAdapter.notifyDataSetChanged();
        }

        double calories = 0;
        double proteins = 0;
        double fats = 0;
        double carbohydrates = 0;

        for (Product i : anotherProducts) {
            calories += i.getCalories();
            proteins += i.getProteins();
            fats += i.getFats();
            carbohydrates += i.getCarbohydrates();
        }
        caloriesText.setText(String.valueOf(roundAvoid(calories, 1)));
        proteinsText.setText(String.valueOf(roundAvoid(proteins,1)));
        fatsText.setText(String.valueOf(roundAvoid(fats,1)));
        carbohydratesText.setText(String.valueOf(roundAvoid(carbohydrates, 1)));
    }
    public static double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    @Override
    protected void onPause() {
        super.onPause();

        //  if (productWithWeight != null) {
        editor = getPreferences(MODE_PRIVATE).edit();
        connections = new ArrayList<>();
        if (anotherProducts != null) {
            connections.addAll(anotherProducts);
        }
        //connections.add(productWithWeight);
        String connectionsJSONString = new Gson().toJson(connections);
        editor.putString(KEY_CONNECTIONS, connectionsJSONString);
        editor.apply();
        //  }
        productWithWeight = null;
    }


    public void cleanProduct(View view) {
        editor = getPreferences(MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        if (connectionsGet != null) {
            connectionsGet.clear();
        }
        productWithWeight = null;
        productAdapter.clear();
        productAdapter.notifyDataSetChanged();
    }

    public void addProduct(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

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
