package com.lizhaoxuan.gcdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Main4Activity extends AppCompatActivity implements View.OnClickListener {

    private List<Bitmap> bitmapList;

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        init();
    }

    private void init() {
        bitmapList = new ArrayList<>();

        img = (ImageView) findViewById(R.id.img);
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
                btn3();
                break;
            case R.id.btn4:
                System.gc();
                break;
        }
    }

    /**
     * 增加内存
     */
    private void btn1() {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.mdip);
        bitmapList.add(bmp);
    }

    /**
     * 创建成员变量
     */
    private void btn2() {
        bitmapList = null;
    }

    /**
     * 再增加一个并显示
     */
    private void btn3() {
        img.setImageResource(R.drawable.mdip);
    }
}
