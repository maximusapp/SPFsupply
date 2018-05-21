package com.example.maximus09.spfsupply.data.model;



public class PostPayProduct {
    private String token;
    private String card_id;

    public PostPayProduct(String token, String card_id) {
        this.token = token;
        this.card_id = card_id;
    }

    public String getToken() {
        return token;
    }

    public String getCard_id() {
        return card_id;
    }

}
