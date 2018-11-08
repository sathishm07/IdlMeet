package com.app.server.models;

public class Connection {

    public String getconnectionId() {
        return connectionId;
    }

    public String getsenderId() {
        return senderId;
    }

    public String getreceiverId() {
        return receiverId;
    }

    public String getconnectionDate() {
        return connectionDate;
    }

    public Boolean getconnectionStatus() {
        return connectionStatus;
    }

    public String getconnectionType() {
        return connectionType;
    }


    String connectionId=null;
    String senderId;
    String receiverId;
    String connectionDate;
    Boolean connectionStatus;
    String connectionType;

    public Connection(String senderId, String receiverId, String connectionDate, Boolean connectionStatus, String connectionType) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.connectionDate = connectionDate;
        this.connectionStatus = connectionStatus;
        this.connectionType = connectionType;
    }
    public void setId(String connectionId) {
        this.connectionId = connectionId;
    }
}
