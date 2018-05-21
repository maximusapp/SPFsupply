package com.example.maximus09.spfsupply.data.model;

/**
 * Created by maximus09 on 03.05.2018.
 */

public class PostNewCountForUser {
    private String token;

    public PostNewCountForUser(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
