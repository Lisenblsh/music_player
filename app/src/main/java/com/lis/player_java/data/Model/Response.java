package com.lis.player_java.data.Model;

import com.google.gson.annotations.SerializedName;

public class Response {
    @SerializedName("count")
    private long count;
    @SerializedName("items")
    private Item[] items;

    public long getCount() {
        return count;
    }

    public void setCount(long value) {
        this.count = value;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] value) {
        this.items = value;
    }
}
