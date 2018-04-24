package com.example.maximus09.spfsupply.data.model;


import java.util.List;

public class ResponseAllOrdersUser {
    private Boolean success;
    private List<OrderDataUser> orders_data;

    public ResponseAllOrdersUser(Boolean success, List<OrderDataUser> orders_data) {
        this.success = success;
        this.orders_data = orders_data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<OrderDataUser> getOrders_data() {
        return orders_data;
    }

    public void setOrders_data(List<OrderDataUser> orders_data) {
        this.orders_data = orders_data;
    }

    public class OrderDataUser {
        private String id;
        private String user_id;
        private String manufacturers_id;
        private String is_active;
        private String total_count;
        private String created_at;
        private String updated_at;
        private String order_date;
        private String manufacturers_logo;
        private String order_name;

        public OrderDataUser(String id, String user_id, String manufacturers_id, String is_active, String total_count, String created_at, String updated_at, String order_date, String manufacturers_logo, String order_name) {
            this.id = id;
            this.user_id = user_id;
            this.manufacturers_id = manufacturers_id;
            this.is_active = is_active;
            this.total_count = total_count;
            this.created_at = created_at;
            this.updated_at = updated_at;
            this.order_date = order_date;
            this.manufacturers_logo = manufacturers_logo;
            this.order_name = order_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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

        public String getTotal_count() {
            return total_count;
        }

        public void setTotal_count(String total_count) {
            this.total_count = total_count;
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

        public String getOrder_date() {
            return order_date;
        }

        public void setOrder_date(String order_date) {
            this.order_date = order_date;
        }

        public String getManufacturers_logo() {
            return manufacturers_logo;
        }

        public void setManufacturers_logo(String manufacturers_logo) {
            this.manufacturers_logo = manufacturers_logo;
        }

        public String getOrder_name() {
            return order_name;
        }

        public void setOrder_name(String order_name) {
            this.order_name = order_name;
        }
    }

}
