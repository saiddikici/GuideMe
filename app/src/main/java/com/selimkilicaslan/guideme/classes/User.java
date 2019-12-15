package com.selimkilicaslan.guideme.classes;

import com.selimkilicaslan.guideme.types.Gender;
import com.selimkilicaslan.guideme.types.UserType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    private String userID;
    private String username;
    private String email;
    private String phoneNumber;
    private String profilePictureURL;
    private String city;
    private String country;
    private String quote;
    private UserType userType;
    private Gender gender;
    private List<Date> availableDates;
    private int reviewCount;
    private int pricePerHour;
    private float rating;
    private ArrayList<ServiceOffered> servicesOffered;
    private ArrayList<LanguageOffered> languages;

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

    public ArrayList<LanguageOffered> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<LanguageOffered> languages) {
        this.languages = languages;
    }

    public ArrayList<ServiceOffered> getServicesOffered() {
        return servicesOffered;
    }

    public void setServicesOffered(ArrayList<ServiceOffered> servicesOffered) {
        this.servicesOffered = servicesOffered;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public int getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(int pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<Date> getAvailableDates() {
        return availableDates;
    }

    public void setAvailableDates(List<Date> availableDates) {
        this.availableDates = availableDates;
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
