package com.selimkilicaslan.guideme.classes;

import java.util.ArrayList;

public class Chat {
    private User receiver;
    private ArrayList<Message> messages;

    public User getReceiver() {
        return receiver;
    }

    public Chat() {
    }

    public Chat(User receiver, ArrayList<Message> messages) {
        this.receiver = receiver;
        this.messages = messages;
    }

    public void setReceiver(User senderName) {
        receiver = senderName;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
