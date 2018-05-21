package com.example.maximus09.spfsupply.data.model;



public class Post {
    private String android_device_token;
    private String email;
    private String password;

    public Post(String email, String password, String android_device_token) {
        this.android_device_token = android_device_token;
        this.email = email;
        this.password = password;
    }


}

