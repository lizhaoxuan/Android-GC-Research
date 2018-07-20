package com.lizhaoxuan.gcdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main8Activity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1;
    private ImageView img;
    private TextView text;

    private List<byte[]> bytes;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        init();
    }

    private void init() {

        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        img = (ImageView) findViewById(R.id.img);
        text = (TextView) findViewById(R.id.text);

        loadImg();
    }

    private void loadImg() {
        btn1.setEnabled(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                bytes = new ArrayList<>();
                String url1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494589188271&di=b8e4acebc99fa87e949ae32a2c32407a&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201705%2F04%2F153146zceecx8f8expi82w.jpg";
                String url2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495109507565&di=c9991ddddd9497bea2fb32358f163931&imgtype=0&src=http%3A%2F%2Fwww.pp3.cn%2Fuploads%2F201502%2F2015021111.jpg";
                String url3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495109507565&di=86ad6e97f87e4632715eb03efa52f948&imgtype=0&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F140325%2F318763-14032513392460.jpg";
                String url4 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495109507567&di=f2f55ed34739bfcc64094427e61e6dac&imgtype=0&src=http%3A%2F%2Fpic.yesky.com%2FuploadImages%2F2015%2F131%2F15%2F2O4G259J20K3.jpg";
                try {
                    bytes.add(ImageService.getImage(url1));
                    bytes.add(ImageService.getImage(url2));
                    bytes.add(ImageService.getImage(url3));
                    bytes.add(ImageService.getImage(url4));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btn1.setEnabled(true);
                    }
                });
            }
        }).start();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn1:
                btn1();
                break;
        }
    }

    private void btn1() {
        if (index > 3) {
            index = 0;
        }
        byte[] data = bytes.get(index);
        long start = System.currentTimeMillis();
        final Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        img.setImageBitmap(bitmap);
        long end = System.currentTimeMillis();
        text.setText("bitmap size:" + bitmap.getByteCount() + "  use:" + (end - start));
        System.out.println("bitmap size:" + bitmap.getByteCount() + "  use:" + (end - start));
        index++;
    }
}
