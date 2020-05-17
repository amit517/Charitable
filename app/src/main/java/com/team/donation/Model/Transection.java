package com.team.donation.Model;


public class Transection {
    private String transectionID;
    private double ammount;
    private String senderName;
    private String uniqueId;
    private String postToken;
    private String transectionDate;
    private String userId;
    private String sendTo;
    public Transection() {
    }

    public Transection(String transectionID, double ammount, String senderName, String uniqueId, String postToken, String transectionDate, String userId) {
        this.transectionID = transectionID;
        this.ammount = ammount;
        this.senderName = senderName;
        this.uniqueId = uniqueId;
        this.postToken = postToken;
        this.transectionDate = transectionDate;
        this.userId = userId;
    }

    public Transection(String transectionID, double ammount, String senderName, String uniqueId, String postToken, String transectionDate, String userId, String sendTo) {
        this.transectionID = transectionID;
        this.ammount = ammount;
        this.senderName = senderName;
        this.uniqueId = uniqueId;
        this.postToken = postToken;
        this.transectionDate = transectionDate;
        this.userId = userId;
        this.sendTo = sendTo;
    }

    public String getTransectionID() {
        return transectionID;
    }

    public void setTransectionID(String transectionID) {
        this.transectionID = transectionID;
    }

    public double getAmmount() {
        return ammount;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getPostToken() {
        return postToken;
    }

    public void setPostToken(String postToken) {
        this.postToken = postToken;
    }

    public String getTransectionDate() {
        return transectionDate;
    }

    public void setTransectionDate(String transectionDate) {
        this.transectionDate = transectionDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }
}
