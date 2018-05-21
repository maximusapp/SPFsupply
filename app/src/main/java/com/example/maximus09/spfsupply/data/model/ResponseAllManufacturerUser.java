package com.example.maximus09.spfsupply.data.model;

import com.example.maximus09.spfsupply.struct.ManufacturersData;

import java.util.List;

/**
 * Created by maximus09 on 10.05.2018.
 */

public class ResponseAllManufacturerUser {
    private Boolean success;
    private List<ManufacturersData> manufacturers_data;

    public ResponseAllManufacturerUser(Boolean success, List<ManufacturersData> manufacturers_data) {
        this.success = success;
        this.manufacturers_data = manufacturers_data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<ManufacturersData> getManufacturers_data() {
        return manufacturers_data;
    }

    public void setManufacturers_data(List<ManufacturersData> manufacturers_data) {
        this.manufacturers_data = manufacturers_data;
    }
}
