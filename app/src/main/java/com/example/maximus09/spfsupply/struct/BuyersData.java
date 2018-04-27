package com.example.maximus09.spfsupply.struct;


import java.util.List;

public class BuyersData {
    public String id;
    public String email;
    public String company_name;
    public String company_logo;
    public String phone_number;
    public String business_address;
    public String permissions;
    public String delivery_address;
    public String manager_name;
    public List<Cards> cards;

    public BuyersData(String id, String email, String company_name, String company_logo,
                      String phone_number, String business_address, String permissions,
                      String delivery_address, String manager_name, List<Cards> cards) {
        this.id = id;
        this.email = email;
        this.company_name = company_name;
        this.company_logo = company_logo;
        this.phone_number = phone_number;
        this.business_address = business_address;
        this.permissions = permissions;
        this.delivery_address = delivery_address;
        this.manager_name = manager_name;
        this.cards = cards;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_logo() {
        return company_logo;
    }

    public void setCompany_logo(String company_logo) {
        this.company_logo = company_logo;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getBusiness_address() {
        return business_address;
    }

    public void setBusiness_address(String business_address) {
        this.business_address = business_address;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public List<Cards> getCards() {
        return cards;
    }

    public void setCards(List<Cards> cards) {
        this.cards = cards;
    }

    private class Cards {
        public String card_number;
        public String exp_month;
        public String exp_year;
        public String cvc;
        public String brand;

        public Cards(String card_number, String exp_month, String exp_year, String cvc, String brand) {
            this.card_number = card_number;
            this.exp_month = exp_month;
            this.exp_year = exp_year;
            this.cvc = cvc;
            this.brand = brand;
        }

        public String getCard_number() {
            return card_number;
        }

        public void setCard_number(String card_number) {
            this.card_number = card_number;
        }

        public String getExp_month() {
            return exp_month;
        }

        public void setExp_month(String exp_month) {
            this.exp_month = exp_month;
        }

        public String getExp_year() {
            return exp_year;
        }

        public void setExp_year(String exp_year) {
            this.exp_year = exp_year;
        }

        public String getCvc() {
            return cvc;
        }

        public void setCvc(String cvc) {
            this.cvc = cvc;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }
    }
}
