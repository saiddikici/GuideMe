package com.selimkilicaslan.guideme.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.classes.Chat;
import com.selimkilicaslan.guideme.classes.MyFragment;
import com.selimkilicaslan.guideme.classes.User;
import com.selimkilicaslan.guideme.adapters.InboxAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InboxFragment extends MyFragment {

    private RecyclerView chatRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_inbox, container, false);

        chatRecyclerView = root.findViewById(R.id.chatRecyclerView);
        final ArrayList<Chat> chats = new ArrayList<>();

        final InboxAdapter inboxAdapter = new InboxAdapter(root.getContext(), chats, mUser.getUid());
        chatRecyclerView.setAdapter(inboxAdapter);

        DocumentReference reference = mDatabase.collection("users").document(mUser.getUid());

        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                chats.clear();
                User user = documentSnapshot.toObject(User.class);
                if(user != null && user.getConversationIDs() != null) {
                    for(DocumentReference conversationID : user.getConversationIDs()){
                        //DocumentReference conversationRef = mDatabase.collection("conversations").document(conversationID.getId());
                        conversationID.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Chat chat = documentSnapshot.toObject(Chat.class);
                                if (chat != null && chat.getMessages() != null && chat.getMessages().size() > 0) {
                                    chats.add(chat);
                                    inboxAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }
            }
        });


        //ArrayList<Message> messages = new ArrayList<>();
        //User user = new User("ali","ali@ali","554", "https://pbs.twimg.com/profile_images/586131436392046592/YdkXfQah_400x400.jpg", UserType.GUIDE, Gender.MALE);
        //User user1 = new User("ali","ali@ali","554", "https://pbs.twimg.com/media/EISSCGLXYAATGvM.jpg", UserType.GUIDE, Gender.MALE);
        //Date date = new Date();
        //Message message = new Message("Tahsim", user, date, true);
        //messages.add(message);
        //chats.add(new Chat(user, messages));
        //chats.add(new Chat(user1, messages));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chatRecyclerView.setLayoutManager(linearLayoutManager);

        return root;
    }
}
