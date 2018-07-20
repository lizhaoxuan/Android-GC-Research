package com.example.zhaoxuan.bitmaptest;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class StreamByteActivity extends AppCompatActivity {

    private List<byte[]> byteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_byte);
        for (int i = 0; i <= 32; i++) {
            byteList.add(Tool.readFile(this, i + ".png"));
        }
        for (int i = 0; i <= 32; i++) {
            byte[] bytes = byteList.get(i);
            long byteStart = System.currentTimeMillis();
            BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            long byteEnd = System.currentTimeMillis();
            Log.d("TAG", "decodeByteArray:" + (byteEnd - byteStart));

            try {
                long streamStart = System.currentTimeMillis();
                BitmapFactory.decodeStream(getAssets().open(i + ".png"));
                long streamEnd = System.currentTimeMillis();
                Log.d("TAG", "decodeStream:" + (streamEnd - streamStart));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
