package com.example.zhaoxuan.bitmaptest;

import android.content.Intent;
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

public class NewTakePhotoActivity extends AppCompatActivity {

    private ImageView photoImg;
    private Button takeBtn;

    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_take_photo);
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File outputImage = new File(path, "oldTakePhoto.jpg");
        fileUri = Uri.fromFile(outputImage);
        photoImg = (ImageView) findViewById(R.id.photo_img);
        takeBtn = (Button) findViewById(R.id.take_btn);
        RecycleTool.getImageView(photoImg, R.drawable.example);


        takeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(captureIntent, 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("TAG", "resultCode:" + resultCode);
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        RecycleTool.getImageView(photoImg, fileUri);
    }
}
