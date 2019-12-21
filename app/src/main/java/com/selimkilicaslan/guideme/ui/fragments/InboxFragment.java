package com.selimkilicaslan.guideme.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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
                final User user = documentSnapshot.toObject(User.class);
                if(user != null && user.getConversationIDs() != null) {
                    for(DocumentReference conversationID : user.getConversationIDs()){
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


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chatRecyclerView.setLayoutManager(linearLayoutManager);

        return root;
    }
}
