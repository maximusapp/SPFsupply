package com.example.maximus09.spfsupply.data.model;



public class PostDeleteProduct {
    private String manufacturers_products_id;
    private String token;

    public PostDeleteProduct(String manufacturers_products_id, String token) {
        this.manufacturers_products_id = manufacturers_products_id;
        this.token = token;
    }

    public String getManufacturers_products_id() {
        return manufacturers_products_id;
    }

    public void setManufacturers_products_id(String manufacturers_products_id) {
        this.manufacturers_products_id = manufacturers_products_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
