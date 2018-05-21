package com.example.maximus09.spfsupply.struct;

/**
 * Created by maximus09 on 10.05.2018.
 */

public class ManufacturersData {
    public String id;
    public String admins_id;
    public String company_name;
    public String logo;
    public String location;
    public String website;
    public String merchant_id;
    public String is_active;
    public String created_at;
    public String updated_at;

    public ManufacturersData(String id, String admins_id, String company_name, String logo, String location, String website, String merchant_id, String is_active, String created_at, String updated_at) {
        this.id = id;
        this.admins_id = admins_id;
        this.company_name = company_name;
        this.logo = logo;
        this.location = location;
        this.website = website;
        this.merchant_id = merchant_id;
        this.is_active = is_active;
        this.created_at = created_at;
        this.updated_at = updated_at;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
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
}
