package com.example.zhaoxuan.bitmaptest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class OldPhotoActivity extends AppCompatActivity {

    private ImageView photoImg;
    private Button takeBtn;

    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        photoImg = (ImageView) findViewById(R.id.photo_img);
        takeBtn = (Button) findViewById(R.id.take_btn);

        photoImg.setImageResource(R.drawable.example);
        takeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                File outputImage = new File(path, "oldTakePhoto.jpg");
                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = Uri.fromFile(outputImage);
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(captureIntent, 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("TAG", "resultCode:" + resultCode);
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(fileUri), null, o);

            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < 1024
                        || height_tmp / 2 < 768)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            o.inJustDecodeBounds = false;
            o.inSampleSize = scale;
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(fileUri), null, o);
            photoImg.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
