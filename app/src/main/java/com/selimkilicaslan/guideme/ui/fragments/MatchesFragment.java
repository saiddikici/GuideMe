package com.selimkilicaslan.guideme.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.adapters.InboxAdapter;
import com.selimkilicaslan.guideme.adapters.MatchesAdapter;
import com.selimkilicaslan.guideme.classes.Chat;
import com.selimkilicaslan.guideme.classes.Match;
import com.selimkilicaslan.guideme.classes.MyFragment;
import com.selimkilicaslan.guideme.classes.User;
import com.selimkilicaslan.guideme.types.MatchStatus;

import java.util.ArrayList;

public class MatchesFragment extends MyFragment {

    private RecyclerView matchesRecylerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_matches, container, false);

        matchesRecylerView = root.findViewById(R.id.matchesRecyclerView);
        final ArrayList<Match> matches = new ArrayList<>();

        final MatchesAdapter matchesAdapter = new MatchesAdapter(root.getContext(), matches, mUser.getUid());
        matchesRecylerView.setAdapter(matchesAdapter);

        DocumentReference reference = mDatabase.collection("users").document(mUser.getUid());

        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                matches.clear();
                User user = documentSnapshot.toObject(User.class);
                if(user != null && user.getMatchReferences() != null) {
                    for(DocumentReference matchID : user.getMatchReferences()){
                        //DocumentReference conversationRef = mDatabase.collection("conversations").document(conversationID.getId());
                        matchID.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Match match = documentSnapshot.toObject(Match.class);
                                if (match != null && match.getDate() != null && match.getGuideReference() != null &&
                                    match.getTouristReference() != null && match.getStatus() != null &&
                                        (match.getStatus().equals(MatchStatus.CANCELED) || match.getStatus().equals(MatchStatus.DONE))) {
                                    matches.add(match);
                                    matchesAdapter.notifyDataSetChanged();
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
        matchesRecylerView.setLayoutManager(linearLayoutManager);

        return root;
    }
}
