package com.example.zhaoxuan.bitmaptest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by lizhaoxuan on 2017/6/1.
 */

public class RecycleTool {
    private static Bitmap photoBitmap;

    private RecycleTool() {
    }

    public static void getImageView(final ImageView imageView, final int srcId) {
        imageView.post(new Runnable() {
            @Override
            public void run() {
                BitmapFactory.Options options = getOptions(imageView, imageView.getResources().openRawResource(srcId));
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inMutable = true;
                if (photoBitmap != null) {
                    options.inBitmap = photoBitmap;
                }
                try {
                    photoBitmap = BitmapFactory.decodeStream(imageView.getResources().openRawResource(srcId), null, options);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    options.inBitmap = null;
                    photoBitmap = BitmapFactory.decodeStream(imageView.getResources().openRawResource(srcId), null, options);
                }
                Log.d("TAG", "size:" + photoBitmap.getByteCount());
                imageView.setImageBitmap(photoBitmap);
            }
        });
    }

    public static void getImageView(final ImageView imageView, final Uri path) {
        imageView.post(new Runnable() {
            @Override
            public void run() {
                BitmapFactory.Options options = null;
                try {
                    options = getOptions(imageView, imageView.getContext().getContentResolver().openInputStream(path));
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    options.inMutable = true;
                    if (photoBitmap != null) {
                        options.inBitmap = photoBitmap;
                    }
                    try {
                        photoBitmap = BitmapFactory.decodeStream(imageView.getContext().getContentResolver().openInputStream(path), null, options);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                        options.inBitmap = null;
                        photoBitmap = BitmapFactory.decodeStream(imageView.getContext().getContentResolver().openInputStream(path), null, options);
                    }
                    Log.d("TAG", "size:" + photoBitmap.getByteCount());
                    imageView.setImageBitmap(photoBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private static BitmapFactory.Options getOptions(ImageView imageView, InputStream inputStream) {
        int width = imageView.getWidth();
        int height = imageView.getHeight();
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (width == 0) {
            return options;
        }
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        Log.d("TAG", "width:" + width + " height:" + height + " outWidth:" + outWidth + " outHeight:" + outHeight);
        int scale = 1;
        while (true) {
            if (outWidth < width || outHeight < height) {
                break;
            }
            outWidth /= 2;
            outHeight /= 2;
            scale *= 2;
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = scale;
        return options;
    }


}
