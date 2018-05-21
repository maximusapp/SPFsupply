package com.example.maximus09.spfsupply.data.model;

/**
 * Created by maximus09 on 17.05.2018.
 */

public class PostDeleteManufacturers {
    private String manufacturers_id;
    private String token;

    public PostDeleteManufacturers(String manufacturers_id, String token) {
        this.manufacturers_id = manufacturers_id;
        this.token = token;
    }

    public String getManufacturers_id() {
        return manufacturers_id;
    }

    public String getToken() {
        return token;
    }
}
