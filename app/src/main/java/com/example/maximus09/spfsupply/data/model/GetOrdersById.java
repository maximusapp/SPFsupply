package com.example.maximus09.spfsupply.data.model;



public class GetOrdersById {
    private String token;
    private String orders_id;

    public GetOrdersById(String token, String orders_id) {
        this.token = token;
        this.orders_id = orders_id;
    }

    public String getToken() {
        return token;
    }

    public String getOrders_id() {
        return orders_id;
    }
}
