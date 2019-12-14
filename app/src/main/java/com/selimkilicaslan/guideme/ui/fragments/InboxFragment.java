package com.selimkilicaslan.guideme.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.classes.Chat;
import com.selimkilicaslan.guideme.classes.Message;
import com.selimkilicaslan.guideme.classes.User;
import com.selimkilicaslan.guideme.types.UserType;
import com.selimkilicaslan.guideme.adapters.InboxAdapter;

import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InboxFragment extends Fragment {

    RecyclerView chatRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_inbox, container, false);

        chatRecyclerView = root.findViewById(R.id.chatRecyclerView);
        ArrayList<Chat> chats = new ArrayList<>();
        ArrayList<Message> messages = new ArrayList<>();
        User user = new User("ali","ali@ali","sifre","554", UserType.GUIDE, "https://pbs.twimg.com/profile_images/586131436392046592/YdkXfQah_400x400.jpg");
        User user1 = new User("ali","ali@ali","sifre","554", UserType.GUIDE, "https://pbs.twimg.com/media/EISSCGLXYAATGvM.jpg");
        Date date = new Date();
        Message message = new Message("Tahsim", user, date, true);
        messages.add(message);
        chats.add(new Chat(user, messages));
        chats.add(new Chat(user1, messages));

        InboxAdapter inboxAdapter = new InboxAdapter(root.getContext(), chats);
        chatRecyclerView.setAdapter(inboxAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chatRecyclerView.setLayoutManager(linearLayoutManager);

        return root;
    }
}
