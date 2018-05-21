package com.example.maximus09.spfsupply.data.model;

/**
 * Created by maximus09 on 18.05.2018.
 */

public class ResponseEditProfile {
    public Boolean success;
    public String message;

    public ResponseEditProfile(Boolean success, String message) {
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
