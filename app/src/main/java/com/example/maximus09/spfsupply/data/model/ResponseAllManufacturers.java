package com.example.maximus09.spfsupply.data.model;


import java.util.List;

public class ResponseAllManufacturers {

    private Boolean success;
    private List<ManufacturersData> manufacturers_data;

    public ResponseAllManufacturers(Boolean success, List<ManufacturersData> manufacturers_data) {
        this.success = success;
        this.manufacturers_data = manufacturers_data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<ManufacturersData> getManufacturers_data() {
        return manufacturers_data;
    }

    public void setManufacturers_data(List<ManufacturersData> manufacturers_data) {
        this.manufacturers_data = manufacturers_data;
    }

    public class ManufacturersData {
        private String logo;
        private String company_name;
        private String id;

        public ManufacturersData(String logo, String company_name, String id) {
            this.logo = logo;
            this.company_name = company_name;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }
}
