package com.example.maximus09.spfsupply.data.model;

/**
 * Created by maximus09 on 08.05.2018.
 */

public class PostSendMessage {
    private String token;
    private String chat_id;
    private String message;
    private String type_message;

    public PostSendMessage(String token, String chat_id, String message, String type_message) {
        this.token = token;
        this.chat_id = chat_id;
        this.message = message;
        this.type_message = type_message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType_message() {
        return type_message;
    }

    public void setType_message(String type_message) {
        this.type_message = type_message;
    }
}
