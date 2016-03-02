package com.lizhaoxuan.gcdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Android GC 调研
 * 1.手动调用GC方法，查看GC时机
 * 2.空Activity的内存大小及GC时机
 * 3.非空大Activity的销毁时机
 * 4.濒临临界值情况下，对象=NULL，再次消耗内存，是否会溢出
 * 5.循环内创建对象是否会引起内存抖动
 * 6.软引用\弱引用 ， 循环内外的GC差异
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);

    }


    /**
     * TRIM_MEMORY_COMPLETE：内存不足，并且该进程在后台进程列表最后一个，马上就要被清理
     * TRIM_MEMORY_MODERATE：内存不足，并且该进程在后台进程列表的中部。
     * TRIM_MEMORY_BACKGROUND：内存不足，并且该进程是后台进程。
     * TRIM_MEMORY_UI_HIDDEN：内存不足，并且该进程的UI已经不可见了。
     * TRIM_MEMORY_RUNNING_CRITICAL：内存不足(后台进程不足3个)，并且该进程优先级比较高，需要清理内存
     * TRIM_MEMORY_RUNNING_LOW：内存不足(后台进程不足5个)，并且该进程优先级比较高，需要清理内存
     * TRIM_MEMORY_RUNNING_MODERATE：内存不足(后台进程超过5个)，并且该进程优先级比较高，需要清理内存
     */
    @Override
    public void onTrimMemory(int level) {
        switch (level) {
            case TRIM_MEMORY_COMPLETE:
                showToast(TRIM_MEMORY_COMPLETE +
                        "内存不足，并且该进程在后台进程列表最后一个，马上就要被清理");
                break;
            case TRIM_MEMORY_MODERATE:
                showToast(TRIM_MEMORY_MODERATE +
                        "内存不足，并且该进程在后台进程列表的中部。");
                break;
            case TRIM_MEMORY_BACKGROUND:
                showToast(TRIM_MEMORY_BACKGROUND +
                        "内存不足，并且该进程是后台进程。");
                break;
            case TRIM_MEMORY_UI_HIDDEN:
                showToast(TRIM_MEMORY_UI_HIDDEN +
                        "内存不足，并且该进程的UI已经不可见了。");
                break;
            case TRIM_MEMORY_RUNNING_CRITICAL:
                showToast(TRIM_MEMORY_RUNNING_CRITICAL +
                        "内存不足(后台进程不足3个)，并且该进程优先级比较高，需要清理内存");
                break;
            case TRIM_MEMORY_RUNNING_LOW:
                showToast(TRIM_MEMORY_RUNNING_LOW +
                        "内存不足(后台进程不足5个)，并且该进程优先级比较高，需要清理内存");
                break;
            case TRIM_MEMORY_RUNNING_MODERATE:
                showToast(TRIM_MEMORY_RUNNING_MODERATE +
                        "内存不足(后台进程超过5个)，并且该进程优先级比较高，需要清理内存");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn1:
                startActivity(Main1Activity.class);
                break;
            case R.id.btn2:
                startActivity(Main2Activity.class);
                break;
            case R.id.btn3:
                startActivity(Main3Activity.class);
                break;
            case R.id.btn4:
                startActivity(Main4Activity.class);
                break;
            case R.id.btn5:
                startActivity(Main5Activity.class);
                break;
            case R.id.btn6:
                startActivity(Main6Activity.class);
                break;
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startActivity(Class clazz){
        startActivity(new Intent(this,clazz));
    }
}
