package com.team.donation.Model;


public class Accessories {

    private String productTitle;
    private String productType;
    private String productDescription;
    private String creatorPhoneNo;
    private String creatorAddress;
    private String postedDate;
    private boolean isEnabled;
    private String creatorName;
    private String type; // Want to give / needed
    private String productImageLink;
    private String uniqueID;
    private String userId;

    public Accessories() {
    }

    public Accessories(String productTitle, String productType, String productDescription, String creatorPhoneNo, String creatorAddress, String postedDate, boolean isEnabled, String creatorName, String type, String productImageLink, String userId) {
        this.productTitle = productTitle;
        this.productType = productType;
        this.productDescription = productDescription;
        this.creatorPhoneNo = creatorPhoneNo;
        this.creatorAddress = creatorAddress;
        this.postedDate = postedDate;
        this.isEnabled = isEnabled;
        this.creatorName = creatorName;
        this.type = type;
        this.productImageLink = productImageLink;
        this.userId = userId;
    }

    public Accessories(String productTitle, String productType, String productDescription, String creatorPhoneNo, String creatorAddress, String postedDate, boolean isEnabled, String creatorName, String type, String productImageLink, String uniqueID, String userId) {
        this.productTitle = productTitle;
        this.productType = productType;
        this.productDescription = productDescription;
        this.creatorPhoneNo = creatorPhoneNo;
        this.creatorAddress = creatorAddress;
        this.postedDate = postedDate;
        this.isEnabled = isEnabled;
        this.creatorName = creatorName;
        this.type = type;
        this.productImageLink = productImageLink;
        this.uniqueID = uniqueID;
        this.userId = userId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getCreatorPhoneNo() {
        return creatorPhoneNo;
    }

    public void setCreatorPhoneNo(String creatorPhoneNo) {
        this.creatorPhoneNo = creatorPhoneNo;
    }

    public String getCreatorAddress() {
        return creatorAddress;
    }

    public void setCreatorAddress(String creatorAddress) {
        this.creatorAddress = creatorAddress;
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

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductImageLink() {
        return productImageLink;
    }

    public void setProductImageLink(String productImageLink) {
        this.productImageLink = productImageLink;
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
}
