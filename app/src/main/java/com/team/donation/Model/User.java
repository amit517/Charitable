package com.team.donation.Model;


public class User {

    private String firstName;
    private String lastName;
    private String nidNumber;
    private String phoneNumber;
    private String address;
    private String email;
    private String profileImageUrl;
    private String uniqueId;
    private String accountType;


    public User() {
    }



  /*  public User(String firstName, String lastName, String nidNumber, String phoneNumber, String address, String email, String uniqueId, String accountType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nidNumber = nidNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.uniqueId = uniqueId;
        this.accountType = accountType;
    }*/

    public User(String firstName, String lastName, String nidNumber, String phoneNumber, String address, String email, String profileImageUrl, String uniqueId, String accountType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nidNumber = nidNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.uniqueId = uniqueId;
        this.accountType = accountType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNidNumber() {
        return nidNumber;
    }

    public void setNidNumber(String nidNumber) {
        this.nidNumber = nidNumber;
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
