package com.example.maximus09.spfsupply.struct;


import java.util.List;

public class ProductListInfoManufactureAdmin {

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

    public ProductListInfoManufactureAdmin(String id, String manufacturers_id, String title,
                                           String logo, String price, String description, String is_active,
                                           String created_at, String updated_at, List<String> files) {
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
