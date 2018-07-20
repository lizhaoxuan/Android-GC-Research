package com.lizhaoxuan.gcdemo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class Main6Activity extends AppCompatActivity implements View.OnClickListener {

    private SoftReference<List<Bitmap>> softReference = new SoftReference<List<Bitmap>>(new ArrayList<Bitmap>());
    private WeakReference<List<Bitmap>> weakReference = new WeakReference<List<Bitmap>>(new ArrayList<Bitmap>());
    private List<SoftReference<Bitmap>> right = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        init();
    }

    private void init() {

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn1:
                outSide();
                showToast("outSide");
                break;
            case R.id.btn2:
                inSide();
                showToast("inSide");
                break;
            case R.id.btn3:
                listRefrence();
                break;
        }
    }

    private void outSide() {
        List<Bitmap> list = softReference.get();
        if (list == null) {
            list = new ArrayList<>();
            softReference = new SoftReference<>(list);
        }
        Resources res = getResources();
        for (int i = 0; i < 10; i++) {
            Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.mdip);
            list.add(bmp);
        }

        Log.d("TAG", "out list size : " + list.size());
    }

    private void inSide() {
        List<Bitmap> list = null;
        Resources res = getResources();
        for (int i = 0; i < 10; i++) {
            Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.mdip);
            list = weakReference.get();
            if (list == null) {
                list = new ArrayList<>();
                weakReference = new WeakReference<>(list);
            }
            list.add(bmp);
        }

        Log.d("TAG", "in list size : " + list.size());
    }

    private void listRefrence(){
        Resources res = getResources();
        for (int i = 0; i < 10; i++) {
            Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.mdip);
            right.add(new SoftReference<>(bmp));
        }
    }

    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }


}
