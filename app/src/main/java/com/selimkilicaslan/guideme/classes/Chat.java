package com.selimkilicaslan.guideme.classes;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

public class Chat {
    private String firstUser;
    private String secondUser;
    private String chatID;
    private DocumentReference firstUserReference;
    private DocumentReference secondUserReference;
    private ArrayList<Message> messages;

    public Chat() {
    }

    public Chat(String firstUser, ArrayList<Message> messages) {
        this.firstUser = firstUser;
        this.messages = messages;
    }

    public Chat(String firstUser, String secondUser, String chatID, DocumentReference firstUserReference, DocumentReference secondUserReference, ArrayList<Message> messages) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.chatID = chatID;
        this.firstUserReference = firstUserReference;
        this.secondUserReference = secondUserReference;
        this.messages = messages;
    }

    public String getChatID() {
        return chatID;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }

    public String getFirstUser() {
        return firstUser;
    }

    public String getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(String secondUser) {
        this.secondUser = secondUser;
    }

    public void setFirstUser(String senderName) {
        firstUser = senderName;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public DocumentReference getFirstUserReference() {
        return firstUserReference;
    }

    public void setFirstUserReference(DocumentReference firstUserReference) {
        this.firstUserReference = firstUserReference;
    }

    public DocumentReference getSecondUserReference() {
        return secondUserReference;
    }

    public void setSecondUserReference(DocumentReference secondUserReference) {
        this.secondUserReference = secondUserReference;
    }
}
