package com.example.maximus09.spfsupply.data.model;

import com.example.maximus09.spfsupply.struct.BasketsData;
import com.example.maximus09.spfsupply.struct.CardData;

import java.util.List;

/**
 * Created by maximus09 on 11.05.2018.
 */

public class ResponseCheckOutProduct {

    public Boolean success;
    public String delivery_address;
    public String total_count;
    public List<BasketsData> baskets_data;
    public List<CardData> cards;


    public ResponseCheckOutProduct(Boolean success, String delivery_address, String total_count, List<BasketsData> baskets_data, List<CardData> cards) {
        this.success = success;
        this.delivery_address = delivery_address;
        this.total_count = total_count;
        this.baskets_data = baskets_data;
        this.cards = cards;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public List<BasketsData> getBaskets_data() {
        return baskets_data;
    }

    public void setBaskets_data(List<BasketsData> baskets_data) {
        this.baskets_data = baskets_data;
    }

    public List<CardData> getCards() {
        return cards;
    }

    public void setCards(List<CardData> cards) {
        this.cards = cards;
    }


}
