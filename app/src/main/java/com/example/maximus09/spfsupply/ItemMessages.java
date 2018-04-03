package com.example.maximus09.spfsupply;


public class ItemMessages {

    private String companyNameMessages;
    private String companyOrderNumMess;

    public ItemMessages(String companyNameMessages, String companyOrderNumMess) {
        this.companyNameMessages = companyNameMessages;
        this.companyOrderNumMess = companyOrderNumMess;
    }

    public String getCompanyNameMessages() {
        return companyNameMessages;
    }

    public void setCompanyNameMessages(String companyNameMessages) {
        this.companyNameMessages = companyNameMessages;
    }

    public String getCompanyOrderNumMess() {
        return companyOrderNumMess;
    }

    public void setCompanyOrderNumMess(String companyOrderNumMess) {
        this.companyOrderNumMess = companyOrderNumMess;
    }
}
