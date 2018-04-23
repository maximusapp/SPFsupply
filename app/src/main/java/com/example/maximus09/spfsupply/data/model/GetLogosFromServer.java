package com.example.maximus09.spfsupply.data.model;


public class GetLogosFromServer {

    private Boolean success;
    private String[] logos;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String[] getLogos() {
        if(logos == null)
            return new String[]{};
        return logos;
    }

}
