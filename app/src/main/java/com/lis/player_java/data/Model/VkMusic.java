package com.lis.player_java.data.Model;

import com.google.gson.annotations.SerializedName;

public class VkMusic {
    @SerializedName("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response value) {
        this.response = value;
    }
}
