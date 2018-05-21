package com.example.maximus09.spfsupply.data.model;



public class ResponseAfterPayProduct {
    public Boolean success;
    public String message;

    public ResponseAfterPayProduct(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
