package com.lis.player_java.data.model;

import com.google.gson.annotations.SerializedName;

public class VkMusicForSingle {

    @SerializedName("response")
    private Response[] response;

    public Response[] getResponse(){
        return response;
    }

    public void setResponse(Response[] value) {
        this.response = value;
    }
}
