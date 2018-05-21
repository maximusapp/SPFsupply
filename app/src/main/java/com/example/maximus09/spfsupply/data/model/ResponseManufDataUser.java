package com.example.maximus09.spfsupply.data.model;

import java.util.List;

/**
 * Created by maximus09 on 11.05.2018.
 */

public class ResponseManufDataUser {
    private Boolean success;
    public ManufacturersDataUser manufacturers_data;

    public ResponseManufDataUser(Boolean success, ManufacturersDataUser manufacturers_data) {
        this.success = success;
        this.manufacturers_data = manufacturers_data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ManufacturersDataUser getManufacturers_data() {
        return manufacturers_data;
    }

    public void setManufacturers_data(ManufacturersDataUser manufacturers_data) {
        this.manufacturers_data = manufacturers_data;
    }

    public class ManufacturersDataUser {
        public String id;
        public String admins_id;
        public String company_name;
        public String logo;
        public String location;
        public String merchant_id;
        public String website;
        public String is_active;
        public String is_slider;
        public String created_at;
        public String updated_at;
        public List<ProductsUser> products;

        public ManufacturersDataUser(String id, String admins_id, String company_name, String logo, String location, String merchant_id, String website, String is_active, String is_slider, String created_at, String updated_at, List<ProductsUser> products) {
            this.id = id;
            this.admins_id = admins_id;
            this.company_name = company_name;
            this.logo = logo;
            this.location = location;
            this.merchant_id = merchant_id;
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

        public List<ProductsUser> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsUser> products) {
            this.products = products;
        }


    }

    public class ProductsUser {
        public String id;
        public String manufacturers_id;
        public String title;
        public String logo;
        public String price;
        public String description;
        public String is_active;
        public String created_at;
        public String updated_at;
        public List<String> files;

        public ProductsUser(String id, String manufacturers_id, String title, String logo, String price, String description, String is_active, String created_at, String updated_at, List<String> files) {
            this.id = id;
            this.manufacturers_id = manufacturers_id;
            this.title = title;
            this.logo = logo;
            this.price = price;
            this.description = description;
            this.is_active = is_active;
            this.created_at = created_at;
            this.updated_at = updated_at;
            this.files = files;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getManufacturers_id() {
            return manufacturers_id;
        }

        public void setManufacturers_id(String manufacturers_id) {
            this.manufacturers_id = manufacturers_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIs_active() {
            return is_active;
        }

        public void setIs_active(String is_active) {
            this.is_active = is_active;
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

        public List<String> getFiles() {
            return files;
        }

        public void setFiles(List<String> files) {
            this.files = files;
        }
    }

    private class Files {
        private String link;

        public Files(String link) {
            this.link = link;
        }

    }
}
