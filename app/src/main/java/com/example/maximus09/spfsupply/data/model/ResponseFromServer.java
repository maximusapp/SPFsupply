package com.example.maximus09.spfsupply.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseFromServer {


    private Boolean success;

    private String accountType;

    private String token;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

}
