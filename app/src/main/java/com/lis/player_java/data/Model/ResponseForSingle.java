package com.lis.player_java.data.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseForSingle {
    @SerializedName("item")
    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item value) {
        this.item = value;
    }
}
