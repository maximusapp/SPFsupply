package com.example.maximus09.spfsupply.data.model;


import com.example.maximus09.spfsupply.struct.ProductListInfoManufactureAdmin;

import java.util.List;

public class ResponseManufactureInfoAdmin {
    private String success;
    public ManufacturersData manufacturers_data;


    public ResponseManufactureInfoAdmin(String success, ManufacturersData manufacturers_data) {
        this.success = success;
        this.manufacturers_data = manufacturers_data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ManufacturersData getManufacturers_data() {
        return manufacturers_data;
    }

    public void setManufacturers_data(ManufacturersData manufacturers_data) {
        this.manufacturers_data = manufacturers_data;
    }

    public class ManufacturersData {
        public String id;
        public String admins_id;
        public String company_name;
        public String logo;
        public String location;
        public String merchant_id;

        public String tax_amount;
        public String shipping_cost;
        public String fee;

        public String website;
        public String is_active;
        public String is_slider;
        public String created_at;
        public String updated_at;
        public List<ProductListInfoManufactureAdmin> products;


        public ManufacturersData(String id, String admins_id, String company_name, String logo, String location, String merchant_id, String tax_amount, String shipping_cost, String fee, String website, String is_active, String is_slider, String created_at, String updated_at, List<ProductListInfoManufactureAdmin> products) {
            this.id = id;
            this.admins_id = admins_id;
            this.company_name = company_name;
            this.logo = logo;
            this.location = location;
            this.merchant_id = merchant_id;
            this.tax_amount = tax_amount;
            this.shipping_cost = shipping_cost;
            this.fee = fee;
            this.website = website;
            this.is_active = is_active;
            this.is_slider = is_slider;
            this.created_at = created_at;
            this.updated_at = updated_at;
            this.products = products;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAdmins_id() {
            return admins_id;
        }

        public void setAdmins_id(String admins_id) {
            this.admins_id = admins_id;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(String merchant_id) {
            this.merchant_id = merchant_id;
        }

        public String getTax_amount() {
            return tax_amount;
        }

        public void setTax_amount(String tax_amount) {
            this.tax_amount = tax_amount;
        }

        public String getShipping_cost() {
            return shipping_cost;
        }

        public void setShipping_cost(String shipping_cost) {
            this.shipping_cost = shipping_cost;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getIs_active() {
            return is_active;
        }

        public void setIs_active(String is_active) {
            this.is_active = is_active;
        }

        public String getIs_slider() {
            return is_slider;
        }

        public void setIs_slider(String is_slider) {
            this.is_slider = is_slider;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public List<ProductListInfoManufactureAdmin> getProducts() {
            return products;
        }

        public void setProducts(List<ProductListInfoManufactureAdmin> products) {
            this.products = products;
        }
    }
}