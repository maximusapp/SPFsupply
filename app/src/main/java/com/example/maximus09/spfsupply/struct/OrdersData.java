package com.example.maximus09.spfsupply.struct;


import java.util.List;

public class OrdersData {
    public String id;
    public String user_id;
    public String manufacturers_id;
    public String is_active;
    public String is_view;
    public String total_count;
    public String created_at;
    public String updated_at;
    public String manufacturers_company_logo;
    public String manufacturers_company_name;
    public String chat_id;
    public String order_name;
    public String company_name;
    public String delivery_location;
    public String order_date;
    public List<Products> products;
    public List<Documents> documents;

    public OrdersData(String id, String user_id, String manufacturers_id, String is_active, String is_view, String total_count, String created_at, String updated_at, String manufacturers_company_logo, String manufacturers_company_name, String chat_id, String order_name, String company_name, String delivery_location, String order_date, List<Products> products, List<Documents> documents) {
        this.id = id;
        this.user_id = user_id;
        this.manufacturers_id = manufacturers_id;
        this.is_active = is_active;
        this.is_view = is_view;
        this.total_count = total_count;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.manufacturers_company_logo = manufacturers_company_logo;
        this.manufacturers_company_name = manufacturers_company_name;
        this.chat_id = chat_id;
        this.order_name = order_name;
        this.company_name = company_name;
        this.delivery_location = delivery_location;
        this.order_date = order_date;
        this.products = products;
        this.documents = documents;
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

    public String getIs_view() {
        return is_view;
    }

    public void setIs_view(String is_view) {
        this.is_view = is_view;
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

    public String getManufacturers_company_logo() {
        return manufacturers_company_logo;
    }

    public void setManufacturers_company_logo(String manufacturers_company_logo) {
        this.manufacturers_company_logo = manufacturers_company_logo;
    }

    public String getManufacturers_company_name() {
        return manufacturers_company_name;
    }

    public void setManufacturers_company_name(String manufacturers_company_name) {
        this.manufacturers_company_name = manufacturers_company_name;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getDelivery_location() {
        return delivery_location;
    }

    public void setDelivery_location(String delivery_location) {
        this.delivery_location = delivery_location;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }

    public List<Documents> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Documents> documents) {
        this.documents = documents;
    }

    public class Products {
        public String id;
        public String count;
        public String price;
        public String product_logo;
        public String product_title;

        public Products(String id, String count, String price, String product_logo, String product_title) {
            this.id = id;
            this.count = count;
            this.price = price;
            this.product_logo = product_logo;
            this.product_title = product_title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProduct_logo() {
            return product_logo;
        }

        public void setProduct_logo(String product_logo) {
            this.product_logo = product_logo;
        }

        public String getProduct_title() {
            return product_title;
        }

        public void setProduct_title(String product_title) {
            this.product_title = product_title;
        }
    }

    public class Documents {
        public String id;
        public String orders_id;
        public String link;
        public String name;
        public String created_at;
        public String updated_at;

        public Documents(String id, String orders_id, String link, String name, String created_at, String updated_at) {
            this.id = id;
            this.orders_id = orders_id;
            this.link = link;
            this.name = name;
            this.created_at = created_at;
            this.updated_at = updated_at;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
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
    }

}
