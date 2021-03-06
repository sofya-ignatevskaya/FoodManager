package com.example.foodmanager.helpers;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH; // полный путь к базе данных
    private static final String DB_NAME = "newdb.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных

    // таблица category
    public static final String tCategory = "category";
    public static final String idCategory = "_id";
    public static final String categoryKindId = "KindId";
    public static final String nameCategory = "CategoryName";

    //таблица Kind
    public static final String tKind = "kind";
    public static final String idKind = "_id";
    public static final String nameKind = "NameKind";

    //таблица Product
    public static final String tProduct = "product";
    public static final String idProduct = "_id";
    public static final String nameProduct = "ProductName";
    public static final String CategoryId = "CategoryId";
    public static final String productKindId = "KindId";
    public static final String Proteins = "Proteins";
    public static final String Fats = "Fats";
    public static final String Carbohydrates = "Uglevody";
    public static final String Calories = "Calories";
    public static final String Weight = "Weight";

    private Context myContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA);
        this.myContext = context;
        DB_PATH = context.getFilesDir().getPath() + DB_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    // копирование бд из assets в  DB_PATH
    public void create_db() {
        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            File file = new File(DB_PATH);
            if (!file.exists()) {
                this.getReadableDatabase();
                //получаем локальную бд как поток
                myInput = myContext.getAssets().open(DB_NAME);
                // Путь к новой бд
                String outFileName = DB_PATH;

                // Открываем пустую бд
                myOutput = new FileOutputStream(outFileName);

                // побайтово копируем данные
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
                myOutput.close();
                myInput.close();
            }
        } catch (IOException ex) {
            Log.d("DatabaseHelper", ex.getMessage());
        }
    }

    public SQLiteDatabase open() throws SQLException {
        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
