package com.example.maximus09.spfsupply.data.model;

/**
 * Created by maximus09 on 18.05.2018.
 */

public class ResponseAddedCard {
    public Boolean success;
    public String message;

    public ResponseAddedCard(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
