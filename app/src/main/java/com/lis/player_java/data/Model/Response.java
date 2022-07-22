package com.lis.player_java.data.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {
    private long count;
    private Item[] items;

    @JsonProperty("count")
    public long getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(long value) {
        this.count = value;
    }

    @JsonProperty("items")
    public Item[] getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(Item[] value) {
        this.items = value;
    }
}
