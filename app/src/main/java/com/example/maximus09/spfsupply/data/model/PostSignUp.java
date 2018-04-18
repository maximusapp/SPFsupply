package com.example.maximus09.spfsupply.data.model;


public class PostSignUp {

    private String token;
    private String company_name;
    private String email;
    private String manager_name;
    private String password;
    private String company_logo;
    private String phone_number;
    private String business_name;
    private String delivery_address;

    public PostSignUp(String token, String company_name, String email, String manager_name, String password, String company_logo, String phone_number, String business_name, String delivery_address) {
        this.token = token;
        this.company_name = company_name;
        this.email = email;
        this.manager_name = manager_name;
        this.password = password;
        this.company_logo = company_logo;
        this.phone_number = phone_number;
        this.business_name = business_name;
        this.delivery_address = delivery_address;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompany_logo() {
        return company_logo;
    }

    public void setCompany_logo(String company_logo) {
        this.company_logo = company_logo;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }
}
