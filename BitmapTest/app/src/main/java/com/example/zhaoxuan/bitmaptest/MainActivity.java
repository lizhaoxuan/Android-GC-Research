package com.example.zhaoxuan.bitmaptest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    TextView textView;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        TestTool.testSampleSize(this);
//        TestTool.testRecycle(this);
//        TestTool.testDecode(this);
//        TestTool.testStreamByte(this);


        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btn10).setOnClickListener(this);
        findViewById(R.id.btn11).setOnClickListener(this);
        findViewById(R.id.btn12).setOnClickListener(this);

        textView = (TextView) findViewById(R.id.text);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn1:
                startActivity(ByteReplaceBitmapActivity.class);
                break;
            case R.id.btn2:
                startActivity(BitmapConfigActivity.class);
                break;
            case R.id.btn3:
                startActivity(DecodeTestActivity.class);
                break;
            case R.id.btn4:
                startActivity(RecycleBitmapActivity.class);
                break;
            case R.id.btn5:
                startActivity(SplashTestActivity.class);
                break;
            case R.id.btn6:
                startActivity(OldPhotoActivity.class);
                break;
            case R.id.btn7:
                startActivity(NewTakePhotoActivity.class);
                break;
            case R.id.btn8:
                startActivity(DrawableBitmapActivity.class);
                break;
            case R.id.btn9:
                startActivity(ListViewActivity.class);
                break;
            case R.id.btn10:
                startActivity(RecycleListActivity.class);
                break;
            case R.id.btn11:
                startActivity(LoadSpeedActivity.class);
                break;
            case R.id.btn12:
                DisplayMetrics metric = new DisplayMetrics();
                double densityDpi = metric.densityDpi;

                textView.setText("densityDpi:" + densityDpi);
                startActivity(TestChoiceActivity.class);
                break;
        }
    }

    private void startActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }

}
