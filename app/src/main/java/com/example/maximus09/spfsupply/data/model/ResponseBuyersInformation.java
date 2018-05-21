package com.example.maximus09.spfsupply.data.model;


import com.example.maximus09.spfsupply.struct.BuyersData;

import java.util.List;

public class ResponseBuyersInformation {
    public Boolean success;
    public List<Permissions> permissions;
    public BuyersData buyers_data;

    public ResponseBuyersInformation(Boolean success, List<Permissions> permissions, BuyersData buyers_data) {
        this.success = success;
        this.permissions = permissions;
        this.buyers_data = buyers_data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Permissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permissions> permissions) {
        this.permissions = permissions;
    }

    public BuyersData getBuyers_data() {
        return buyers_data;
    }

    public void setBuyers_data(BuyersData buyers_data) {
        this.buyers_data = buyers_data;
    }

    public class Permissions {
        public String company_name;
        public String logo;
        public String id;
        public String checked;

        public Permissions(String company_name, String logo, String id, String checked) {
            this.company_name = company_name;
            this.logo = logo;
            this.id = id;
            this.checked = checked;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getChecked() {
            return checked;
        }

        public void setChecked(String checked) {
            this.checked = checked;
        }
    }

}
