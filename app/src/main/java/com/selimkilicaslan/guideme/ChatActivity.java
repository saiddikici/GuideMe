package com.selimkilicaslan.guideme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    RecyclerView chatRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        ArrayList<Message> messages = new ArrayList<>();
        Message message = new Message();
        message.setMessageContent("Deneme mesajı");

        message.setUser(new User());
        messages.add(new Message());
        Chat chat = new Chat("Said", messages, "https://pbs.twimg.com/media/EISSCGLXYAATGvM.jpg");

        ChatAdapter chatAdapter = new ChatAdapter(this, chat);
        chatRecyclerView.setAdapter(chatAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chatRecyclerView.setLayoutManager(linearLayoutManager);
    }
}
