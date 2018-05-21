package com.example.maximus09.spfsupply.data.model;

/**
 * Created by maximus09 on 09.05.2018.
 */

public class Message {
    public String chat_id;
    public String from;
    public String to;
    public String from_type;
    public String to_type;
    public String type_message;
    public String message;
    public String updated_at;
    public String created_at;
    public String id;

    public Message(String chat_id, String from, String to, String from_type, String to_type, String type_message, String message, String updated_at, String created_at, String id) {
        this.chat_id = chat_id;
        this.from = from;
        this.to = to;
        this.from_type = from_type;
        this.to_type = to_type;
        this.type_message = type_message;
        this.message = message;
        this.updated_at = updated_at;
        this.created_at = created_at;
        this.id = id;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom_type() {
        return from_type;
    }

    public void setFrom_type(String from_type) {
        this.from_type = from_type;
    }

    public String getTo_type() {
        return to_type;
    }

    public void setTo_type(String to_type) {
        this.to_type = to_type;
    }

    public String getType_message() {
        return type_message;
    }

    public void setType_message(String type_message) {
        this.type_message = type_message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
