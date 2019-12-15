package com.selimkilicaslan.guideme.classes;

public class Guide {
    private String name;
    private String gender;
    private String city;
    private String country;
    private String quote;
    private String profilePictureURL;
    private int reviewCount;
    private int pricePerHour;
    private float rating;

    public Guide() {
    }

    public Guide(String name, String gender, String city, String country, int reviewCount, float rating, String quote, int pricePerHour, String profilePictureURL) {
        this.name = name;
        this.gender = gender;
        this.city = city;
        this.country = country;
        this.reviewCount = reviewCount;
        this.rating = rating;
        this.quote = quote;
        this.pricePerHour = pricePerHour;
        this.profilePictureURL = profilePictureURL;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public int getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(int pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}
