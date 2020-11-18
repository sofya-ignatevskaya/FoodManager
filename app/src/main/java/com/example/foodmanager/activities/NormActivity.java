package com.example.foodmanager.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodmanager.R;
import com.example.foodmanager.adapters.DatabaseAdapter;

public class NormActivity extends AppCompatActivity {

    Spinner genderSpinner;
    Spinner sportSpinner;
    EditText weightEditText;
    EditText heightEditText;
    EditText ageEditText;
    TextView normEditText;
    TextView normTextView;
    Button installButton;


    int genderPosition;
    int sportPosition;

    double normCalories = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_norm);

        genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        sportSpinner = (Spinner) findViewById(R.id.sportSpinner);
        weightEditText = (EditText) findViewById(R.id.weightValue);
        heightEditText = (EditText) findViewById(R.id.heightValue);
        ageEditText = (EditText) findViewById(R.id.ageValue);
        normEditText = (TextView) findViewById(R.id.normaValue);
        normTextView = (TextView) findViewById(R.id.normaText);
        installButton = (Button) findViewById(R.id.installButton);
    }

    public void calculateButton(View view) {
        try {
            //получение позиции
            genderPosition = genderSpinner.getSelectedItemPosition();
            sportPosition = sportSpinner.getSelectedItemPosition();
            //получение данных
            double weight = Double.parseDouble(weightEditText.getText().toString());
            double height = Double.parseDouble(heightEditText.getText().toString());
            double age = Double.parseDouble(ageEditText.getText().toString());

            normCalories = 10 * weight + 6.25 * height - 5 * age;

            switch (genderPosition) {
                case 0:
                    normCalories -= 161;
                    break;
                case 1:
                    normCalories += 5;
                    break;
                default:
                    break;
            }

            switch (sportPosition) {
                case 0:
                    normCalories *= 1.2;
                    break;
                case 1:
                    normCalories *= 1.375;
                    break;
                case 2:
                    normCalories *= 1.55;
                    break;
                case 3:
                    normCalories *= 1.725;
                    break;
                case 4:
                    normCalories *= 1.9;
                    break;
                default:
                    break;
            }
            DatabaseAdapter da = new DatabaseAdapter(this);
            normEditText.setText(String.valueOf(da.roundAvoid(normCalories, 0)));

            //включение видимости
            String caloriesVisibility = normEditText.getText().toString();
            if (!caloriesVisibility.equals("0")) {
                normTextView.setVisibility(View.VISIBLE);
                normEditText.setVisibility(View.VISIBLE);
                installButton.setVisibility(View.VISIBLE);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не все поля заполнены",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void installButton(View view){
        // переход к главной activity
        Intent intent = new Intent(this, MainActivity.class);
        // передача данных для другого окна
        intent.putExtra("value_of_normaCalories", normEditText.getText().toString());
        startActivity(intent);
    }

}
