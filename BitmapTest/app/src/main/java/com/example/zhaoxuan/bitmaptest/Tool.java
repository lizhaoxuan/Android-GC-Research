package com.example.zhaoxuan.bitmaptest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lizhaoxuan on 2017/5/20.
 */

public class Tool {

    private Tool() {
    }

    public static byte[] readFile(Context context, String fileName) {
        try {
            //得到资源中的asset数据流
            InputStream in = context.getResources().getAssets().open(fileName);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            in.close();
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int heightRatio = Math.round((float) height / (float) reqHeight);
            int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    private static boolean doesExisted(String filepath) {
        return TextUtils.isEmpty(filepath) ? false : doesExisted(new File(filepath));
    }

    private static boolean doesExisted(File file) {
        return file != null && file.exists();
    }

    public static byte[] getBitmapBytes(String picfile, int reqWidth, int reqHeight, float maxSize) throws IOException {
        if (reqWidth <= 0) {
            throw new IllegalArgumentException("size must be greater than 0!");
        } else if (!doesExisted(picfile)) {
            throw new FileNotFoundException("picfile == null?" + (picfile == null ? "null" : picfile));
        } else if (!verifyBitmap(picfile)) {
            throw new IOException("解析位图失败");
        } else {
            FileInputStream input = new FileInputStream(picfile);
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(input, (Rect) null, opts);
            input.close();
            boolean rate = true;
            int rate1 = calculateInSampleSize(opts, reqWidth, reqHeight);
            opts.inSampleSize = rate1 <= 1 ? 1 : rate1;
            opts.inJustDecodeBounds = false;
            Bitmap temp = getDecodeBitmap(picfile, opts);
            if (null == temp) {
                return null;
            } else {
                int degree = readPictureDegree(picfile);
                if (degree != 0) {
                    temp = rotateBitmap(temp, degree);
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                temp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                float size = (float) baos.toByteArray().length / 1024.0F;

                int quality;
                for (quality = 85; size > maxSize && quality > 30; size = (float) baos.toByteArray().length / 1024.0F) {
                    baos.reset();
                    temp.compress(Bitmap.CompressFormat.JPEG, quality, baos);
                    quality -= 5;
                }

                Log.d("Less", "图片宽x高：" + temp.getWidth() + "x" + temp.getHeight());
                Log.d("Less", "压缩后  质量：" + size + "kb quality = " + quality);
                return baos.toByteArray();
            }
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate((float) degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
        }

        return bitmap;
    }

    private static boolean verifyBitmap(String path) {
        try {
            return verifyBitmap((InputStream) (new FileInputStream(path)));
        } catch (FileNotFoundException var2) {
            var2.printStackTrace();
            return false;
        }
    }

    private static boolean verifyBitmap(InputStream input) {
        if (input == null) {
            return false;
        } else {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Object input1 = input instanceof BufferedInputStream ? input : new BufferedInputStream(input);
            BitmapFactory.decodeStream((InputStream) input1, (Rect) null, options);

            try {
                ((InputStream) input1).close();
            } catch (IOException var3) {
                var3.printStackTrace();
            }

            return options.outHeight > 0 && options.outWidth > 0;
        }
    }

    public static int readPictureDegree(String path) {
        short degree = 0;

        try {
            ExifInterface e = new ExifInterface(path);
            int orientation = e.getAttributeInt("Orientation", 1);
            switch (orientation) {
                case 3:
                    degree = 180;
                    break;
                case 6:
                    degree = 90;
                    break;
                case 8:
                    degree = 270;
            }
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        return degree;
    }

    private static Bitmap getDecodeBitmap(String bmpFile, BitmapFactory.Options opts) {
        BitmapFactory.Options optsTmp = opts;
        if (opts == null) {
            optsTmp = new BitmapFactory.Options();
            optsTmp.inSampleSize = 1;
        }

        Bitmap bmp = null;
        FileInputStream input = null;
        boolean maxTrial = true;
        int i = 0;

        while (i < 5) {
            try {
                input = new FileInputStream(bmpFile);
                bmp = BitmapFactory.decodeStream(input, (Rect) null, opts);

                try {
                    input.close();
                } catch (IOException var10) {
                    var10.printStackTrace();
                }
                break;
            } catch (OutOfMemoryError var11) {
                var11.printStackTrace();
                optsTmp.inSampleSize *= 2;

                try {
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException var9) {
                    var9.printStackTrace();
                }

                ++i;
            } catch (FileNotFoundException var12) {
                break;
            }
        }

        return bmp;
    }

}
