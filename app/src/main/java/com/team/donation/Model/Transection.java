package com.team.donation.Model;

/**
 * Created by Amit on 08,April,2020
 * kundu.amit517@gmail.com
 */
public class Transection {
    private String transectionID;
    private double ammount;
    private String senderName;

    public Transection() {
    }

    public Transection(String transectionID, double ammount, String senderName) {
        this.transectionID = transectionID;
        this.ammount = ammount;
        this.senderName = senderName;
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
}
