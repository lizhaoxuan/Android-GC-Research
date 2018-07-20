package com.example.zhaoxuan.bitmaptest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class DrawableBitmapActivity extends AppCompatActivity {

    private ImageView drawableImg, bitmapImg, drawableOnceBtn;

    private List<Drawable> drawableList = new ArrayList<>();
    private List<Bitmap> bitmapList = new ArrayList<>();

    int[] ids = new int[]{R.drawable.ico_splash, R.drawable.example, R.drawable.t500_519_m, R.drawable.t500_519_xh};
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_bitmap);

        findViewById(R.id.drawable_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < 20; i++) {
                    Drawable dr2 = getResources().getDrawable(R.drawable.ico_splash);
                    drawableList.add(dr2);
                }
            }
        });
        findViewById(R.id.bitmap_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.ico_splash));
                bitmapList.add(bitmap);
            }
        });

        findViewById(R.id.drawable_once_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index >= 4) {
                    index = 0;
                }
                drawableList.add(getResources().getDrawable(ids[index++]));
            }
        });
    }

    private void test(int id) {
        Bitmap bitmap = BitmapFactory.decodeStream(getResources().openRawResource(id));
    }
}
