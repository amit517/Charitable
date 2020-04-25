package com.team.donation.Model;


public class Organization {
    private String name;
    private String regNumber;
    private String phoneNumber;
    private String address;
    private String email;
    private String profileImageUrl;
    private String uniqueId;
    private String accountType;
    private String isActive;

    public Organization() {
    }

    public Organization(String name, String regNumber, String phoneNumber, String address, String email, String uniqueId, String accountType,String isActive) {
        this.name = name;
        this.regNumber = regNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.uniqueId = uniqueId;
        this.accountType = accountType;
        this.isActive = isActive;
    }

    public Organization(String name, String regNumber, String phoneNumber, String address, String email, String profileImageUrl, String uniqueId, String accountType,String isActive) {
        this.name = name;
        this.regNumber = regNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.uniqueId = uniqueId;
        this.accountType = accountType;
        this.isActive = isActive;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
