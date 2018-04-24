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
        private String manufacturers_logo;
        private String chat_name;
        private String order_date;

        public ChatsDataUser(String manufacturers_logo, String chat_name, String order_date) {
            this.manufacturers_logo = manufacturers_logo;
            this.chat_name = chat_name;
            this.order_date = order_date;
        }

        public String getManufacturers_logo() {
            return manufacturers_logo;
        }

        public void setManufacturers_logo(String manufacturers_logo) {
            this.manufacturers_logo = manufacturers_logo;
        }

        public String getChat_name() {
            return chat_name;
        }

        public void setChat_name(String chat_name) {
            this.chat_name = chat_name;
        }

        public String getOrder_date() {
            return order_date;
        }

        public void setOrder_date(String order_date) {
            this.order_date = order_date;
        }
    }

}
