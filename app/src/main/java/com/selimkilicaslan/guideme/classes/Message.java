package com.selimkilicaslan.guideme.classes;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.util.Date;

public class Message {
    private String messageContent;
    private String sender;
    private DocumentReference senderReference;
    private Timestamp timestamp;

    public Message(String messageContent, String sender, Timestamp timestamp) {
        this.messageContent = messageContent;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public Message() {
    }

    public DocumentReference getSenderReference() {
        return senderReference;
    }

    public void setSenderReference(DocumentReference senderReference) {
        this.senderReference = senderReference;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
