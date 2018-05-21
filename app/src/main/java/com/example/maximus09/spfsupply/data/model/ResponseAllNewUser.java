package com.example.maximus09.spfsupply.data.model;

/**
 * Created by maximus09 on 03.05.2018.
 */

public class ResponseAllNewUser {
    private Boolean success;
    private String checkout_count;
    private String message_count;
    private String company_name;
    private String company_logo;

    public ResponseAllNewUser(Boolean success, String checkout_count, String message_count, String company_name, String company_logo) {
        this.success = success;
        this.checkout_count = checkout_count;
        this.message_count = message_count;
        this.company_name = company_name;
        this.company_logo = company_logo;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCheckout_count() {
        return checkout_count;
    }

    public void setCheckout_count(String checkout_count) {
        this.checkout_count = checkout_count;
    }

    public String getMessage_count() {
        return message_count;
    }

    public void setMessage_count(String message_count) {
        this.message_count = message_count;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_logo() {
        return company_logo;
    }

    public void setCompany_logo(String company_logo) {
        this.company_logo = company_logo;
    }
}
