package com.example.coronavirusapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.NumberPicker;

public class UpdateHealthStatus extends AppCompatActivity {

    NumberPicker temperature1,temperature2;
    int temperatureValue1, temperatureValue2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.holo_red_light, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.holo_red_light));
        }
        //hides action bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_update_health_status);

        temperature1 = findViewById(R.id.temperature1);
        temperature1.setMinValue(95);
        temperature1.setMaxValue(107);
        temperature1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                temperatureValue1 = picker.getValue();
            }
        });

        temperature2 = findViewById(R.id.temperature2);
        temperature2.setMinValue(0);
        temperature2.setMaxValue(9);
        temperature2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                temperatureValue2 = picker.getValue();
            }
        });


    }
}
