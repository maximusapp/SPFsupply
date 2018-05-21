package com.example.maximus09.spfsupply.data.model;

import java.util.List;

/**
 * Created by maximus09 on 04.05.2018.
 */

public class PostBuyersEdit {
    private String token;
    private String buyers_id;
    private String email;
    private String company_name;
    private String password;
    private String last_password;
    private String phone_number;
    private String business_address;
    private String delivery_address;
    private String manager_name;
    private String company_logo;
    private List<PermissionsData> permissions_data;

    public PostBuyersEdit(String token, String buyers_id, String email, String company_name, String password, String last_password, String phone_number, String business_address, String delivery_address, String manager_name, String company_logo, List<PermissionsData> permissions_data) {
        this.token = token;
        this.buyers_id = buyers_id;
        this.email = email;
        this.company_name = company_name;
        this.password = password;
        this.last_password = last_password;
        this.phone_number = phone_number;
        this.business_address = business_address;
        this.delivery_address = delivery_address;
        this.manager_name = manager_name;
        this.company_logo = company_logo;
        this.permissions_data = permissions_data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBuyers_id() {
        return buyers_id;
    }

    public void setBuyers_id(String buyers_id) {
        this.buyers_id = buyers_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLast_password() {
        return last_password;
    }

    public void setLast_password(String last_password) {
        this.last_password = last_password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getBusiness_address() {
        return business_address;
    }

    public void setBusiness_address(String business_address) {
        this.business_address = business_address;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public String getCompany_logo() {
        return company_logo;
    }

    public void setCompany_logo(String company_logo) {
        this.company_logo = company_logo;
    }

    public List<PermissionsData> getPermissions_data() {
        return permissions_data;
    }

    public void setPermissions_data(List<PermissionsData> permissions_data) {
        this.permissions_data = permissions_data;
    }

    private class PermissionsData {
        private String id;
        private int condition;

        public PermissionsData(String id, int condition) {
            this.id = id;
            this.condition = condition;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getCondition() {
            return condition;
        }

        public void setCondition(int condition) {
            this.condition = condition;
        }
    }
}
