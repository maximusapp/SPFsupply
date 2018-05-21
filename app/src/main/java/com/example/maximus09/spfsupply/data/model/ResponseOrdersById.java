package com.example.maximus09.spfsupply.data.model;


import com.example.maximus09.spfsupply.struct.OrdersData;

import java.util.List;

public class ResponseOrdersById {
    private Boolean success;
    private OrdersData orders_data;

    public ResponseOrdersById(Boolean success, OrdersData orders_data) {
        this.success = success;
        this.orders_data = orders_data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public OrdersData getOrders_data() {
        return orders_data;
    }

    public void setOrders_data(OrdersData orders_data) {
        this.orders_data = orders_data;
    }



}
