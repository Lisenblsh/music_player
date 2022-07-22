package com.lis.player_java.data.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VkMusic {
    private Response response;

    @JsonProperty("response")
    public Response getResponse() {
        return response;
    }

    @JsonProperty("response")
    public void setResponse(Response value) {
        this.response = value;
    }
}
