package com.example.maximus09.spfsupply.struct;

import com.example.maximus09.spfsupply.ListOfChekOut;

import java.util.List;



public class BasketsData {
        public String manufacturers_id;
        public String manufacturers_title;
        public List<ManufacturersProduct> manufacturers_products;


    public BasketsData(String manufacturers_id, String manufacturers_title, List<ManufacturersProduct> manufacturers_products) {
        this.manufacturers_id = manufacturers_id;
        this.manufacturers_title = manufacturers_title;
        this.manufacturers_products = manufacturers_products;
    }

    public String getManufacturers_id() {
        return manufacturers_id;
    }

    public void setManufacturers_id(String manufacturers_id) {
        this.manufacturers_id = manufacturers_id;
    }

    public String getManufacturers_title() {
        return manufacturers_title;
    }

    public void setManufacturers_title(String manufacturers_title) {
        this.manufacturers_title = manufacturers_title;
    }

    public List<ManufacturersProduct> getManufacturers_products() {
        return manufacturers_products;
    }

    public void setManufacturers_products(List<ManufacturersProduct> manufacturers_products) {
        this.manufacturers_products = manufacturers_products;
    }

    public class ManufacturersProduct {
        public String id;
        public String price;
        public String logo;
        public String title;
        public String count;

        public ManufacturersProduct(String id, String price, String logo, String title, String count) {
            this.id = id;
            this.price = price;
            this.logo = logo;
            this.title = title;
            this.count = count;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

    }




}