package com.selimkilicaslan.guideme;

public class Guide {
    private String Name;
    private String Gender;
    private String City;
    private String Country;
    private String Quote;
    private String ProfilePictureURL;
    private int ReviewCount;
    private int PricePerHour;
    private float Rating;

    public Guide() {
    }

    public Guide(String name, String gender, String city, String country, int reviewCount, float rating, String quote, int pricePerHour, String profilePictureURL) {
        Name = name;
        Gender = gender;
        City = city;
        Country = country;
        ReviewCount = reviewCount;
        Rating = rating;
        Quote = quote;
        PricePerHour = pricePerHour;
        ProfilePictureURL = profilePictureURL;
    }

    public String getProfilePictureURL() {
        return ProfilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        ProfilePictureURL = profilePictureURL;
    }

    public int getPricePerHour() {
        return PricePerHour;
    }

    public void setPricePerHour(int pricePerHour) {
        PricePerHour = pricePerHour;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public int getReviewCount() {
        return ReviewCount;
    }

    public void setReviewCount(int reviewCount) {
        ReviewCount = reviewCount;
    }

    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
    }

    public String getQuote() {
        return Quote;
    }

    public void setQuote(String quote) {
        Quote = quote;
    }
}
