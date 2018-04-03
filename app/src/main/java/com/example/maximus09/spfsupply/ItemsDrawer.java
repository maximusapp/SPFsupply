package com.example.maximus09.spfsupply;



public class ItemsDrawer {
    private String menuItem;
    private String count;

    public ItemsDrawer(String menuItem, String count) {
        this.menuItem = menuItem;
        this.count = count;
    }

    public String getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(String menuItem) {
        this.menuItem = menuItem;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
