package com.selimkilicaslan.guideme;

import java.sql.Time;

public class Message {
    private String messageContent;
    private User user;
    private Time time;
    private Boolean isSent;

    public Message(String messageContent, User user, Time time, Boolean isSent) {
        this.messageContent = messageContent;
        this.user = user;
        this.time = time;
        this.isSent = isSent;
    }

    public Message() {
    }

    public Boolean getSent() {
        return isSent;
    }

    public void setSent(Boolean sent) {
        isSent = sent;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
