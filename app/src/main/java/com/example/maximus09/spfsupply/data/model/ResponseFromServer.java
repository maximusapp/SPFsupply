package com.example.maximus09.spfsupply.data.model;


public class ResponseFromServer {

    private Boolean success;
    private String account_type;
    private String token;

    public ResponseFromServer(Boolean success, String account_type, String token) {
        this.success = success;
        this.account_type = account_type;
        this.token = token;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public class AccountData {

        private String id;
        private String email;
        private String type_acc;
        private String created_at;
        private String updated_at;
        private String token;

        public AccountData(String id, String email, String type_acc, String created_at, String updated_at, String token) {
            this.id = id;
            this.email = email;
            this.type_acc = type_acc;
            this.created_at = created_at;
            this.updated_at = updated_at;
            this.token = token;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getType_acc() {
            return type_acc;
        }

        public void setType_acc(String type_acc) {
            this.type_acc = type_acc;
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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

}


