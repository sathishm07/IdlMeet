package com.app.server.models;

public class Message {

    public String getmessageId() {
        return messageId;
    }

    public String getconnectionId() {
        return connectionId;
    }

    public String getsenderId() {
        return senderId;
    }

    public String getreceiverId() {
        return receiverId;
    }

    public String getmessageContent() {
        return messageContent;
    }

    public String getmessageDate() {
        return messageDate;
    }

    public Boolean getmessageStatus() { return messageStatus; }


    String messageId=null;
    String connectionId;
    String senderId;
    String receiverId;
    String messageContent;
    String messageDate;
    Boolean messageStatus;

    public Message(String connectionId, String senderId, String receiverId, String messageContent, String messageDate, Boolean messageStatus) {
        this.connectionId = connectionId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageContent = messageContent;
        this.messageDate = messageDate;
        this.messageStatus = messageStatus;
    }
    public void setId(String messageId) {
        this.messageId = messageId;
    }

    public void setConnectionId(String id) { this.connectionId = id;}
}
