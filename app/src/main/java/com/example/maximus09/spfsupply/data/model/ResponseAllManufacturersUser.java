package com.example.maximus09.spfsupply.data.model;

import java.util.List;


public class ResponseAllManufacturersUser {
    public Boolean success;
    private List<ManufactFeature> manufacturers_featured;
    private List<ManufactAll> manufacturers_all;

    public ResponseAllManufacturersUser(Boolean success, List<ManufactFeature> manufacturers_featured, List<ManufactAll> manufacturers_all) {
        this.success = success;
        this.manufacturers_featured = manufacturers_featured;
        this.manufacturers_all = manufacturers_all;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<ManufactFeature> getManufacturers_featured() {
        return manufacturers_featured;
    }

    public void setManufacturers_featured(List<ManufactFeature> manufacturers_featured) {
        this.manufacturers_featured = manufacturers_featured;
    }

    public List<ManufactAll> getManufacturers_all() {
        return manufacturers_all;
    }

    public void setManufacturers_all(List<ManufactAll> manufacturers_all) {
        this.manufacturers_all = manufacturers_all;
    }

    public class ManufactFeature {
        public String id;
        public String logo;
        public String company_name;

        public ManufactFeature(String id, String logo, String company_name) {
            this.id = id;
            this.logo = logo;
            this.company_name = company_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }
    }

    public class ManufactAll {
        public String id;
        public String logo;
        public String company_name;

        public ManufactAll(String id, String logo, String company_name) {
            this.id = id;
            this.logo = logo;
            this.company_name = company_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }
    }


}
