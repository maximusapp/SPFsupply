package com.example.maximus09.spfsupply;

import com.example.maximus09.spfsupply.struct.BasketsData;

/**
 * Created by maximus09 on 14.05.2018.
 */

public class ListOfChekOut {

    public String manufacturers_id;
    public String manufacturers_title;
    public BasketsData.ManufacturersProduct manufacturers_product;

    public ListOfChekOut(String manufacturers_id, String manufacturers_title) {
        this.manufacturers_id = manufacturers_id;
        this.manufacturers_title = manufacturers_title;
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

    // Constructor for product.
    public ListOfChekOut(BasketsData.ManufacturersProduct manufacturers_product) {
        this.manufacturers_product = manufacturers_product;
    }

    public BasketsData.ManufacturersProduct getManufacturers_product() {
        return manufacturers_product;
    }

    public void setManufacturers_product(BasketsData.ManufacturersProduct manufacturers_product) {
        this.manufacturers_product = manufacturers_product;
    }


    // Checked is Header
    public Boolean isHeader(){
        if (getManufacturers_product() == null) {
            return true;
        } else {
            return false;
        }

    }


}
