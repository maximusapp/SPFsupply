package com.example.maximus09.spfsupply.data.model;



public class PostConfirmBuyers {
    private String token;
    private String buyers_id;
    private String is_active;

    public PostConfirmBuyers(String token, String buyers_id, String is_active) {
        this.token = token;
        this.buyers_id = buyers_id;
        this.is_active = is_active;
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

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }
}
