package com.example.maximus09.spfsupply.data.model;


import android.content.Intent;

public class PostPermitionsEdit {
    private String token;
    private String buyers_id;
    private String manufacturers_id;
    private Intent value;

    public PostPermitionsEdit(String token, String buyers_id, String manufacturers_id, Intent value) {
        this.token = token;
        this.buyers_id = buyers_id;
        this.manufacturers_id = manufacturers_id;
        this.value = value;
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

    public String getManufacturers_id() {
        return manufacturers_id;
    }

    public void setManufacturers_id(String manufacturers_id) {
        this.manufacturers_id = manufacturers_id;
    }

    public Intent getValue() {
        return value;
    }

    public void setValue(Intent value) {
        this.value = value;
    }
}
