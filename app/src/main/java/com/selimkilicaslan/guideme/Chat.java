package com.selimkilicaslan.guideme;

import java.util.ArrayList;

public class Chat {
    private String SenderName;
    private ArrayList<Message> Messages;
    private String ProfilePictureURL;

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }

    public ArrayList<Message> getMessages() {
        return Messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        Messages = messages;
    }

    public String getProfilePictureURL() {
        return ProfilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        ProfilePictureURL = profilePictureURL;
    }

    public Chat(String senderName, ArrayList<Message> messages, String profilePictureURL) {
        SenderName = senderName;
        Messages = messages;
        ProfilePictureURL = profilePictureURL;
    }
}
