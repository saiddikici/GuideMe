package com.selimkilicaslan.guideme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class InboxActivity extends AppCompatActivity {
    RecyclerView chatRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        setTitle("Chat");

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        ArrayList<Chat> chats = new ArrayList<>();
        ArrayList<Message> messages = new ArrayList<>();
        User user = new User("ali","ali@ali","sifre","554", UserType.GUIDE, "https://pbs.twimg.com/media/EISSCGLXYAATGvM.jpg");
        Date date = new Date();
        Message message = new Message("Tahsim", user, date, true);
        messages.add(message);
        chats.add(new Chat(user, messages));
        chats.add(new Chat(user, messages));

        InboxAdapter inboxAdapter = new InboxAdapter(this, chats);
        chatRecyclerView.setAdapter(inboxAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chatRecyclerView.setLayoutManager(linearLayoutManager); */
    }
}
