package com.example.zhaoxuan.bitmaptest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    private RecyclerView myRecycleView;
    private List<ItemValue> myDatas;
    private OldAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_actvitiy);
        myDatas = new ArrayList<>();
        for (int i = 32; i > 0; i--) {
            myDatas.add(new ItemValue(i));
        }

        myAdapter = new OldAdapter(this, myDatas);
        myRecycleView = (RecyclerView) findViewById(R.id.recycle_view);
        myRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        myRecycleView.setAdapter(myAdapter);
    }
}
