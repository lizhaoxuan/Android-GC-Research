package com.example.zhaoxuan.bitmaptest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

/**
 * Created by lizhaoxuan on 2017/6/1.
 */

public class TestTool {

    private TestTool() {
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void testInBitmap(Context context) {

        Bitmap bitmap = BitmapFactory.decodeStream(getInputStream(context, "ico_1080_1920.png"));
        Log.d("TAG", "start bitmap getAllocationByteCount:" + bitmap.getAllocationByteCount() + " getByteCount:" + bitmap.getByteCount());
        BitmapFactory.Options option1 = testInBitmapOptions(1);
        option1.inBitmap = bitmap;
        Bitmap bitmap2 = BitmapFactory.decodeStream(getInputStream(context, "ico_50_88.png"), null, option1);
        Log.d("TAG", "two bitmap getAllocationByteCount:" + bitmap2.getAllocationByteCount() + " getByteCount:" + bitmap2.getByteCount());

        BitmapFactory.Options option2 = testInBitmapOptions(1);
        option2.inBitmap = bitmap2;
        Bitmap bitmap3 = BitmapFactory.decodeStream(getInputStream(context, "ico_500_888.png"), null, option2);
        Log.d("TAG", "three bitmap getAllocationByteCount:" + bitmap3.getAllocationByteCount() + " getByteCount:" + bitmap3.getByteCount());

    }

    private static InputStream getInputStream(Context context, String path) {
        try {
            return context.getResources().getAssets().open(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static BitmapFactory.Options testInBitmapOptions(int inSampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        return options;
    }

    public static Bitmap adjustPhotoRotation(Bitmap bm, final int orientationDegree) {
        Matrix m = new Matrix();
        m.setRotate(orientationDegree, (float) bm.getWidth(), (float) bm.getHeight());

        try {
            Bitmap bm1 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
            return bm1;
        } catch (OutOfMemoryError ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void testSampleSize(Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap;
        try {
            BitmapFactory.decodeStream(context.getAssets().open("ico_200_355.png"), null, options);
            Log.d("TAG", "old Height: " + options.outHeight + "  old Width:" + options.outWidth);
        } catch (IOException e) {
            e.printStackTrace();
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = 3;
        try {
            bitmap = BitmapFactory.decodeStream(context.getAssets().open("ico_200_355.png"), null, options);
            Log.d("TAG", "new Height: " + bitmap.getHeight() + "  new Width:" + bitmap.getWidth());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testRecycle(Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getAssets().open("ico_200_355.png"), null, options);
            Log.d("TAG", "ARGB_8888: " + bitmap.getByteCount());
        } catch (IOException e) {
            e.printStackTrace();
        }
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inBitmap = bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(context.getAssets().open("ico_200_355.png"), null, options);
            Log.d("TAG", "RGB_565: " + bitmap.getByteCount());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testDecode(Context context) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = BitmapFactory.decodeStream(context.getAssets().open("p_500_519.png"), null, options);
            Log.d("TAG", "1.bitmap:" + bitmap.getByteCount());
            options.inPreferredConfig = Bitmap.Config.ALPHA_8;
            options.inBitmap = bitmap;
            bitmap = BitmapFactory.decodeStream(context.getAssets().open("p_500_519.png"), null, options);
            Log.d("TAG", "2.bitmap:" + bitmap.getByteCount());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testStreamByte(Context context) {
        Formatter formatter = new Formatter(System.out);

        List<byte[]> byteList = new ArrayList<>();

        for (int i = 1; i <= 32; i++) {
            byteList.add(Tool.readFile(context, "jpg/" + i + ".png"));
        }

        for (int i = 1; i < 32; i++) {
            long byteStart;
            long byteEnd;
            byte[] bytes = byteList.get(i);
            byteStart = System.currentTimeMillis();
            BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            byteEnd = System.currentTimeMillis();
//            Log.d("TAG", "decodeByteArray:" + (byteEnd - byteStart));

            long streamStart = 0, streamEnd = 0;
            try {
                streamStart = System.currentTimeMillis();
                BitmapFactory.decodeStream(context.getAssets().open("jpg/" + i + ".png"));
                streamEnd = System.currentTimeMillis();
//                Log.d("TAG", "decodeStream:" + (streamEnd - streamStart));
            } catch (Exception e) {
                e.printStackTrace();
            }
            formatter.format("size: %-10s  decodeByteArray:%5d  decodeStream:%10s \n", bytes.length, (byteEnd - byteStart), (streamEnd - streamStart));
//            Log.d("TAG", "size : " + bytes.length + "   decodeByteArray:" + (byteEnd - byteStart) + "   decodeStream:" + (streamEnd - streamStart));
        }
    }
}
