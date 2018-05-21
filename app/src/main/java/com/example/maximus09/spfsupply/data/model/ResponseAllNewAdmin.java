package com.example.maximus09.spfsupply.data.model;

/**
 * Created by maximus09 on 03.05.2018.
 */

public class ResponseAllNewAdmin {
    private Boolean success;
    private String new_buyers_count;
    private String new_message_count;
    private String new_orders_count;

    public ResponseAllNewAdmin(Boolean success, String new_buyers_count, String new_message_count, String new_orders_count) {
        this.success = success;
        this.new_buyers_count = new_buyers_count;
        this.new_message_count = new_message_count;
        this.new_orders_count = new_orders_count;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getNew_buyers_count() {
        return new_buyers_count;
    }

    public void setNew_buyers_count(String new_buyers_count) {
        this.new_buyers_count = new_buyers_count;
    }

    public String getNew_message_count() {
        return new_message_count;
    }

    public void setNew_message_count(String new_message_count) {
        this.new_message_count = new_message_count;
    }

    public String getNew_orders_count() {
        return new_orders_count;
    }

    public void setNew_orders_count(String new_orders_count) {
        this.new_orders_count = new_orders_count;
    }
}
