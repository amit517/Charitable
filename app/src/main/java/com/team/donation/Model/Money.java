package com.team.donation.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


public class Money implements Parcelable {

    private String uniqueID;
    private String userId;
    private double askedAmount;
    private String postedDate;
    private boolean isEnabled;
    private String organizationName;
    private String bkashNumber;
    private String description;
    private String productImageLink;
    private double fixedAmount;
    private double progressBar;
    private String title;

    public Money() {
    }

    public Money(String userId, double askedAmount, String postedDate, boolean isEnabled, String organizationName, String bkashNumber, String description, String productImageLink, double fixedAmount, String title) {
        this.userId = userId;
        this.askedAmount = askedAmount;
        this.postedDate = postedDate;
        this.isEnabled = isEnabled;
        this.organizationName = organizationName;
        this.bkashNumber = bkashNumber;
        this.description = description;
        this.productImageLink = productImageLink;
        this.fixedAmount = fixedAmount;
        this.title = title;
    }

    public Money(String uniqueID, String userId, double askedAmount, String postedDate, boolean isEnabled, String organizationName, String bkashNumber, String description, String productImageLink, double fixedAmount, double progressBar, String title) {
        this.uniqueID = uniqueID;
        this.userId = userId;
        this.askedAmount = askedAmount;
        this.postedDate = postedDate;
        this.isEnabled = isEnabled;
        this.organizationName = organizationName;
        this.bkashNumber = bkashNumber;
        this.description = description;
        this.productImageLink = productImageLink;
        this.fixedAmount = fixedAmount;
        this.progressBar = progressBar;
        this.title = title;
    }

    protected Money(Parcel in) {
        uniqueID = in.readString();
        userId = in.readString();
        askedAmount = in.readDouble();
        postedDate = in.readString();
        isEnabled = in.readByte() != 0;
        organizationName = in.readString();
        bkashNumber = in.readString();
        description = in.readString();
        productImageLink = in.readString();
        fixedAmount = in.readDouble();
        progressBar = in.readDouble();
        title = in.readString();
    }

    public static final Creator<Money> CREATOR = new Creator<Money>() {
        @Override
        public Money createFromParcel(Parcel in) {
            return new Money(in);
        }

        @Override
        public Money[] newArray(int size) {
            return new Money[size];
        }
    };

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

    public String getBkashNumber() {
        return bkashNumber;
    }

    public void setBkashNumber(String bkashNumber) {
        this.bkashNumber = bkashNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductImageLink() {
        return productImageLink;
    }

    public void setProductImageLink(String productImageLink) {
        this.productImageLink = productImageLink;
    }

    public double getFixedAmount() {
        return fixedAmount;
    }

    public void setFixedAmount(double fixedAmount) {
        this.fixedAmount = fixedAmount;
    }

    public double getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(double progressBar) {
        this.progressBar = progressBar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uniqueID);
        dest.writeString(userId);
        dest.writeDouble(askedAmount);
        dest.writeString(postedDate);
        dest.writeByte((byte) (isEnabled ? 1 : 0));
        dest.writeString(organizationName);
        dest.writeString(bkashNumber);
        dest.writeString(description);
        dest.writeString(productImageLink);
        dest.writeDouble(fixedAmount);
        dest.writeDouble(progressBar);
        dest.writeString(title);
    }
}
