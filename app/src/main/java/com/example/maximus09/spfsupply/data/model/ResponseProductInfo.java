package com.example.maximus09.spfsupply.data.model;

import java.util.List;

/**
 * Created by maximus09 on 11.05.2018.
 */

public class ResponseProductInfo {
    public Boolean success;
    public ManufacturersProductData product_data;

    public ResponseProductInfo(Boolean success, ManufacturersProductData product_data) {
        this.success = success;
        this.product_data = product_data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ManufacturersProductData getProduct_data() {
        return product_data;
    }

    public void setProduct_data(ManufacturersProductData product_data) {
        this.product_data = product_data;
    }

    public class ManufacturersProductData {
        public String id;
        public String manufacturers_id;
        public String title;
        public String logo;
        public String price;
        public String description;
        public String is_active;
        public String created_at;
        public String updated_at;
        public String can_buy;
        public List<FileData> files;

        public ManufacturersProductData(String id, String manufacturers_id, String title, String logo, String price, String description, String is_active, String created_at, String updated_at, String can_buy, List<FileData> files) {
            this.id = id;
            this.manufacturers_id = manufacturers_id;
            this.title = title;
            this.logo = logo;
            this.price = price;
            this.description = description;
            this.is_active = is_active;
            this.created_at = created_at;
            this.updated_at = updated_at;
            this.can_buy = can_buy;
            this.files = files;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getManufacturers_id() {
            return manufacturers_id;
        }

        public void setManufacturers_id(String manufacturers_id) {
            this.manufacturers_id = manufacturers_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public String getCan_buy() {
            return can_buy;
        }

        public void setCan_buy(String can_buy) {
            this.can_buy = can_buy;
        }

        public List<FileData> getFiles() {
            return files;
        }

        public void setFiles(List<FileData> files) {
            this.files = files;
        }
    }

    public class FileData {
        public String link;
        public String file_name;

        public FileData(String link, String file_name) {
            this.link = link;
            this.file_name = file_name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getFile_name() {
            return file_name;
        }

        public void setFile_name(String file_name) {
            this.file_name = file_name;
        }
    }
}
