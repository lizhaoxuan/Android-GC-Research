package com.lizhaoxuan.gcdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.IOException;

public class Main7Activity extends AppCompatActivity implements View.OnClickListener {

    private Bitmap bitmap;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        init();
    }

    private void init() {
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        img = (ImageView) findViewById(R.id.img);
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
//                System.gc();
                break;
            case R.id.btn4:
                btn4();
                break;
            case R.id.btn5:
                btn5();
                break;
        }
    }

    private void btn4() {
        Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.drawable_jpg_1);
        Bitmap b2 = BitmapFactory.decodeResource(getResources(), R.drawable.drawable_png_1);
        System.out.println("jpg getByteCount : " + b1.getByteCount() +
                " getRowBytes:" + b1.getRowBytes() + " getHeight:" +
                b1.getHeight() + " getWidth:" + b1.getWidth());

        System.out.println("png getByteCount : " + b2.getByteCount() +
                " getRowBytes:" + b2.getRowBytes() + " getHeight:" +
                b2.getHeight() + " getWidth:" + b2.getWidth());
    }

    private void btn3() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        System.out.println("density:" + density + "  densityDpi:" + densityDpi);
    }

    /**
     * 创建临时变量
     */
    private void btn1() {
//        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.mdip);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] data = ImageService.getImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494589188271&di=b8e4acebc99fa87e949ae32a2c32407a&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201705%2F04%2F153146zceecx8f8expi82w.jpg");
                    System.out.println("data size : " + data.length);
                    long start = System.currentTimeMillis();
                    final Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    long end = System.currentTimeMillis();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            img.setImageBitmap(bitmap);
                        }
                    });
                    System.out.println("startTime:" + start + " endTime:" + end + " use:" + (end - start));
                    System.out.println("bitmap size : " + bitmap.getByteCount());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    /**
     * 创建成员变量
     */
    private void btn2() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test_drawable);
        Log.d("TAG", "getByteCount : " + bitmap.getByteCount());
        Log.d("TAG", "getByteCount : " + bitmap.getAllocationByteCount());
        Log.d("TAG", "getByteCount : " + bitmap.getRowBytes() + " " + bitmap.getHeight());
    }

    private void btn5() {
        System.out.println("aaaaaaaaaa");
        System.out.println("img height：" + img.getHeight() + " width:" + img.getWidth());
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);

        System.out.println("height：" + wm.getDefaultDisplay().getHeight() + " width:" + wm.getDefaultDisplay().getWidth());

        Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.test_drawable);
        Bitmap b2 = BitmapFactory.decodeResource(getResources(), R.drawable.test_mdip);
        Bitmap b3 = BitmapFactory.decodeResource(getResources(), R.drawable.test_xhdpi);
        Bitmap b4 = BitmapFactory.decodeResource(getResources(), R.drawable.test_xxhdpi);
        System.out.println("b1 height：" + b1.getHeight() + " width:" + b1.getWidth());

        System.out.println("drawable getByteCount : " + b1.getByteCount() +
                " getRowBytes:" + b1.getRowBytes() + " getHeight:" +
                b1.getHeight() + " getWidth:" + b1.getWidth());

        System.out.println("mdip getByteCount : " + b2.getByteCount() +
                " getRowBytes:" + b2.getRowBytes() + " getHeight:" +
                b2.getHeight() + " getWidth:" + b2.getWidth());

        System.out.println("xhdip getByteCount : " + b3.getByteCount() +
                " getRowBytes:" + b3.getRowBytes() + " getHeight:" +
                b3.getHeight() + " getWidth:" + b3.getWidth());

        System.out.println("xxhdip getByteCount : " + b4.getByteCount() +
                " getRowBytes:" + b4.getRowBytes() + " getHeight:" +
                b3.getHeight() + " getWidth:" + b4.getWidth());

        bitmap = zoomImg(b4, img.getWidth(), img.getHeight());

        System.out.println("bitmap getByteCount : " + bitmap.getByteCount() +
                " getRowBytes:" + bitmap.getRowBytes() + " getHeight:" +
                bitmap.getHeight() + " getWidth:" + bitmap.getWidth());

        img.setImageBitmap(bitmap);
    }


    /**
     * 处理图片
     *
     * @param bm        所要转换的bitmap
     * @param newWidth  新的宽
     * @param newHeight 新的高
     * @return 指定宽高的bitmap
     */
    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

}
