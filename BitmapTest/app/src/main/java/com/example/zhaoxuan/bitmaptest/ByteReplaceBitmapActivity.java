package com.example.zhaoxuan.bitmaptest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ByteReplaceBitmapActivity extends AppCompatActivity {

    private Button btn;
    private ListView listView;

    private byte[][] bytes;
    private Bitmap[] bitmaps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_byte_replace_bitmap);
        listView = (ListView) findViewById(R.id.list_view);

        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redaImage();
            }
        });

    }

    private void redaImage() {
        String[] path = new String[]{"23.webp", "img.jpeg", "ico_50_88.png", "ico_50_88_compress.png",
                "ico_200_355.png", "ico_200_355_compress.png",
                "ico_500_888.png", "ico_500_888_compress.png",
                "ico_1080_1920.png", "ico_1080_1920_compress.png",
                "un_compress.webp", "compress.webp"};


        int length = path.length;
        String[] context = new String[length];
        bytes = new byte[length][];
        bitmaps = new Bitmap[length];

        for (int i = 0; i < length; i++) {
            bytes[i] = Tool.readFile(this, path[i]);
            long start = System.currentTimeMillis();
            bitmaps[i] = BitmapFactory.decodeByteArray(bytes[i], 0, bytes[i].length);
            long end = System.currentTimeMillis();
            context[i] = (path[i] + "  bytes:" + bytes[i].length
                    + "b ≈ " + bytes[i].length / 1024 + "kb  bitmap:" + bitmaps[i].getByteCount()
                    + "b ≈ " + bitmaps[i].getByteCount() / 1024 + "kb  use:" + String.valueOf(end - start)) + "ms";
            Log.d("TAG", context[i]);
            Log.d("TAG_1", "use:" + String.valueOf(end - start) + "ms");
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, context);
        listView.setAdapter(arrayAdapter);
    }
}
