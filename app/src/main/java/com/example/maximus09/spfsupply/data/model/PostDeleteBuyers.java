package com.example.maximus09.spfsupply.data.model;



public class PostDeleteBuyers {
    private String token;
    private String buyers_id;

    public PostDeleteBuyers(String token, String buyers_id) {
        this.token = token;
        this.buyers_id = buyers_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBuyers_id() {
        return buyers_id;
    }

    public void setBuyers_id(String buyers_id) {
        this.buyers_id = buyers_id;
    }
}
