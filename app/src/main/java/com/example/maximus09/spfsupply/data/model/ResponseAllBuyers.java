package com.example.maximus09.spfsupply.data.model;


import java.util.List;

public class ResponseAllBuyers {

    private Boolean success;
    private List<AccountData> buyers_data;

    public ResponseAllBuyers(Boolean success, List<AccountData> buyers_data) {
        this.success = success;
        this.buyers_data = buyers_data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<AccountData> getBuyers_data() {
        return buyers_data;
    }

    public void setBuyers_data(List<AccountData> buyers_data) {
        this.buyers_data = buyers_data;
    }



    public class AccountData {
        private String id;
        private String company_name;
        private String company_logo;
        private String is_new;

        public AccountData(String id, String company_name, String company_logo, String is_new) {
            this.id = id;
            this.company_name = company_name;
            this.company_logo = company_logo;
            this.is_new = is_new;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getCompany_logo() {
            return company_logo;
        }

        public void setCompany_logo(String company_logo) {
            this.company_logo = company_logo;
        }

        public String getIs_new() {
            return is_new;
        }

        public void setIs_new(String is_new) {
            this.is_new = is_new;
        }
    }

}