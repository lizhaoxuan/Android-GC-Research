package com.example.zhaoxuan.bitmaptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecycleListActivity extends AppCompatActivity {

    private RecyclerView myRecycleView;
    private List<ItemValue> myDatas;
    private NewAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_list);

        myDatas = new ArrayList<>();
        for (int i = 32; i > 0; i--) {
            myDatas.add(new ItemValue(i));
        }

        myAdapter = new NewAdapter(this, myDatas);
        myRecycleView = (RecyclerView) findViewById(R.id.recycle_view);
        myRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        myRecycleView.setAdapter(myAdapter);
    }
}
