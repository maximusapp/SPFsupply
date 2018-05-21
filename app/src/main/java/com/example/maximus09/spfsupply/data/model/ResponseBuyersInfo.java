package com.example.maximus09.spfsupply.data.model;

import java.util.List;

/**
 * Created by maximus09 on 10.05.2018.
 */

public class ResponseBuyersInfo {
    private Boolean success;
    public BuyersData buyers_data;
    private List<Card> cards;

    public ResponseBuyersInfo(Boolean success, BuyersData buyers_data, List<Card> cards) {
        this.success = success;
        this.buyers_data = buyers_data;
        this.cards = cards;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public BuyersData getBuyers_data() {
        return buyers_data;
    }

    public void setBuyers_data(BuyersData buyers_data) {
        this.buyers_data = buyers_data;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

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
        public String is_active;
        public String is_new;
        public String is_paid;
        public String created_at;
        public String updated_at;

        public BuyersData(String id, String email, String company_name, String company_logo, String phone_number, String business_address, String permissions, String delivery_address, String manager_name, String is_active, String is_new, String is_paid, String created_at, String updated_at) {
            this.id = id;
            this.email = email;
            this.company_name = company_name;
            this.company_logo = company_logo;
            this.phone_number = phone_number;
            this.business_address = business_address;
            this.permissions = permissions;
            this.delivery_address = delivery_address;
            this.manager_name = manager_name;
            this.is_active = is_active;
            this.is_new = is_new;
            this.is_paid = is_paid;
            this.created_at = created_at;
            this.updated_at = updated_at;
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

        public String getIs_active() {
            return is_active;
        }

        public void setIs_active(String is_active) {
            this.is_active = is_active;
        }

        public String getIs_new() {
            return is_new;
        }

        public void setIs_new(String is_new) {
            this.is_new = is_new;
        }

        public String getIs_paid() {
            return is_paid;
        }

        public void setIs_paid(String is_paid) {
            this.is_paid = is_paid;
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

    private class Card {
        private String id;
        private String card_number;

        public Card(String id, String card_number) {
            this.id = id;
            this.card_number = card_number;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCard_number() {
            return card_number;
        }

        public void setCard_number(String card_number) {
            this.card_number = card_number;
        }
    }
}
