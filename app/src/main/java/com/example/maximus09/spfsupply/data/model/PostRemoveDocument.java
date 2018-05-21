package com.example.maximus09.spfsupply.data.model;



public class PostRemoveDocument {
    private String token;
    private String documents_id;

    public PostRemoveDocument(String token, String documents_id) {
        this.token = token;
        this.documents_id = documents_id;
    }

    public String getToken() {
        return token;
    }

    public String getDocuments_id() {
        return documents_id;
    }
}
