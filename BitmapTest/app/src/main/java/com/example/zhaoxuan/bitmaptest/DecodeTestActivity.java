package com.example.zhaoxuan.bitmaptest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DecodeTestActivity extends AppCompatActivity {

    private Spinner bitmapSpinner;
    private Button streamBtn, resourceBtn;

    private String bitmapPath = "p_500_519.png";

    private List<String> bitmapList = new ArrayList<String>() {
        {
            add("p_500_519.png");
            add("ico_50_88.png");
            add("ico_200_355_compress.png");
            add("ico_500_888_compress.png");
            add("ico_1080_1920_compress.png");
            add("dr_280_280.png");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decode_test);
        bitmapSpinner = (Spinner) findViewById(R.id.bitmap_spinner);
        streamBtn = (Button) findViewById(R.id.stream_btn);
        resourceBtn = (Button) findViewById(R.id.resource_btn);

        ArrayAdapter<String> bitmapAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bitmapList);
        bitmapAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bitmapSpinner.setAdapter(bitmapAdapter);

        bitmapSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bitmapPath = bitmapList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        streamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    long start = System.currentTimeMillis();
                    Bitmap bitmap = BitmapFactory.decodeStream(getAssets().open(bitmapPath));
                    long end = System.currentTimeMillis();
                    Log.d("TAG", "decodeStream size:" + bitmap.getByteCount() + " use:" + (end - start));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                byte[] bytes = Tool.readFile(DecodeTestActivity.this, bitmapPath);
                long start = System.currentTimeMillis();
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                long end = System.currentTimeMillis();
                Log.d("TAG", "decodeByteArray size:" + bitmap.getByteCount() + " use:" + (end - start));
            }
        });

        resourceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decodeResource();
                decodeResource2();
                decodeResource3();
            }
        });
    }

    private void decodeResource() {
        long start = System.currentTimeMillis();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.t500_519_m);
        long end = System.currentTimeMillis();
        Log.d("TAG", "t500_519_m size:" + bitmap.getByteCount() + " use:" + (end - start));
    }

    private void decodeResource2() {
        long start = System.currentTimeMillis();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.t500_519_xh);
        long end = System.currentTimeMillis();
        Log.d("TAG", "t500_519_xh size:" + bitmap.getByteCount() + " use:" + (end - start));
    }

    private void decodeResource3() {
        long start = System.currentTimeMillis();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.t500_519_xxh);
        long end = System.currentTimeMillis();
        Log.d("TAG", "t500_519_xxh size:" + bitmap.getByteCount() + " use:" + (end - start));
    }
}
