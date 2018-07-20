package com.example.zhaoxuan.bitmaptest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhaoxuan.li on 2015/10/12.
 */
public class OldAdapter extends RecyclerView.Adapter<OldAdapter.MyViewHolder> {

    private HashMap<String, Bitmap> bitmapHashMap = new HashMap<>();

    private Context context;
    private List<ItemValue> myDatas;

    public OldAdapter(Context context, List myDatas) {
        this.context = context;
        this.myDatas = myDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_recycle_view, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ItemValue item = myDatas.get(position);
        long start = System.currentTimeMillis();
        Bitmap bitmap = bitmapHashMap.get(item.getId());
        boolean isFirstLoad = false;
        if (bitmap == null) {
            try {
                isFirstLoad = true;
                bitmap = BitmapFactory.decodeStream(context.getAssets().open(item.getPath()));
                bitmapHashMap.put(item.getId(), bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        holder.iconImg.setImageBitmap(bitmap);
        holder.timeText.setText("isFirstLoad: " + String.valueOf(isFirstLoad) + "  " + (end - start) + "ms");
    }

    @Override
    public int getItemCount() {
        return myDatas.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        protected ImageView iconImg;
        protected TextView timeText;

        public MyViewHolder(View view) {
            super(view);
            iconImg = (ImageView) view.findViewById(R.id.icon);
            timeText = (TextView) view.findViewById(R.id.time);
        }

    }
}
