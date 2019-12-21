package com.selimkilicaslan.guideme.classes;

import com.google.firebase.firestore.DocumentReference;
import com.selimkilicaslan.guideme.types.MatchStatus;

import java.util.Date;

public class Match {
    private String tourist;
    private DocumentReference touristReference;
    private String guide;
    private DocumentReference guideReference;
    private int price;
    private MatchStatus status;
    private Date date;
    private String matchID;

    public Match() {
    }

    public String getMatchID() {
        return matchID;
    }

    public void setMatchID(String matchID) {
        this.matchID = matchID;
    }

    public String getTourist() {
        return tourist;
    }

    public void setTourist(String tourist) {
        this.tourist = tourist;
    }

    public DocumentReference getTouristReference() {
        return touristReference;
    }

    public void setTouristReference(DocumentReference touristReference) {
        this.touristReference = touristReference;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public DocumentReference getGuideReference() {
        return guideReference;
    }

    public void setGuideReference(DocumentReference guideReference) {
        this.guideReference = guideReference;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
