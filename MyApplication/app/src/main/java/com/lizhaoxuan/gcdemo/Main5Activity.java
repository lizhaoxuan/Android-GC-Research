package com.lizhaoxuan.gcdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Main5Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        init();
    }

    private void init() {
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn1:
                btn1();
                break;
            case R.id.btn2:
                System.gc();
                break;
        }
    }

    /**
     * 循环内创建对象
     */
    private void btn1() {
        for (int i = 0; i < 20; i++) {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.test_100);
        }
    }


}
