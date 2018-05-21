package com.example.maximus09.spfsupply.data.model;

/**
 * Created by maximus09 on 11.05.2018.
 */

public class GetProductByIDUser {
    public String token;
    public String product_id;

    public GetProductByIDUser(String token, String product_id) {
        this.token = token;
        this.product_id = product_id;
    }

}
