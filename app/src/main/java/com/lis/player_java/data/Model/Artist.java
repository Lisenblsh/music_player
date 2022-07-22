package com.lis.player_java.data.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Artist {
    private String name;
    private String domain;
    private String id;

    @JsonProperty("name")
    public String getName() { return name; }
    @JsonProperty("name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("domain")
    public String getDomain() { return domain; }
    @JsonProperty("domain")
    public void setDomain(String value) { this.domain = value; }

    @JsonProperty("id")
    public String getID() { return id; }
    @JsonProperty("id")
    public void setID(String value) { this.id = value; }
}
