package com.example.maximus09.spfsupply.data.model;



public class GetBuyersById {
    private String token;
    private String buyers_id;

    public GetBuyersById(String token, String buyers_id) {
        this.token = token;
        this.buyers_id = buyers_id;
    }

    public String getToken() {
        return token;
    }

    public String getBuyers_id() {
        return buyers_id;
    }
}
