package com.example.foodmanager.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodmanager.R;

public class NormActivity extends AppCompatActivity {

    Spinner genderSpinner;
    Spinner sportSpinner;
    EditText weightEditText;
    EditText heightEditText;
    EditText ageEditText;
    TextView normEditText;

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
        normEditText= (TextView) findViewById(R.id.normaValue);

        genderPosition = genderSpinner.getSelectedItemPosition();
        sportPosition = sportSpinner.getSelectedItemPosition();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {

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
            normEditText.setText(String.valueOf(normCalories));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не все поля заполнены",
                    Toast.LENGTH_SHORT).show();
        }
    }

        public void installButton(View view){

        }


}
