package com.example.maximus09.spfsupply.data.model;

/**
 * Created by maximus09 on 09.05.2018.
 */

public class ResponseMessageSend {
    public Boolean success;
    public Message message_data;

    public ResponseMessageSend(Boolean success, Message message_data) {
        this.success = success;
        this.message_data = message_data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Message getMessage_data() {
        return message_data;
    }

    public void setMessage_data(Message message_data) {
        this.message_data = message_data;
    }
}
