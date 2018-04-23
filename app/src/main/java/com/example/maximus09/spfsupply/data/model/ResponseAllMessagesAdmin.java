package com.example.maximus09.spfsupply.data.model;


import java.util.List;

public class ResponseAllMessagesAdmin {
    private Boolean success;
    private List<ChatsData> chats_data;

    public ResponseAllMessagesAdmin(Boolean success, List<ChatsData> chats_data) {
        this.success = success;
        this.chats_data = chats_data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<ChatsData> getChats_data() {
        return chats_data;
    }

    public void setChats_data(List<ChatsData> chats_data) {
        this.chats_data = chats_data;
    }

    public class ChatsData {
        private String order_id;
        private String id;
        private String manufacturers_logo;
        private String manufacturers_company_name;
        private String have_new_message;

        public ChatsData(String order_id, String id, String manufacturers_logo, String manufacturers_company_name, String have_new_message) {
            this.order_id = order_id;
            this.id = id;
            this.manufacturers_logo = manufacturers_logo;
            this.manufacturers_company_name = manufacturers_company_name;
            this.have_new_message = have_new_message;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getHave_new_message() {
            return have_new_message;
        }

        public void setHave_new_message(String have_new_message) {
            this.have_new_message = have_new_message;
        }

    }

}
