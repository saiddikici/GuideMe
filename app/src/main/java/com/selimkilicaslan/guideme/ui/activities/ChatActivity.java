package com.selimkilicaslan.guideme.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.adapters.ChatAdapter;
import com.selimkilicaslan.guideme.classes.Chat;
import com.selimkilicaslan.guideme.classes.Message;
import com.selimkilicaslan.guideme.classes.User;
import com.selimkilicaslan.guideme.types.UserType;

import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    RecyclerView chatRecyclerView;
    EditText messageInputEditText;

    Chat chat = new Chat();
    ArrayList<Message> messages;
    User user;
    User user2;

    ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatRecyclerView = findViewById(R.id.chatWindowRecyclerView);
        messageInputEditText = findViewById(R.id.messageInputEditText);
        messages = new ArrayList<>();
        user = new User("ali","ali@ali","sifre","554", UserType.GUIDE, "https://i.ibb.co/4j109Mv/taksim-dayi.png");
        user2 = new User("ali","ali@ali","sifre","554", UserType.TOURIST, "https://i.ibb.co/gM42f1L/taksim-interviewer.png");



        chat = new Chat(user2, messages);

        chatAdapter = new ChatAdapter(this, chat);
        chatRecyclerView.setAdapter(chatAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chatRecyclerView.setLayoutManager(linearLayoutManager);
    }

    public void sendMessageButtonOnClick(View view){
        String content = messageInputEditText.getText().toString();
        Message message = new Message(content, user, null, true);
        Message message2 = new Message(content, user2, null, false);
        messages.add(message);
        messages.add(message2);
        chat.setMessages(messages);
        chatAdapter.notifyDataSetChanged();
        messageInputEditText.setText("");
    }
}
