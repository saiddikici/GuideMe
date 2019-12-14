package com.selimkilicaslan.guideme.classes;

import java.util.ArrayList;

public class Chat {
    private User receiver;
    private ArrayList<Message> Messages;

    public User getReceiver() {
        return receiver;
    }

    public Chat() {
    }

    public void setReceiver(User senderName) {
        receiver = senderName;
    }

    public ArrayList<Message> getMessages() {
        return Messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        Messages = messages;
    }

    public Chat(User senderName, ArrayList<Message> messages) {
        receiver = senderName;
        Messages = messages;
    }
}
