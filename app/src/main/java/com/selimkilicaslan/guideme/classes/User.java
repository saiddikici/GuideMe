package com.selimkilicaslan.guideme.classes;

import com.selimkilicaslan.guideme.types.UserType;

import android.graphics.Bitmap;

public class User {
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String profilePictureURL;
    private UserType userType;
    private Bitmap bmpProfilePicture;

    public User() {
    }


    public User(String username, String email, String password, String phoneNumber, UserType userType, String profilePictureURL) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
        this.profilePictureURL = profilePictureURL;
    }

    public Bitmap getBmpProfilePicture() {
        return bmpProfilePicture;
    }

    public void setBmpProfilePicture(Bitmap bmpProfilePicture) {
        this.bmpProfilePicture = bmpProfilePicture;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
