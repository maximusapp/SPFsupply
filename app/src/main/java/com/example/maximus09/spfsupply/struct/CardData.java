package com.example.maximus09.spfsupply.struct;

/**
 * Created by maximus09 on 14.05.2018.
 */

public class CardData {

    public String id;
    public String card_number;


    public CardData(String card_number, String id) {
        this.card_number = card_number;
        this.id = id;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

