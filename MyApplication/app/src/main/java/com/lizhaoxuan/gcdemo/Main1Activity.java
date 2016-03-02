package com.lizhaoxuan.gcdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Main1Activity extends AppCompatActivity implements View.OnClickListener {

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        init();
    }

    private void init() {
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn1:
                btn1();
                break;
            case R.id.btn2:
                btn2();
                break;
            case R.id.btn3:
                System.gc();
                break;
            case R.id.btn4:
                bitmap = null;
                break;
        }
    }

    /**
     * 创建临时变量
     */
    private void btn1() {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.big_254);
    }

    /**
     * 创建成员变量
     */
    private void btn2() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.big_254);
    }
}
