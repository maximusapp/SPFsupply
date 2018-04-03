package com.example.maximus09.spfsupply;

/**
 * Created by maximus09 on 23.03.2018.
 */

public class ItemsOrders {

    private String companyName;
    private String ordersNumber;
    private String ordersPrice;
    private String ordersDate;

    public ItemsOrders(String companyName, String ordersNumber, String ordersPrice, String ordersDate) {
        this.companyName = companyName;
        this.ordersNumber = ordersNumber;
        this.ordersPrice = ordersPrice;
        this.ordersDate = ordersDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOrdersNumber() {
        return ordersNumber;
    }

    public void setOrdersNumber(String ordersNumber) {
        this.ordersNumber = ordersNumber;
    }

    public String getOrdersPrice() {
        return ordersPrice;
    }

    public void setOrdersPrice(String ordersPrice) {
        this.ordersPrice = ordersPrice;
    }

    public String getOrdersDate() {
        return ordersDate;
    }

    public void setOrdersDate(String ordersDate) {
        this.ordersDate = ordersDate;
    }
}
