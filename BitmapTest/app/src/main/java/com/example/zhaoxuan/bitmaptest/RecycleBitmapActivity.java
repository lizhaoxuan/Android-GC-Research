package com.example.zhaoxuan.bitmaptest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class RecycleBitmapActivity extends AppCompatActivity {

    private ImageView imageView;

    private int index;
    private String[] bitmapPaths = new String[]{
            "welcome_1.png", "welcome_2.png", "welcome_3.png"};

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_bitmap);

        imageView = (ImageView) findViewById(R.id.imageView);

        findViewById(R.id.recycle_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recycle();
            }
        });
        findViewById(R.id.un_recycle_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unRecycle();
            }
        });

    }

    private void unRecycle() {
        byte[] welcome1 = Tool.readFile(this, bitmapPaths[index++]);
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(welcome1, 0, welcome1.length));
        if (index >= 3) {
            index = 0;
        }
    }

    private void recycle() {
        byte[] welcome1 = Tool.readFile(this, bitmapPaths[index++]);
        if (bitmap == null) {
            BitmapFactory.Options option1 = new BitmapFactory.Options();
            option1.inMutable = true;
            bitmap = BitmapFactory.decodeByteArray(welcome1, 0, welcome1.length, option1);
        } else {
            BitmapFactory.Options option1 = new BitmapFactory.Options();
            option1.inBitmap = bitmap;
            option1.inMutable = true;
            bitmap = BitmapFactory.decodeByteArray(welcome1, 0, welcome1.length, option1);
        }
        imageView.setImageBitmap(bitmap);
        if (index >= 3) {
            index = 0;
        }
    }
}
