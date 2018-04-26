package com.example.maximus09.spfsupply.data.model;



public class GetManufactureById {

    private String manufacturers_id;
    private String token;

    public GetManufactureById(String manufacturers_id, String token) {
        this.manufacturers_id = manufacturers_id;
        this.token = token;
    }

    public String getManufacturers_id() {
        return manufacturers_id;
    }

    public void setManufacturers_id(String manufacturers_id) {
        this.manufacturers_id = manufacturers_id;
    }

}
