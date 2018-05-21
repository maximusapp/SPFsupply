package com.example.maximus09.spfsupply.data.model;

/**
 * Created by maximus09 on 11.05.2018.
 */

public class PostAddProduct {
    private String token;
    private String product_id;
    private String count;

    public PostAddProduct(String token, String product_id, String count) {
        this.token = token;
        this.product_id = product_id;
        this.count = count;
    }
}
