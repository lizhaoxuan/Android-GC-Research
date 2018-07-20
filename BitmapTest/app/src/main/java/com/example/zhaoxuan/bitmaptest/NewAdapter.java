package com.example.zhaoxuan.bitmaptest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zhaoxuan.li on 2015/10/12.
 */
public class NewAdapter extends RecyclerView.Adapter<NewAdapter.MyViewHolder> {

    private HashMap<Integer, Bitmap> bitmapHashMap = new HashMap<>();
    private HashMap<String, byte[]> byteHashMap = new HashMap<>();

    private Context context;
    private List<ItemValue> myDatas;

    public NewAdapter(Context context, List myDatas) {
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
        byte[] data = byteHashMap.get(item.getId());
        boolean isFirstLoad = false;
        Bitmap bitmap = null;
        if (data == null) {
            isFirstLoad = true;
            long byteStart = System.currentTimeMillis();
            data = Tool.readFile(context, item.getPath());
            long byteEnd = System.currentTimeMillis();
            Log.d("TAG", "readFileToByte use:" + (byteEnd - byteStart));
            bitmap = createBitmap(data, holder.iconImg.hashCode(), item.getPath());
            byteHashMap.put(item.getId(), data);
            holder.iconImg.setImageBitmap(bitmap);
        } else {
            long s = System.currentTimeMillis();
            createBitmap(data, holder.iconImg.hashCode(), item.getPath());
            long e = System.currentTimeMillis();
            Log.d("TAG", "createBitmap:" + (e - s));
        }
        long end = System.currentTimeMillis();
        Log.d("TAG", "all :" + (end - start));
        holder.timeText.setText("isFirstLoad: " + (data.length > 100000) + "  " + (end - start) + "ms");
    }

    @Override
    public int getItemCount() {
        return myDatas.size();
    }


    private Bitmap createBitmap(byte[] data, int imgHashCode, String path) {
        Bitmap bitmap = bitmapHashMap.get(imgHashCode);
        BitmapFactory.Options option1 = new BitmapFactory.Options();
        option1.inMutable = true;
        try {
            if (bitmap == null) {
                if (data.length > 100000) {
                    bitmap = BitmapFactory.decodeStream(context.getAssets().open(path), null, option1);
                } else {
                    bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, option1);
                }
                bitmapHashMap.put(imgHashCode, bitmap);
            } else {
                option1.inBitmap = bitmap;
                if (data.length > 100000) {
                    bitmap = BitmapFactory.decodeStream(context.getAssets().open(path), null, option1);
                } else {
                    bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, option1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
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
