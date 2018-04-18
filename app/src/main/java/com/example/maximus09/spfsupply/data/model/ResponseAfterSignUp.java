package com.example.maximus09.spfsupply.data.model;



public class ResponseAfterSignUp {

    private Boolean success;
    private String token;
    private String message;

    public ResponseAfterSignUp(Boolean success, String token, String message) {
        this.success = success;
        this.token = token;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    //inner class with user data
    private  class UsersData {
        private String email;
        private String company_name;
        private String phone_number;
        private String company_logo;
        private String business_name;
        private String delivery_address;
        private String manager_name;
        private String updated_at;
        private String created_at;
        private String id;

        public UsersData(String email, String company_name, String phone_number, String company_logo, String business_name, String delivery_address, String manager_name, String updated_at, String created_at, String id) {
            this.email = email;
            this.company_name = company_name;
            this.phone_number = phone_number;
            this.company_logo = company_logo;
            this.business_name = business_name;
            this.delivery_address = delivery_address;
            this.manager_name = manager_name;
            this.updated_at = updated_at;
            this.created_at = created_at;
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

        public String getCompany_logo() {
            return company_logo;
        }

        public void setCompany_logo(String company_logo) {
            this.company_logo = company_logo;
        }

        public String getBusiness_name() {
            return business_name;
        }

        public void setBusiness_name(String business_name) {
            this.business_name = business_name;
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

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
    // end inner class UserData
}
