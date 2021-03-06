package com.example.maximus09.spfsupply.data.model;


public class PostSignUp {

    private String email;
    private String password;
    private String company_name;
    private String phone_number;
    private String business_address;
    private String delivery_address;
    private String manager_name;
    private String card_number;
    private String exp_month;
    private String exp_year;
    private String cvc;
    private String company_logo;

    public PostSignUp(String email, String password, String company_name, String phone_number,
                      String business_address, String delivery_address, String manager_name,
                      String card_number, String exp_month, String exp_year, String cvc, String company_logo) {
        this.email = email;
        this.password = password;
        this.company_name = company_name;
        this.phone_number = phone_number;
        this.business_address = business_address;
        this.delivery_address = delivery_address;
        this.manager_name = manager_name;
        this.card_number = card_number;
        this.exp_month = exp_month;
        this.exp_year = exp_year;
        this.cvc = cvc;
        this.company_logo = company_logo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
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

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getExp_month() {
        return exp_month;
    }

    public void setExp_month(String exp_month) {
        this.exp_month = exp_month;
    }

    public String getExp_year() {
        return exp_year;
    }

    public void setExp_year(String exp_year) {
        this.exp_year = exp_year;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getCompany_logo() {
        return company_logo;
    }

    public void setCompany_logo(String company_logo) {
        this.company_logo = company_logo;
    }
}
