package com.selimkilicaslan.guideme;

import java.util.Date;

public class Message {
    private String messageContent;
    private User user;
    private Date date;
    private Boolean isSent;

    public Message(String messageContent, User user, Date date, Boolean isSent) {
        this.messageContent = messageContent;
        this.user = user;
        this.date = date;
        this.isSent = isSent;
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

    public Message() {
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

    public Date getTime() {
        return date;
    }

    public void setTime(Date date) {
        this.date = date;
    }
}
