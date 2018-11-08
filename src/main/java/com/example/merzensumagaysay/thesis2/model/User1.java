package com.example.merzensumagaysay.thesis2.model;

import com.google.gson.annotations.SerializedName;

public class User1 {

    @SerializedName("response")
    private String Response;

    @SerializedName("name")
    private String Name;


    @SerializedName("userType")
    private String userType;

    @SerializedName("id")
    private int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResponse() {
        return Response;
    }

    public String getName() {
        return Name;
    }

    public String getUserType() {
        return userType;
    }
}
