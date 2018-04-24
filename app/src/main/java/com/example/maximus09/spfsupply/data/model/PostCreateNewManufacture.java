package com.example.maximus09.spfsupply.data.model;



public class PostCreateNewManufacture {
    private String token;
    private String company_name;
    private String location;
    private String website;
    private String tax_amount;
    private String shipping_cost;
    private String fee;
    private String logo;

    public PostCreateNewManufacture(String token, String company_name, String location, String website, String tax_amount, String shipping_cost, String fee,  String logo) {
        this.token = token;
        this.company_name = company_name;
        this.location = location;
        this.website = website;
        this.tax_amount = tax_amount;
        this.shipping_cost = shipping_cost;
        this.fee = fee;
        this.logo = logo;
    }

    public String getToken() {
        return token;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getLocation() {
        return location;
    }

    public String getWebsite() {
        return website;
    }

    public String getTax_amount() {
        return tax_amount;
    }

    public String getShipping_cost() {
        return shipping_cost;
    }

    public String getFee() {
        return fee;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
