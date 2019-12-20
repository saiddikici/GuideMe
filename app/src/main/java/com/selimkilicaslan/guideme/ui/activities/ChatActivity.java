package com.selimkilicaslan.guideme.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.adapters.ChatAdapter;
import com.selimkilicaslan.guideme.classes.Chat;
import com.selimkilicaslan.guideme.classes.Message;
import com.selimkilicaslan.guideme.classes.MyAppCompatActivity;
import com.selimkilicaslan.guideme.classes.User;

import java.util.ArrayList;

public class ChatActivity extends MyAppCompatActivity {

    RecyclerView chatRecyclerView;
    EditText messageInputEditText;

    Chat chat = new Chat();
    ArrayList<Message> messages;
    User user;
    User user2;

    ChatAdapter chatAdapter;


    String chatID;
    private Bitmap senderImage;
    private Bitmap receiverImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatID = getIntent().getStringExtra("chatID");

        chatRecyclerView = findViewById(R.id.chatWindowRecyclerView);
        messageInputEditText = findViewById(R.id.messageInputEditText);
        messages = new ArrayList<>();
        chat = new Chat();
        chat.setMessages(messages);

        final DocumentReference conversationRef = mDatabase.collection("conversations").document(chatID);

        conversationRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                chat = documentSnapshot.toObject(Chat.class);

                DocumentReference senderRef;
                final DocumentReference receiverRef;

                if(mUser.getUid().equals(chat.getFirstUser())){
                    senderRef = chat.getFirstUserReference();
                    receiverRef = chat.getSecondUserReference();
                } else {
                    senderRef = chat.getSecondUserReference();
                    receiverRef = chat.getFirstUserReference();
                }
                senderRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User sender = documentSnapshot.toObject(User.class);
                        Glide.with(getApplicationContext())
                                .asBitmap()
                                .load(sender.getProfilePictureURL())
                                .into(new CustomTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                        senderImage = resource;
                                        receiverRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                User receiver = documentSnapshot.toObject(User.class);
                                                Glide.with(getApplicationContext())
                                                        .asBitmap()
                                                        .load(receiver.getProfilePictureURL())
                                                        .into(new CustomTarget<Bitmap>() {
                                                            @Override
                                                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                                receiverImage = resource;
                                                                chatAdapter = new ChatAdapter(getApplicationContext(), chat, mUser.getUid(), senderImage, receiverImage);
                                                                chatRecyclerView.setAdapter(chatAdapter);

                                                                conversationRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                                                        //chat = documentSnapshot.toObject(Chat.class);
                                                                        chat.setMessages(documentSnapshot.toObject(Chat.class).getMessages());
                                                                        chatAdapter.notifyDataSetChanged();
                                                                    }
                                                                });
                                                                //chatAdapter = new ChatAdapter(getApplicationContext(), chat, mUser.getUid(), senderImage, receiverImage);
                                                            }

                                                            @Override
                                                            public void onLoadCleared(@Nullable Drawable placeholder) {
                                                            }
                                                        });
                                            }
                                        });
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {
                                    }
                                });
                    }
                });

            }
        });








        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chatRecyclerView.setLayoutManager(linearLayoutManager);
    }

    public void sendMessageButtonOnClick(View view){
        if (!messageInputEditText.getText().toString().equals("")){
            String content = messageInputEditText.getText().toString();
            Message message = new Message(content, mUser.getUid(), Timestamp.now());
            DocumentReference senderRef = mDatabase.collection("users").document(mUser.getUid());
            message.setSenderReference(senderRef);
            DocumentReference reference = mDatabase.collection("conversations").document(chatID);
            reference.update("messages", FieldValue.arrayUnion(message));
            messageInputEditText.setText("");
            //messages.add(message);
            //messages.add(message2);
            //chat.setMessages(messages);
            //chatAdapter.notifyDataSetChanged();
            //messageInputEditText.setText("");
        }

    }
}
