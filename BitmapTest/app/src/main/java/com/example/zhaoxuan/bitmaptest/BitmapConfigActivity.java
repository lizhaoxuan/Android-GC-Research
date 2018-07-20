package com.example.zhaoxuan.bitmaptest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BitmapConfigActivity extends AppCompatActivity {

    private Spinner configSpinner;
    private Spinner bitmapSpinner;
    private Button runBtn;
    private ImageView imageView;
    private TextView sizeTv;

    private String config = "ARGB_8888";
    private String bitmap = "p_500_519.png";

    private HashMap<String, Bitmap.Config> configMap = new HashMap<String, Bitmap.Config>() {
        {
            put("ARGB_8888", Bitmap.Config.ARGB_8888);
            put("ARGB_4444", Bitmap.Config.ARGB_4444);
            put("RGB_565", Bitmap.Config.RGB_565);
            put("ALPHA_8", Bitmap.Config.ALPHA_8);
        }
    };

    private List<String> configList = new ArrayList<String>() {
        {
            add("ARGB_8888");
            add("ARGB_4444");
            add("RGB_565");
            add("ALPHA_8");
            add("");
        }
    };

    private List<String> bitmapList = new ArrayList<String>() {
        {
            add("p_500_519.png");
            add("ico_50_88.png");
            add("ico_200_355_compress.png");
            add("ico_500_888_compress.png");
            add("ico_1080_1920_compress.png");
            add("dr_280_280.png");
            add("black.jpeg");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_config);

        runBtn = (Button) findViewById(R.id.run_btn);
        configSpinner = (Spinner) findViewById(R.id.config_spinner);
        bitmapSpinner = (Spinner) findViewById(R.id.bitmap_spinner);
        imageView = (ImageView) findViewById(R.id.image);
        sizeTv = (TextView) findViewById(R.id.size_tv);

        final ArrayAdapter<String> configAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, configList);
        configAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        configSpinner.setAdapter(configAdapter);

        ArrayAdapter<String> bitmapAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bitmapList);
        bitmapAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bitmapSpinner.setAdapter(bitmapAdapter);

        configSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                config = configList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bitmapSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bitmap = bitmapList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        runBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputStream stream = null;
                try {
                    stream = getAssets().open(bitmap);
                    BitmapFactory.Options op1 = new BitmapFactory.Options();
                    if (!config.equals("")) {
                        op1.inPreferredConfig = configMap.get(config);
                    }
                    Bitmap bm1 = BitmapFactory.decodeStream(stream, null, op1);
                    imageView.setImageBitmap(bm1);
                    sizeTv.setText("size:" + bm1.getByteCount());
                    Log.d("TAG", "config:" + config + "  bitmap:" + bitmap + "  size:" + bm1.getByteCount());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
