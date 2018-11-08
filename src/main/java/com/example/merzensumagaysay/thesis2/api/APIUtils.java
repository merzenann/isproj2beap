package com.example.merzensumagaysay.thesis2.api;

public class APIUtils {

    private APIUtils()
    {
    };


    public static final String API_URL = "http://192.168.1.9:8080/admin/";

    public static InterfaceAPI getInterfaceAPI(){
        return RetrofitClient.getClient(API_URL).create(InterfaceAPI.class);
    }
}
