package com.example.maximus09.spfsupply.data.model;

/**
 * Created by maximus09 on 15.05.2018.
 */

public class ResponseOrderDocumentData {
    public Boolean success;
    public OrderDocData orders_documents_data;

    public ResponseOrderDocumentData(Boolean success, OrderDocData orders_documents_data) {
        this.success = success;
        this.orders_documents_data = orders_documents_data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public OrderDocData getOrders_documents_data() {
        return orders_documents_data;
    }

    public void setOrders_documents_data(OrderDocData orders_documents_data) {
        this.orders_documents_data = orders_documents_data;
    }

    public class OrderDocData {
        public String orders_id;
        public String link;
        public String name;
        public String created_at;
        public String updated_at;
        public String id;

        public OrderDocData(String orders_id, String link, String name, String created_at, String updated_at, String id) {
            this.orders_id = orders_id;
            this.link = link;
            this.name = name;
            this.created_at = created_at;
            this.updated_at = updated_at;
            this.id = id;
        }

        public String getOrders_id() {
            return orders_id;
        }

        public void setOrders_id(String orders_id) {
            this.orders_id = orders_id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
