package com.selimkilicaslan.guideme.classes;

import com.selimkilicaslan.guideme.types.Gender;
import com.selimkilicaslan.guideme.types.UserType;

public class User {
    private String userID;
    private String username;
    private String email;
    private String phoneNumber;
    private String profilePictureURL;
    private UserType userType;
    private Gender gender;

    public User() {
    }

    public User(String username, String email, String phoneNumber, String profilePictureURL, UserType userType, Gender gender) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profilePictureURL = profilePictureURL;
        this.userType = userType;
        this.gender = gender;
    }

    public User(String userID, String username, String email, String phoneNumber, String profilePictureURL, UserType userType, Gender gender) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profilePictureURL = profilePictureURL;
        this.userType = userType;
        this.gender = gender;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
