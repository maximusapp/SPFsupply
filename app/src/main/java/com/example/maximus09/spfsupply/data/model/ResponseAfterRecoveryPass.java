package com.example.maximus09.spfsupply.data.model;



public class ResponseAfterRecoveryPass {

    private Boolean success;
    private String message;

    public ResponseAfterRecoveryPass(Boolean success, String message) {
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
