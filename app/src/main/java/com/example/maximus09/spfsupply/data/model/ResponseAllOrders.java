package com.example.maximus09.spfsupply.data.model;


import java.util.List;

public class ResponseAllOrders {
    private Boolean success;
    private List<OrdersData> orders_data;

    public ResponseAllOrders(Boolean success, List<OrdersData> orders_data) {
        this.success = success;
        this.orders_data = orders_data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<OrdersData> getOrders_data() {
        return orders_data;
    }

    public void setOrders_data(List<OrdersData> orders_data) {
        this.orders_data = orders_data;
    }

    public class OrdersData {
        private String total_count;
        private String id;
        private String order_date;
        private String manufacturers_company_name;
        private String manufacturers_logo;
        private String order_name;
        private String is_paid;

        public OrdersData(String total_count, String id, String order_date, String manufacturers_company_name, String manufacturers_logo, String order_name, String is_paid) {
            this.total_count = total_count;
            this.id = id;
            this.order_date = order_date;
            this.manufacturers_company_name = manufacturers_company_name;
            this.manufacturers_logo = manufacturers_logo;
            this.order_name = order_name;
            this.is_paid = is_paid;
        }

        public String getTotal_count() {
            return total_count;
        }

        public void setTotal_count(String total_count) {
            this.total_count = total_count;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrder_date() {
            return order_date;
        }

        public void setOrder_date(String order_date) {
            this.order_date = order_date;
        }

        public String getManufacturers_company_name() {
            return manufacturers_company_name;
        }

        public void setManufacturers_company_name(String manufacturers_company_name) {
            this.manufacturers_company_name = manufacturers_company_name;
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

        public String getIs_paid() {
            return is_paid;
        }

        public void setIs_paid(String is_paid) {
            this.is_paid = is_paid;
        }
    }

}
