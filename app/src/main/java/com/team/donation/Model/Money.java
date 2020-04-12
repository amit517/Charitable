package com.team.donation.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amit on 08,April,2020
 * kundu.amit517@gmail.com
 */
public class Money {

    private String uniqueID;
    private String userId;
    private double askedAmount;
    private String postedDate;
    private boolean isEnabled;
    private String organizationName;
    private String bkashNumber;
    private String description;

    private List<Transection> transectionList;
    public Money() {
    }

    public Money(String uniqueID, String userId, double askedAmount, String postedDate, boolean isEnabled, String organizationName, String bkashNumber, String description) {
        this.uniqueID = uniqueID;
        this.userId = userId;
        this.askedAmount = askedAmount;
        this.postedDate = postedDate;
        this.isEnabled = isEnabled;
        this.organizationName = organizationName;
        this.bkashNumber = bkashNumber;
        this.description = description;
    }

    public Money(String userId, double askedAmount, String postedDate, boolean isEnabled, String organizationName, String bkashNumber, String description) {
        this.userId = userId;
        this.askedAmount = askedAmount;
        this.postedDate = postedDate;
        this.isEnabled = isEnabled;
        this.organizationName = organizationName;
        this.bkashNumber = bkashNumber;
        this.description = description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public String getBkashNumber() {
        return bkashNumber;
    }

    public void setBkashNumber(String bkashNumber) {
        this.bkashNumber = bkashNumber;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getAskedAmount() {
        return askedAmount;
    }

    public void setAskedAmount(double askedAmount) {
        this.askedAmount = askedAmount;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public List<Transection> getTransectionList() {
        return transectionList;
    }

    public void setTransectionList(List<Transection> transectionList) {
        this.transectionList = transectionList;
    }
}
