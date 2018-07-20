package com.example.zhaoxuan.bitmaptest;

/**
 * Created by zhaoxuan.li on 2015/10/12.
 */
public class ItemValue {

    private String id;

    public ItemValue(int id) {
        this.id = String.valueOf(id);
    }

    public String getId() {
        return id;
    }

    public String getPath() {
        return "small/" + id + ".png";
    }
}
