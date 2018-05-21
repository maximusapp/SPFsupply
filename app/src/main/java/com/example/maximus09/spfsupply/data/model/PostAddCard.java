package com.example.maximus09.spfsupply.data.model;

/**
 * Created by maximus09 on 18.05.2018.
 */

public class PostAddCard {
    private String token;
    private String first_name;
    private String last_name;
    private String card_number;
    private String exp_month;
    private String exp_year;
    private String cvc;

    public PostAddCard(String token, String first_name, String last_name, String card_number, String exp_month, String exp_year, String cvc) {
        this.token = token;
        this.first_name = first_name;
        this.last_name = last_name;
        this.card_number = card_number;
        this.exp_month = exp_month;
        this.exp_year = exp_year;
        this.cvc = cvc;
    }
}
