package com.example.zhaoxuan.bitmaptest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class LoadSpeedActivity extends AppCompatActivity {

    TextView textView1, textView2, textView3, textView4, textView5;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_speed);

        textView1 = (TextView) findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.text2);
        textView3 = (TextView) findViewById(R.id.text3);
        textView4 = (TextView) findViewById(R.id.text4);
        textView5 = (TextView) findViewById(R.id.text5);
        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                run1();
                run2();
                run3();
            }
        });
    }


    private void run1() {
        long start = System.currentTimeMillis();
        BitmapFactory.decodeResource(getResources(), R.drawable.ico_splash);
        long end = System.currentTimeMillis();

        textView1.setText("资源文件加载Bitmap 耗时：" + (end - start) + "秒");
        Log.d("TAG", "资源文件加载Bitmap 耗时：" + (end - start) + "秒");
    }

    private void run2() {
        try {
            long start = System.currentTimeMillis();
            BitmapFactory.decodeStream(getResources().getAssets().open("ico_splash.png"));
            long end = System.currentTimeMillis();

            textView2.setText("decodeStream加载本地图片 耗时：" + (end - start) + "秒");
            Log.d("TAG", "decodeStream加载本地图片 耗时：" + (end - start) + "秒");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void run3() {
        long start = System.currentTimeMillis();
        byte[] bytes = Tool.readFile(this, "ico_splash.png");
        long end = System.currentTimeMillis();
        run4(start, bytes);
        textView3.setText("读取本地图片到byte[] 耗时：" + (end - start) + "秒");
        Log.d("TAG", "读取本地图片到byte[] 耗时：" + (end - start) + "秒");
    }

    private void run4(long realStart, byte[] bytes) {
        long start = System.currentTimeMillis();
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        long end = System.currentTimeMillis();

        textView4.setText("byte[] to Bitmap 耗时：" + (end - start) + "秒");
        Log.d("TAG", "byte[] to Bitmap 耗时：" + (end - start) + "秒");
        textView4.setText("读取本地图片到byte[] 再到Bitmap 耗时：" + (end - realStart) + "秒");
        Log.d("TAG", "读取本地图片到byte[] 再到Bitmap 耗时：" + (end - realStart) + "秒");

    }
}
