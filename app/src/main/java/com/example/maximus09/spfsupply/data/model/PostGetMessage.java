package com.example.maximus09.spfsupply.data.model;



public class PostGetMessage {
    private String token;
    private String chat_id;
    private String last_message_id;

    public PostGetMessage(String token, String chat_id, String last_message_id) {
        this.token = token;
        this.chat_id = chat_id;
        this.last_message_id = last_message_id;
    }

    public PostGetMessage(String token, String chat_id) {
        this.token = token;
        this.chat_id = chat_id;

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

    public String getLast_message_id() {
        return last_message_id;
    }

    public void setLast_message_id(String last_message_id) {
        this.last_message_id = last_message_id;
    }
}
