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

        setTitle("Chat");

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        ArrayList<Chat> chats = new ArrayList<>();
        ArrayList<String> messages = new ArrayList<>();
        messages.add("Hello :)");
        chats.add(new Chat("Selim Enes", messages, "https://pbs.twimg.com/profile_images/586131436392046592/YdkXfQah_400x400.jpg"));
        chats.add(new Chat("Said Dikici", messages, "https://pbs.twimg.com/profile_images/586131436392046592/YdkXfQah_400x400.jpg"));

        ChatAdapter chatAdapter = new ChatAdapter(this, chats);
        chatRecyclerView.setAdapter(chatAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chatRecyclerView.setLayoutManager(linearLayoutManager);
    }
}
