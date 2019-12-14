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

        //ArrayList<Message> messages = new ArrayList<>();
        user = new User("ali","ali@ali","sifre","554", UserType.GUIDE, "https://i.ibb.co/4j109Mv/taksim-dayi.png");
        //Date date = new Date();
        //Message message = new Message("Hello World!", user, date, true);
        //messages.add(message);

        user2 = new User("ali","ali@ali","sifre","554", UserType.TOURIST, "https://i.ibb.co/gM42f1L/taksim-interviewer.png");
        /*Date date2 = new Date();
        Message message2 = new Message("How are you?", user2, date2, false);
        messages.add(message2);

        Message message3 = new Message("Can I think?", user, date, true);
        messages.add(message3);

        Message message4 = new Message("Can you tell me how can I get to Taksim?", user2, date2, false);
        messages.add(message4);

        Message message5 = new Message("Taksim", user, date, true);
        messages.add(message5);

        Message message6 = new Message("You know!", user, date, true);
        messages.add(message6);

        Message message7 = new Message("How can I get to Taksim?", user2, date2, false);
        messages.add(message7);

        Message message8 = new Message("Here is Taksim", user, date, true);
        messages.add(message8);

        User user3 = new User("ali","ali@ali","sifre","554", UserType.TOURIST, "https://i.ibb.co/68zc8Jj/taksim-translator.png");
        Date date3 = new Date();
        Message message9 = new Message("Nasıl giderim diyor dayı!", user3, date3, false);
        messages.add(message9);

        Message message10 = new Message("I answered here", user, date, true);
        messages.add(message10);*/


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
