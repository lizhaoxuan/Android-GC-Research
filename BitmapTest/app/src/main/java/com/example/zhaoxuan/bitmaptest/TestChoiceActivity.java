package com.example.zhaoxuan.bitmaptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class TestChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_choice);


        TextView textView = (TextView) findViewById(R.id.text);
        DisplayMetrics metric = new DisplayMetrics();
        double densityDpi = metric.densityDpi;

        textView.setText("densityDpi:" + densityDpi);
    }
}
