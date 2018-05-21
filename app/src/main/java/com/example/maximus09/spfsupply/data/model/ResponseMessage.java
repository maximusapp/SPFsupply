package com.example.maximus09.spfsupply.data.model;

import java.util.List;

/**
 * Created by maximus09 on 08.05.2018.
 */

public class ResponseMessage {
    public Boolean success;
    public List<Message>  messages;

    public ResponseMessage(Boolean success, List<Message> messages) {
        this.success = success;
        this.messages = messages;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
