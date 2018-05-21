package com.example.maximus09.spfsupply.data.model;

/**
 * Created by maximus09 on 14.05.2018.
 */

public class PostRemoveFromBasket {
    private String token;
    private String basket_id;

    public PostRemoveFromBasket(String token, String basket_id) {
        this.token = token;
        this.basket_id = basket_id;
    }

    public String getToken() {
        return token;
    }

    public String getBasket_id() {
        return basket_id;
    }
}
