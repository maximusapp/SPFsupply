package com.example.maximus09.spfsupply.data.model;


import java.util.List;

public class ResponseAllMessageUser {
    private Boolean success;
    private List<ChatsDataUser> chats_data;

    public ResponseAllMessageUser(Boolean success, List<ChatsDataUser> chats_data) {
        this.success = success;
        this.chats_data = chats_data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<ChatsDataUser> getChats_data() {
        return chats_data;
    }

    public void setChats_data(List<ChatsDataUser> chats_data) {
        this.chats_data = chats_data;
    }

    public class ChatsDataUser {
        private String id;
        private String chat_name;
        private String manufacturers_id;
        private String is_active;
        private String created_at;
        private String updated_at;
        private String manufacturers_logo;
        private String manufacturers_company_name;
        private String have_new;
        private String order_date;

        public ChatsDataUser(String id, String chat_name, String manufacturers_id, String is_active, String created_at, String updated_at, String manufacturers_logo, String manufacturers_company_name, String have_new, String order_date) {
            this.id = id;
            this.chat_name = chat_name;
            this.manufacturers_id = manufacturers_id;
            this.is_active = is_active;
            this.created_at = created_at;
            this.updated_at = updated_at;
            this.manufacturers_logo = manufacturers_logo;
            this.manufacturers_company_name = manufacturers_company_name;
            this.have_new = have_new;
            this.order_date = order_date;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getChat_name() {
            return chat_name;
        }

        public void setChat_name(String chat_name) {
            this.chat_name = chat_name;
        }

        public String getManufacturers_id() {
            return manufacturers_id;
        }

        public void setManufacturers_id(String manufacturers_id) {
            this.manufacturers_id = manufacturers_id;
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

        public String getManufacturers_logo() {
            return manufacturers_logo;
        }

        public void setManufacturers_logo(String manufacturers_logo) {
            this.manufacturers_logo = manufacturers_logo;
        }

        public String getManufacturers_company_name() {
            return manufacturers_company_name;
        }

        public void setManufacturers_company_name(String manufacturers_company_name) {
            this.manufacturers_company_name = manufacturers_company_name;
        }

        public String getHave_new() {
            return have_new;
        }

        public void setHave_new(String have_new) {
            this.have_new = have_new;
        }

        public String getOrder_date() {
            return order_date;
        }

        public void setOrder_date(String order_date) {
            this.order_date = order_date;
        }
    }

}
