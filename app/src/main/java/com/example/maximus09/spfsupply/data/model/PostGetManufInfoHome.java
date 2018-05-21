package com.example.maximus09.spfsupply.data.model;

/**
 * Created by maximus09 on 11.05.2018.
 */

public class PostGetManufInfoHome {
    private String token;
    private String manufacturers_id;

    public PostGetManufInfoHome(String token, String manufacturers_id) {
        this.token = token;
        this.manufacturers_id = manufacturers_id;
    }
}
