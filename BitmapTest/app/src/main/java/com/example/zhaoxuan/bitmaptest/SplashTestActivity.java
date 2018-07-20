package com.example.zhaoxuan.bitmaptest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;

public class SplashTestActivity extends AppCompatActivity {

    private Button btn1, btn2, btn3;
    private Bitmap bitmap1, bitmap2, bitmap3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_test);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                bitmap1 = BitmapFactory.decodeStream(getInputStream("ico_200_355.png"), null, options);
                Log.d("TAG", "start bitmap getHeight:" + bitmap1.getHeight() + " getWidth:" + bitmap1.getWidth());
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                bitmap2 = BitmapFactory.decodeStream(getInputStream("ico_200_355.png"), null, options);
                Log.d("TAG", "TWO bitmap getHeight:" + bitmap2.getHeight() + " getWidth:" + bitmap2.getWidth());
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inBitmap = null;
                options.inMutable = true;
                bitmap3 = BitmapFactory.decodeStream(getInputStream("ico_500_888.png"), null, options);
                Log.d("TAG", "THREE bitmap getAllocationByteCount:" + bitmap3.getAllocationByteCount() + " getByteCount:" + bitmap3.getByteCount());
            }
        });

    }

    private InputStream getInputStream(String path) {
        try {
            return getResources().getAssets().open(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
