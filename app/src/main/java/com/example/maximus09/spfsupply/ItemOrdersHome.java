package com.example.maximus09.spfsupply;



public class ItemOrdersHome {

    private String orderHome;
    private String dateHome;
    private String priceHome;

    public ItemOrdersHome(String orderHome, String dateHome, String priceHome) {
        this.orderHome = orderHome;
        this.dateHome = dateHome;
        this.priceHome = priceHome;
    }

    public String getOrderHome() {
        return orderHome;
    }

    public void setOrderHome(String orderHome) {
        this.orderHome = orderHome;
    }

    public String getDateHome() {
        return dateHome;
    }

    public void setDateHome(String dateHome) {
        this.dateHome = dateHome;
    }

    public String getPriceHome() {
        return priceHome;
    }

    public void setPriceHome(String priceHome) {
        this.priceHome = priceHome;
    }
}
